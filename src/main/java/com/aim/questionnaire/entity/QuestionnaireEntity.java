package com.aim.questionnaire.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

public class QuestionnaireEntity implements Serializable {
    private static final long serialVersionUID = -14775862818907045L;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 调查类型
     */
    private String inquiryType;

    /**
     * 开始时间
     */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;

    /**
     * 创建人昵称
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改人
     */
    private String updateUserName;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 是否删除（0否1是）
     */
    private Integer isDelete;

    /**
     * 调查名称
     */
    private String inquiryName;

    /**
     * 调查说明
     */
    private String inquiryExplain;
    private String remark;

    /**
     *  是否启动
     */
    private Integer  isStart;

    /**
     * 点击量
     */
    private Integer clickNumber;

    /**
     * 导入模板的id
     */
    private Integer importQuestionnaireId;

    /**
     * 问卷标题
     */
    private String questionName;

    /**
     * 问卷说明
     */
    private String questionContent;

    /**
     * 问卷问题
     */
    private String questionList;

    /**
     * 问卷回答
     */
    private AnswerEntity questionnaireAnswer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getInquiryName() {
        return inquiryName;
    }

    public void setInquiryName(String inquiryName) {
        this.inquiryName = inquiryName;
    }

    public String getInquiryExplain() {
        return inquiryExplain;
    }

    public void setInquiryExplain(String inquiryExplain) {
        this.inquiryExplain = inquiryExplain;
    }

    public Integer getIsStart() {
        return isStart;
    }

    public void setIsStart(Integer isStart) {
        this.isStart = isStart;
    }

    public Integer getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(Integer clickNumber) {
        this.clickNumber = clickNumber;
    }

    public Integer getImportQuestionnaireId() {
        return importQuestionnaireId;
    }

    public void setImportQuestionnaireId(Integer importQuestionnaireId) {
        this.importQuestionnaireId = importQuestionnaireId;
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

    public String getQuestionList() {
        return questionList;
    }

    public void setQuestionList(String questionList) {
        this.questionList = questionList;
    }

    public AnswerEntity getQuestionnaireAnswer() {
        return questionnaireAnswer;
    }

    public void setQuestionnaireAnswer(AnswerEntity questionnaireAnswer) {
        this.questionnaireAnswer = questionnaireAnswer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

