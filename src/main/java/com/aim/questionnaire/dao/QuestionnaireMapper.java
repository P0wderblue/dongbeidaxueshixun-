package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.QuestionnaireEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuestionnaireMapper {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<QuestionnaireEntity> queryAll();

    /**
     * 查询所有数据带条件
     *
     * @param maps 实例对象
     * @return 实例对象集合
     */
    List<QuestionnaireEntity> queryList(Map<String,Object> maps);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuestionnaireEntity queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param questionnaire 查询条件
     * @return 总行数
     */
    long count(QuestionnaireEntity questionnaire);

    /**
     * 新增数据
     *
     * @param questionnaire 实例对象
     * @return 影响行数
     */
    int insert(QuestionnaireEntity questionnaire);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Questionnaire> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QuestionnaireEntity> entities);

    /**
     * 修改数据
     *
     * @param questionnaire 实例对象
     * @return 影响行数
     */
    int update(QuestionnaireEntity questionnaire);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 统计符合条件数据的数量
     * @param maps  查询参数
     * @return 查询结果
     */
    int queryTotal(Map<String, Object> maps);

    /**
     * 查询修改的问卷名称是否有冲突
     * @param inquiryName  调查名称
     * @return 调查结果
     */
    int queryInquiryMame(String inquiryName);

    int queryInquiryMameId(@Param("inquiryName") String inquiryName,@Param("id") Integer id);
}


