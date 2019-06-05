package com.farm.core.farm.controller;

import com.farm.base.common.JsonResult;
import com.farm.core.farm.QuestionVO;
import com.farm.core.farm.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/question")
@RestController
public class QuestionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FarmController.class);
    @Autowired
    private QuestionService questionService;
    @GetMapping("list")
    public JsonResult findQuestionList(){
        List<QuestionVO>list=questionService.findList();
        System.out.println(JsonResult.ok(list));
        return JsonResult.ok(list);
    }
}
