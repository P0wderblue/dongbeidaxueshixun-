package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.AnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AnswerMapper {

    List<AnswerEntity> queryAll();

    List<AnswerEntity> queryList(Map<String,Object> maps);

    AnswerEntity queryById(Integer id);

    long count(AnswerEntity questionnaireAnswer);

    int insert(AnswerEntity questionnaireAnswer);

    /**
     * 修改数据
     *
     * @param questionnaireAnswer 实例对象
     * @return 影响行数
     */
    int update(AnswerEntity questionnaireAnswer);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 统计符合条件数据的条数
     * @param maps  条件参数
     * @return 查询结果
     */
    int queryTotal(Map<String, Object> maps);

    List<Map<String, Object>> statistics(Integer questionnaireId);
}


