package com.farm.core.farm.mapper;

import com.farm.base.farm.FarmQuestion;
import com.farm.core.farm.QuestionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FarmQuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FarmQuestion record);

    int insertSelective(FarmQuestion record);

    FarmQuestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FarmQuestion record);

    int updateByPrimaryKey(FarmQuestion record);

    List<QuestionVO> selectByParams();
}