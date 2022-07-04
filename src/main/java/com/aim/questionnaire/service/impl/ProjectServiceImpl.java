package com.aim.questionnaire.service.impl;

import com.aim.questionnaire.dao.ProjectMapper;
import com.aim.questionnaire.entity.ProjectEntity;
import com.aim.questionnaire.entity.QuestionnaireEntity;
import com.aim.questionnaire.service.ProjectService;
import com.aim.questionnaire.service.QuestionnaireService;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private QuestionnaireService questionnaireService;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<ProjectEntity> queryAll() {
        return this.projectMapper.queryAll();
    }

    /**
     * 查询所有数据带条件
     *
     * @param map 实例对象
     * @return 实例对象集合
     */
    @Override
    public List<ProjectEntity> queryList(Map<String,Object> map) {
        return this.projectMapper.queryList(map);
    }

    /**
     * 查询是否有下一页数据
     *
     * @param project 实例对象
     * @return 查询结果
     */
    @Override
    public Boolean queryNextPage(ProjectEntity project) {
        int count = this.projectMapper.queryNextPage(project);
        return count > 0;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProjectEntity queryById(Integer id) {
        return this.projectMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    @Override
    public ProjectEntity insert(ProjectEntity project) {
        this.projectMapper.insert(project);
        return project;
    }

    /**
     * 修改数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    @Override
    public ProjectEntity update(ProjectEntity project) {
        this.projectMapper.update(project);
        return this.queryById(project.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        if (this.queryInfo(id) > 0) {
            throw new UnknownAccountException("该项目下有在进行中的问卷，无法删除!");
        }
        boolean isProject = true;
        Map<String,Object> maps=new HashMap<>();
        maps.put("projectId",id);
        List<QuestionnaireEntity> questionnaireEntities = questionnaireService.queryList(maps);
        for (QuestionnaireEntity questionnaireEntity:questionnaireEntities){
            questionnaireService.deleteById(questionnaireEntity.getId(), isProject);
        }
        ProjectEntity projectEntity = projectMapper.queryById(id);
        projectEntity.setIsDelete(1);
        return this.projectMapper.update(projectEntity)>0;
    }

    @Override
    public int queryCount(Map<String, Object> map) {
        return this.projectMapper.queryTotal(map);
    }

    /**
     * 查询有没有相同名称的项目
     * @param projectName  项目数量
     * @return 查询结果
     */
    @Override
    public int queryProjectName(String projectName) {
        return this.projectMapper.queryProjectName(projectName);
    }

    @Override
    public int queryInfo(Integer id) {
        Map<String,Object> maps=new HashMap<>();
        maps.put("projectId",id);
        maps.put("isStart",0);
        return  questionnaireService.queryTotal(maps);
    }

    /**
     * 查询有没有相同名称的项目（携带底）
     * @param projectName 项目名称
     * @param id  项目Id
     * @return  查询结果
     */
    @Override
    public int queryProjectNameId(String projectName, Integer id) {
        return projectMapper.queryProjectNameId(projectName,id);
    }
}

