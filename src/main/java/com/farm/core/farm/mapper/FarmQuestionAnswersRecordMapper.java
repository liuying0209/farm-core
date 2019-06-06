package com.farm.core.farm.mapper;


import com.farm.base.farm.FarmQuestionAnswersRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FarmQuestionAnswersRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(FarmQuestionAnswersRecord record);

    int insertSelective(FarmQuestionAnswersRecord record);

    FarmQuestionAnswersRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FarmQuestionAnswersRecord record);

    int updateByPrimaryKey(FarmQuestionAnswersRecord record);
}