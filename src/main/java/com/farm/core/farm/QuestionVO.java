package com.farm.core.farm;

import com.farm.base.farm.FarmAnswers;

import java.util.List;

public class QuestionVO {

    private Integer id;

    private String title;

    private List<AnswersVO> answersList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AnswersVO> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(List<AnswersVO> answersList) {
        this.answersList = answersList;
    }
}
