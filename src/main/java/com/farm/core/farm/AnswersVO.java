package com.farm.core.farm;

import com.farm.base.farm.FarmAnswers;

import java.util.List;

public class AnswersVO {

    private Integer answerId;
    /**
     * farm_question.id
     */
    private Integer questionId;

    /**
     * A或B或C或D
     */
    private String options;

    /**
     * 选项内容
     */
    private String context;


    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}
