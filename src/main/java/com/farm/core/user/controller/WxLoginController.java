package com.farm.core.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.DingTalkSignatureUtil;
import com.dingtalk.api.request.OapiServiceGetCorpTokenRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiServiceGetCorpTokenResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.farm.base.common.JsonResult;
import com.farm.core.config.ApiUrlConstant;
import com.farm.core.config.DdConfig;
import com.farm.core.config.WxConfig;
import com.farm.core.constant.Constants;
import com.farm.core.user.DdLoginVO;
import com.farm.core.user.UserInfo;
import com.farm.core.user.UserInfoWxDTO;
import com.farm.core.user.exception.DdException;
import com.farm.core.user.exception.UserException;
import com.farm.core.user.service.UserLoginService;
import com.farm.core.util.RedisUtil;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 ** @Version 1.0.0
 */
@RestController
@RequestMapping("api/wx")
public class WxLoginController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    UserLoginService userLoginService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    DdConfig ddConfig;
    @Autowired
    WxConfig wxConfig;

    @PostMapping("login")
    public JsonResult ddLogin(@RequestBody UserInfoWxDTO params) throws UserException, DdException {

        LOGGER.info("请求开始报告: 微信登入接口 参数:{}", JSONObject.toJSONString(params));
        String requestAuthCode = params.getRequestAuthCode();

        if(StringUtils.isBlank(requestAuthCode)){
            LOGGER.error("必要参数为空");
            throw  new DdException(DdException.ERROR_CODE_ILLEGAL_ARGUMENTS);
        }
        //一次调用
        Map resultMap= getOapiServiceGetOpenid(requestAuthCode);
        if(resultMap==null|| resultMap.get("openid")==null || StringUtils.isBlank(resultMap.get("openid").toString()) ){
            LOGGER.error("必要参数为空");
            throw  new DdException(DdException.ERROR_CODE_ILLEGAL_ARGUMENTS);
        }

        String openid=resultMap.get("openid").toString();
        String sessionKey=resultMap.get("session_key").toString();

        UserInfo userInfo= params.getUserInfo();
        userInfo.setUserId(openid);
        userInfo.setSessionKey(sessionKey);
        params.setUserInfo(userInfo);
        String token = this.userLoginService.wxLogin(params);
        LOGGER.info("用户token:{}",token);

        Map<String,String> returnMap=new HashMap<String, String>();

        returnMap.put("token", token);

        return JsonResult.ok(returnMap);

    }


    /**
     * 通过钉钉服务端API获取用户在当前企业的userId
     *
     * @param accessToken 企业访问凭证Token
     * @param code        免登code
     * @
     */
    private OapiUserGetuserinfoResponse getOapiUserGetuserinfo(String accessToken, String code) {
        DingTalkClient client = new DefaultDingTalkClient(ApiUrlConstant.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(code);
        request.setHttpMethod("GET");

        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
        if (response == null || !response.isSuccess()) {
            return null;
        }
        return response;
    }


    /**
     * 获取dd用户的详细信息
     */

    private OapiUserGetResponse getOapiUserGetResponse(String accessToken, String userId) {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
        if (response == null || !response.isSuccess()) {
            return null;
        }

        return response;
    }


    /**
     * suiteTicket是一个定时变化的票据，主要目的是为了开发者的应用与钉钉之间访问时的安全加固。
     * 测试应用：可随意设置，钉钉只做签名不做安全加固限制。
     * 正式应用：开发者应该从自己的db中读取suiteTicket,suiteTicket是由开发者在开发者平台设置的应用回调地址，由钉钉定时推送给应用，
     * 由开发者在回调地址所在代码解密和验证签名完成后获取到的.正式应用钉钉会在开发者代码访问时做严格检查。
     *
     * @return suiteTicket
     */
    private String getSuiteTicket(String suiteKey) throws DdException {

        /**
         * 生产环境直接从redis中获取
         */
        Object obj = redisUtil.get(Constants.SUITE_TICKET);
        if(obj==null){
            LOGGER.error("redis中获取suiteTicket 异常");
            throw new DdException(DdException.SUITE_TICKET_IS_ERROR);
        }

        return obj.toString();

    }


    /**
     * 获取openid
     * @return
     * @throws DdException
     */
    private Map getOapiServiceGetOpenid(String requestAuthCode) throws DdException{

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("appid", wxConfig.getAppid());
        params.put("secret", wxConfig.getSecret());
        params.put("js_code", requestAuthCode);
        params.put("grant_type", wxConfig.getGrantType());

        String queryString = DingTalkSignatureUtil.paramToQueryString(params, "utf-8");
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        //接口返回openid、session_key等
        HttpGet httpGet =new HttpGet("https://api.weixin.qq.com/sns/jscode2session?" + queryString);
        CloseableHttpResponse response = null;
        Map map =null;
        try {
            response = httpCilent.execute(httpGet);
            HttpEntity entity= response.getEntity();
            String result= EntityUtils.toString(entity);
            map = JSON.parseObject(result,Map.class);
            for (Object object : map.keySet()){
                LOGGER.info("key为："+object+"值为："+map.get(object));
            }
        } catch (Exception e) {
            LOGGER.info(e.toString(), e);
            return null;
        }
        return map;
    }

    private Map getOapiServiceGetAccessTokenToken() throws DdException{
     //   https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("appid",wxConfig.getAppid());
        params.put("secret",wxConfig.getSecret());
        params.put("grant_type",wxConfig.getGrantType());

        String queryString = DingTalkSignatureUtil.paramToQueryString(params, "utf-8");
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpGet httpGet =new HttpGet("https://api.weixin.qq.com/cgi-bin/token?" + queryString);
        CloseableHttpResponse response = null;
        Map map =null;
        try {
            response = httpCilent.execute(httpGet);
            HttpEntity entity= response.getEntity();
            String result= EntityUtils.toString(entity);
            map = JSON.parseObject(result,Map.class);
            for (Object object : map.keySet()){
                System.out.println("key为："+object+"值为："+map.get(object));
            }
        } catch (Exception e) {
            LOGGER.info(e.toString(), e);
            return null;
        }
        return map;
    }

    private  JSONObject getWxApiUserInfo(String requestAuthCode,String openid) throws DdException{

        //GET https://api.weixin.qq.com/wxa/getpaidunionid?access_token=ACCESS_TOKEN&openid=OPENID
        //一次调用
       Map tokenMap= getOapiServiceGetAccessTokenToken();
       String access_token= tokenMap.get("access_token").toString();
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("access_token", access_token);
        params.put("openid", openid);

        String queryString = DingTalkSignatureUtil.paramToQueryString(params, "utf-8");
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpGet httpGet =new HttpGet("https://api.weixin.qq.com/wxa/getpaidunionid?" + queryString);
        CloseableHttpResponse response = null;
        JSONObject jsonObject=null;
        try {
            response = httpCilent.execute(httpGet);
            HttpEntity entity= response.getEntity();
            String result= EntityUtils.toString(entity);
            jsonObject = JSON.parseObject(result);
            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            LOGGER.info(e.toString(), e);
            return null;
        }
        return jsonObject;

    }

}
