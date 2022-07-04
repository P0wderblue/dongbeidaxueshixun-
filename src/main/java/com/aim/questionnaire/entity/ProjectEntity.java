package com.aim.questionnaire.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = 775713262642151230L;

    private Integer id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目内容
     */
    private String projectContent;
    /**
     * 创建人id
     */
    private String createdId;
    /**
     * 创建人名称
     */
    private String createdBy;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationDate;
    /**
     * 修改人id
     */
    private String lastUpdatedId;
    /**
     * 修改人昵称
     */
    private String lastUpdatedBy;

    /**
     * 修改时间
     */
    private Date lastUpdateDate;

    /**
     * 是否删除
     */
    private Integer isDelete;

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getCreatedId() {
        return createdId;
    }

    public void setCreatedId(String createdId) {
        this.createdId = createdId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedId() {
        return lastUpdatedId;
    }

    public void setLastUpdatedId(String lastUpdatedId) {
        this.lastUpdatedId = lastUpdatedId;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}

