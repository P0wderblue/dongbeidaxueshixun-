package com.aim.questionnaire.service.impl;

import com.aim.questionnaire.dao.AnswerMapper;
import com.aim.questionnaire.dao.QuestionnaireMapper;
import com.aim.questionnaire.entity.AnswerEntity;
import com.aim.questionnaire.entity.QuestionnaireEntity;
import com.aim.questionnaire.service.AnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("questionnaireAnswerService")
public class AnswerServiceImpl implements AnswerService {
    @Resource
    private AnswerMapper questionnaireAnswerMapper;

    @Resource
    private QuestionnaireMapper questionnaireMapper;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<AnswerEntity> queryAll() {
        return this.questionnaireAnswerMapper.queryAll();
    }

    /**
     * 查询所有数据带条件
     *
     * @param maps 实例对象
     * @return 实例对象集合
     */
    @Override
    public List<AnswerEntity> queryList(Map<String, Object> maps) {
        return this.questionnaireAnswerMapper.queryList(maps);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AnswerEntity queryById(Integer id) {
        return this.questionnaireAnswerMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param questionnaireAnswer 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public AnswerEntity insert(AnswerEntity questionnaireAnswer) {
        //根据id查询问卷标题说明，
        QuestionnaireEntity questionnaire = questionnaireMapper.queryById(questionnaireAnswer.getQuestionnaireId());
        questionnaireAnswer.setQuestionName(questionnaire.getQuestionName());
        questionnaireAnswer.setQuestionContent(questionnaire.getQuestionContent());
        questionnaireAnswer.setCreateTime(new Date());
        this.questionnaireAnswerMapper.insert(questionnaireAnswer);

        questionnaire.setClickNumber(questionnaire.getClickNumber() + 1);
        questionnaireMapper.update(questionnaire);
        return questionnaireAnswer;
    }

    /**
     * 修改数据
     *
     * @param questionnaireAnswer 实例对象
     * @return 实例对象
     */
    @Override
    public AnswerEntity update(AnswerEntity questionnaireAnswer) {
        this.questionnaireAnswerMapper.update(questionnaireAnswer);
        return this.queryById(questionnaireAnswer.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.questionnaireAnswerMapper.deleteById(id) > 0;
    }

    /**
     * 统计符合条件数据的条数
     *
     * @param maps 条件参数
     * @return 查询结果
     */
    @Override
    public int queryTotal(Map<String, Object> maps) {
        return questionnaireAnswerMapper.queryTotal(maps);
    }

    /**
     * 通过答卷id获取答卷及数据
     *
     * @param answerId 问卷对应的回答id
     * @return 数据
     */
    @Override
    public QuestionnaireEntity queryQuestionnaireIssueAnswer(Integer answerId) {
        //答卷id
        AnswerEntity questionnaireAnswerEntity = questionnaireAnswerMapper.queryById(answerId);
        //答卷问题
        QuestionnaireEntity questionnaireIssue = questionnaireMapper.queryById(questionnaireAnswerEntity.getQuestionnaireId());
        questionnaireIssue.setQuestionnaireAnswer(questionnaireAnswerEntity);
        return questionnaireIssue;
    }

    /**
     *统计问卷的ip
     * @param questionnaireId 问卷id
     * @return 问卷结果
     */
    @Override
    public List<Map<String, Object>> statistics(Integer questionnaireId) {
        return questionnaireAnswerMapper.statistics(questionnaireId);
    }

}

