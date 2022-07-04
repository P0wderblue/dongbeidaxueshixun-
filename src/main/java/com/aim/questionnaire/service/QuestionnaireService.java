package com.aim.questionnaire.service;

import com.aim.questionnaire.entity.QuestionnaireEntity;

import java.util.List;
import java.util.Map;

public interface QuestionnaireService {

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
     * 新增数据
     *
     * @param questionnaire 实例对象
     * @return 实例对象
     */
    QuestionnaireEntity insert(QuestionnaireEntity questionnaire);

    /**
     * 修改数据
     *
     * @param questionnaire 实例对象
     * @return 实例对象
     */
    QuestionnaireEntity update(QuestionnaireEntity questionnaire);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id, Boolean isProject);
    boolean deleteProjectById(Integer id);

    /**
     * 统计符合条件数据的数量
     * @param maps  查询参数
     * @return 查询结果
     */
    int queryTotal(Map<String,Object> maps);

    /**
     * 通过主键修改数据
     *
     * @param id 主键
     * @return 是否成功
     */
    int editQuestionnaireStart(Integer id);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuestionnaireEntity queryInfo(Integer id);

    QuestionnaireEntity queryQuestionnaireIssueId(Integer questionnaireIssueId);

    /**
     * 新增数据（导入逻辑）
     * @param questionnaire  数据
     * @return 查询结果
     */
    QuestionnaireEntity insertImport(QuestionnaireEntity questionnaire);

    /**
     * 修改数据（导入）
     * @param questionnaire 数据
     * @return 结果
     */
    QuestionnaireEntity updateImport(QuestionnaireEntity questionnaire);

    /**
     * 查询单挑详细信息
     * @param id id
     * @return 结果
     */
    QuestionnaireEntity queryObject(Integer id);

    int sendEmail(String email,Integer questionnaireId, String emailContent);

    int updateData(QuestionnaireEntity questionnaire);
}
