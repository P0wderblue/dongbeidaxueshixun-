package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.ExcleUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExcleUserMapper {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<ExcleUserEntity> queryAll();

    /**
     * 查询所有数据带条件
     *
     * @param maps 实例对象
     * @return 实例对象集合
     */
    List<ExcleUserEntity> queryList(Map<String,Object> maps);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExcleUserEntity queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param excleUser 查询条件
     * @return 总行数
     */
    long count(ExcleUserEntity excleUser);

    /**
     * 新增数据
     *
     * @param excleUser 实例对象
     * @return 影响行数
     */
    int insert(ExcleUserEntity excleUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ExcleUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ExcleUserEntity> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ExcleUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ExcleUserEntity> entities);

    /**
     * 修改数据
     *
     * @param excleUser 实例对象
     * @return 影响行数
     */
    int update(ExcleUserEntity excleUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 查询符合条件的数据
     * @param maps 请求参数
     * @return 查询结果
     */
    int queryTotal(Map<String, Object> maps);

    String selectStudentNumber(@Param("studentNumber") String studentNumber);

    void updateUserByName(ExcleUserEntity userResord);

}


