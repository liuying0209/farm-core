package com.farm.core.farm.service.impl;

import com.farm.base.common.JsonResult;
import com.farm.base.farm.FarmQuestion;
import com.farm.core.farm.QuestionVO;
import com.farm.core.farm.mapper.FarmQuestionMapper;
import com.farm.core.farm.service.QuestionService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionServiceImpl implements QuestionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(FarmServiceImpl.class);

    @Autowired
    private FarmQuestionMapper farmQuestionMapper;

    @Override
    public List<QuestionVO> findList() {
        List<QuestionVO> questionVOList = farmQuestionMapper.selectByParams();
         LOGGER.info("问题与答案List{}",JSONArray.fromObject(questionVOList));
        return questionVOList;
    }
}