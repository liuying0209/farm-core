package com.farm.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("wxConfig")
public class WxConfig {
   //微信公众后台获取
    @Value("${wx.appid}")
    private String appid;

    //微信公众后台获取
    @Value("${wx.secret}")
    private String secret;

    //微信开发文档获取
    @Value("${wx.grant_type}")
    private String grantType;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
