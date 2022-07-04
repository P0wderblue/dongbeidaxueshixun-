package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.ProjectEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectMapper {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<ProjectEntity> queryAll();

    /**
     * 查询所有数据带条件
     *
     * @param query 实例对象
     * @return 实例对象集合
     */
    List<ProjectEntity> queryList(Map<String, Object> query);

    /**
     * 查询是否有下一页数据
     *
     * @param ProjectEntity 实例对象
     * @return 查询结果
     */
    int queryNextPage(ProjectEntity ProjectEntity);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProjectEntity queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param ProjectEntity 查询条件
     * @return 总行数
     */
    long count(ProjectEntity ProjectEntity);

    /**
     * 新增数据
     *
     * @param ProjectEntity 实例对象
     * @return 影响行数
     */
    int insert(ProjectEntity ProjectEntity);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProjectEntity> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProjectEntity> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProjectEntity> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ProjectEntity> entities);

    /**
     * 修改数据
     *
     * @param ProjectEntity 实例对象
     * @return 影响行数
     */
    int update(ProjectEntity ProjectEntity);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 统计登录用户条数信息
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 查询有没有相同名称的项目
     * @param projectName  项目数量
     * @return 查询结果
     */
    int queryProjectName(@Param("projectName") String projectName);

    /**
     * 查询有没有相同名称的项目（携带底）
     * @param projectName 项目名称
     * @param id  项目Id
     * @return  查询结果
     */
    int queryProjectNameId(@Param("projectName") String projectName, @Param("id")Integer id);
}


