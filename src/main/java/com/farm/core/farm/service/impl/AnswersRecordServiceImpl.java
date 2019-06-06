package com.farm.core.farm.service.impl;

import com.farm.base.farm.FarmQuestionAnswersRecord;
import com.farm.base.user.User;
import com.farm.core.farm.AnswersRecordVO;
import com.farm.core.farm.AnswersVO;
import com.farm.core.farm.mapper.FarmQuestionAnswersRecordMapper;
import com.farm.core.farm.service.AnswersRecordService;
import com.farm.core.util.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswersRecordServiceImpl implements AnswersRecordService {
   @Autowired
    private FarmQuestionAnswersRecordMapper farmQuestionAnswersRecordMapper;

    @Override
    public Integer addAnswersRecord(AnswersRecordVO answersRecordVO) {

        FarmQuestionAnswersRecord farmQuestionAnswersRecord =new FarmQuestionAnswersRecord();
        BeanUtils.copyProperties(answersRecordVO,farmQuestionAnswersRecord);     //获取但前用户
        User current = UserUtils.getCurrent();
        String userId = current.getId();
        farmQuestionAnswersRecord.setCreatorId(userId);
        farmQuestionAnswersRecord.setUpdaterId(userId);
       Integer recordId= farmQuestionAnswersRecordMapper.insertSelective(farmQuestionAnswersRecord);
        return recordId;
    }
}
