package com.aim.questionnaire.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


public class AnswerEntity implements Serializable {
    private static final long serialVersionUID = -61905331944156829L;

    /**
     * id
     */
    private Integer id;
    /**
     * 问卷id
     */
    private Integer questionnaireId;
    /**
     * 问卷标题
     */
    private String questionName;
    /**
     * 问卷说明
     */
    private String questionContent;
    /**
     * 答题时间
     */
    private String responseTime;
    /**
     * 提交时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 提交ip
     */
    private String submitIp;
    /**
     * 问题回答
     */
    private String answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSubmitIp() {
        return submitIp;
    }

    public void setSubmitIp(String submitIp) {
        this.submitIp = submitIp;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

