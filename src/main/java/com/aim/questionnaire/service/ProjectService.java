package com.aim.questionnaire.service;

import com.aim.questionnaire.entity.ProjectEntity;

import java.util.List;
import java.util.Map;

public interface ProjectService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<ProjectEntity> queryAll();

    /**
     * 查询所有数据带条件
     *
     * @param map 实例对象
     * @return 实例对象集合
     */
    List<ProjectEntity> queryList(Map<String,Object> map);

    /**
     * 查询是否有下一页数据
     *
     * @param project 实例对象
     * @return 查询结果
     */
    Boolean queryNextPage(ProjectEntity project);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProjectEntity queryById(Integer id);

    /**
     * 新增数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    ProjectEntity insert(ProjectEntity project);

    /**
     * 修改数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    ProjectEntity update(ProjectEntity project);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 统计登录用户条数
     * @param map  筛选条件
     * @return 查询结果
     */
    int queryCount(Map<String,Object> map);

    /**
     * 查询有没有相同名称的项目
     * @param projectName  项目数量
     * @return 查询结果
     */
    int queryProjectName(String projectName);

    /**
     * 查询项目下符合条件的问卷
     * @param id 项目id
     * @return 查询结果
     */
    int queryInfo(Integer id);

    /**
     * 查询有没有相同名称的项目（携带底）
     * @param projectName 项目名称
     * @param id  项目Id
     * @return  查询结果
     */
    int queryProjectNameId(String projectName, Integer id);
}
