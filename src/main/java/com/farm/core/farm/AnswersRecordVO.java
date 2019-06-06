package com.farm.core.farm;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  liuying
 * @Date 2019-06-05 
 */


public class AnswersRecordVO  {

	private Long id;

	/**
	 * 回答题的ID
	 */
	private Long questionId;

	/**
	 * 回答题的内容
	 */

	private String questionTitle;

	/**
	 * 答案ID
	 */
	private Long answerId;

	/**
	 * 答案选项
	 */
	private String answerOption;

	/**
	 * 答案内容
	 */
	private String answerContext;


	/**
	 * 0是正常数据，1是已删除数据
	 */
	private Integer delFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public String getAnswerOption() {
		return answerOption;
	}

	public void setAnswerOption(String answerOption) {
		this.answerOption = answerOption;
	}

	public String getAnswerContext() {
		return answerContext;
	}

	public void setAnswerContext(String answerContext) {
		this.answerContext = answerContext;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
