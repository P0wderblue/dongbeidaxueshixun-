package com.aim.questionnaire.service;

import com.aim.questionnaire.entity.AnswerEntity;
import com.aim.questionnaire.entity.QuestionnaireEntity;

import java.util.List;
import java.util.Map;

public interface AnswerService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<AnswerEntity> queryAll();

    /**
     * 查询所有数据带条件
     *
     * @param maps 实例对象
     * @return 实例对象集合
     */
    List<AnswerEntity> queryList(Map<String,Object> maps);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AnswerEntity queryById(Integer id);

    /**
     * 新增数据
     *
     * @param questionnaireAnswer 实例对象
     * @return 实例对象
     */
    AnswerEntity insert(AnswerEntity questionnaireAnswer);

    /**
     * 修改数据
     *
     * @param questionnaireAnswer 实例对象
     * @return 实例对象
     */
    AnswerEntity update(AnswerEntity questionnaireAnswer);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 统计符合条件数据的条数
     * @param maps  条件参数
     * @return 查询结果
     */
    int queryTotal(Map<String,Object> maps);

    /**
     * 通过答卷id获取答卷及数据
     *
     * @param answerId 问卷对应的回答id
     * @return 数据
     */
    QuestionnaireEntity queryQuestionnaireIssueAnswer(Integer answerId);

    /**
     * 统计ip
     * @param questionnaireId 问卷id
     * @return 查询结果
     */
    List<Map<String, Object>> statistics(Integer questionnaireId);

}
