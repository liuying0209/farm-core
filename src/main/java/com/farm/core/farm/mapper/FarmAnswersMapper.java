package com.farm.core.farm.mapper;

import com.farm.base.farm.FarmAnswers;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FarmAnswersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FarmAnswers record);

    int insertSelective(FarmAnswers record);

    FarmAnswers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FarmAnswers record);

    int updateByPrimaryKey(FarmAnswers record);
}