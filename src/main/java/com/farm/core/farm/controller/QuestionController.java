package com.farm.core.farm.controller;

import com.alibaba.fastjson.JSONObject;
import com.farm.base.common.JsonResult;
import com.farm.base.exception.FarmException;
import com.farm.core.config.LoginCheck;
import com.farm.core.farm.AnswersRecordVO;
import com.farm.core.farm.AnswersVO;
import com.farm.core.farm.CreateFarmVO;
import com.farm.core.farm.QuestionVO;
import com.farm.core.farm.service.AnswersRecordService;
import com.farm.core.farm.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequestMapping("api/question")
@RestController
public class QuestionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FarmController.class);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswersRecordService answersRecordService;

    @GetMapping("list")
    @LoginCheck
    public JsonResult findQuestionList(){
        List<QuestionVO>list=questionService.findList();
        LOGGER.info(JsonResult.ok(list).toString());
        return JsonResult.ok(list);
    }


    @PostMapping("add")
    @LoginCheck
    public JsonResult saveAnswersRecord(@RequestBody AnswersRecordVO params) throws FarmException {
        LOGGER.info("请求开始报告 : 回答问题记录参数:{}", JSONObject.toJSONString(params));
        Integer recordId= answersRecordService.addAnswersRecord(params);
        HashMap<String, Object> map = new HashMap<>();
        map.put("answersRecordId",recordId);
        return JsonResult.ok(map);
    }

}
