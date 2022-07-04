package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.PageUtils;
import com.aim.questionnaire.common.utils.Query;
import com.aim.questionnaire.entity.AnswerEntity;
import com.aim.questionnaire.entity.QuestionnaireEntity;
import com.aim.questionnaire.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questionnaireAnswer")
public class AnswerController {
    /**
     * 服务对象
     */
    @Resource
    private AnswerService questionnaireAnswerService;

    /**
     * 分页查询符合条件数据
     *
     * @return 实例对象集合
     */
    @RequestMapping(value = "/queryListPage", method = RequestMethod.POST)
    public HttpResponseEntity queryListPage(@RequestBody Map<String, Object> params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        Query query = new Query(params);
        //分页查询列表
        List<AnswerEntity> list = questionnaireAnswerService.queryList(query);
        //查询下一页有没有数据
        int count = questionnaireAnswerService.queryTotal(query);
        PageUtils pageUtils = new PageUtils(list, count, query.getLimit(), query.getPage());
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(pageUtils);
        return httpResponseEntity;
    }


    /**
     * 新增数据
     *
     * @param questionnaireAnswer 实体
     * @return 新增结果
     */
    @PostMapping("add")
    public HttpResponseEntity add(@RequestBody AnswerEntity questionnaireAnswer) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setData(this.questionnaireAnswerService.insert(questionnaireAnswer));
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setMessage("答卷提交成功，感谢您的参与。");
        return httpResponseEntity;
    }


    /**
     * 通过主键查询单条数据及回答
     *
     * @param answerId 问卷对应的回答id
     * @return 单条数据
     */
    @GetMapping("/queryQuestionnaireAnswer/{answerId}")
    public HttpResponseEntity queryQuestionnaireAnswer(@PathVariable("answerId") Integer answerId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        QuestionnaireEntity questionnaireIssue = questionnaireAnswerService.queryQuestionnaireIssueAnswer(answerId);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(questionnaireIssue);
        return httpResponseEntity;
    }

    /**
     * 通过主键查询单条数据及回答
     *
     * @param questionnaireId 问卷id
     * @return 单条数据
     */
    @GetMapping("/queryQuestionnaire/{questionnaireId}")
    public HttpResponseEntity queryQuestionnaire(@PathVariable("questionnaireId") Integer questionnaireId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        Map<String,Object> map=new HashMap<>();
        map.put("questionnaireId",questionnaireId);
        List<AnswerEntity> questionnaireIssue = questionnaireAnswerService.queryList(map);
        if(questionnaireIssue.size()>0){
            httpResponseEntity.setData(questionnaireIssue);
        }
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        return httpResponseEntity;
    }

    /**
     * 统计图
     */
    @RequestMapping("/statistics/{questionnaireId}")
    public HttpResponseEntity statistics(@PathVariable("questionnaireId") Integer questionnaireId) throws MessagingException {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            //统计当前问卷所有回答的ip（只有前五个）
            List<Map<String, Object>> maps=questionnaireAnswerService.statistics(questionnaireId);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setData(maps);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }

        return httpResponseEntity;
    }


}


