package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.PageUtils;
import com.aim.questionnaire.common.utils.Query;
import com.aim.questionnaire.entity.ProjectEntity;
import com.aim.questionnaire.entity.QuestionnaireEntity;
import com.aim.questionnaire.service.QuestionnaireService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    /**
     * 服务对象
     */
    @Resource
    private QuestionnaireService questionnaireService;


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
        List<QuestionnaireEntity> list = questionnaireService.queryList(query);
        //查询下一页有没有数据
        int count = questionnaireService.queryTotal(query);
        PageUtils pageUtils = new PageUtils(list, count, query.getLimit(), query.getPage());
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(pageUtils);
        return httpResponseEntity;
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/info/{id}")
    public HttpResponseEntity queryById(@PathVariable("id") Integer id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        try {
            QuestionnaireEntity questionnaireEntity = this.questionnaireService.queryInfo(id);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setData(questionnaireEntity);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }
        return httpResponseEntity;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/queryObject/{id}")
    public HttpResponseEntity queryObject(@PathVariable("id") Integer id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        try {
            QuestionnaireEntity questionnaireEntity = this.questionnaireService.queryObject(id);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setData(questionnaireEntity);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }
        return httpResponseEntity;
    }


    /**
     * 通过主键查询问题
     *
     * @param questionnaireIssueId 主键
     * @return 单条数据
     */
    @GetMapping("/queryQuestionnaireIssueId/{questionnaireIssueId}")
    public HttpResponseEntity queryQuestionnaireIssueId(@PathVariable("questionnaireIssueId") Integer questionnaireIssueId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            QuestionnaireEntity questionnaireEntity = this.questionnaireService.queryQuestionnaireIssueId(questionnaireIssueId);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setData(questionnaireEntity);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }
        return httpResponseEntity;
    }

    /**
     * 新增数据
     *
     * @param questionnaire 实体
     * @return 新增结果
     */
    @PostMapping("/saveQuestionnaire")
    public HttpResponseEntity saveQuestionnaire(@RequestBody QuestionnaireEntity questionnaire) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            this.questionnaireService.insert(questionnaire);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//        httpResponseEntity.setData(questionnaireEntity);
            httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }
        return httpResponseEntity;
    }

    @PostMapping("/saveImportQuestionnaire")
    public HttpResponseEntity saveImportQuestionnaire(@RequestBody QuestionnaireEntity questionnaire) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            this.questionnaireService.insertImport(questionnaire);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }
        return httpResponseEntity;
    }

    @PostMapping("/editQuestionnaire")
    public HttpResponseEntity edit(@RequestBody QuestionnaireEntity questionnaire) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            this.questionnaireService.update(questionnaire);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }

        return httpResponseEntity;
    }

    @PostMapping("/update")
    public HttpResponseEntity update(@RequestBody QuestionnaireEntity questionnaire) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            this.questionnaireService.updateData(questionnaire);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }

        return httpResponseEntity;
    }

    @PostMapping("/editImportQuestionnaire")
    public HttpResponseEntity editImportQuestionnaire(@RequestBody QuestionnaireEntity questionnaire) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            this.questionnaireService.updateImport(questionnaire);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }

        return httpResponseEntity;
    }

    @RequestMapping("/delete")
    public HttpResponseEntity deleteById(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            this.questionnaireService.deleteById(projectEntity.getId(),false);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.MAKE_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }
        return httpResponseEntity;

    }

    @PostMapping("/editQuestionnaireStart")
    public HttpResponseEntity editQuestionnaireStart(@RequestBody QuestionnaireEntity questionnaire) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            this.questionnaireService.editQuestionnaireStart(questionnaire.getId());
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }

        return httpResponseEntity;
    }


    @GetMapping(value = "/queryAll")
    public HttpResponseEntity queryAll(@RequestParam Map<String, Object> params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        //分页查询列表
        List<QuestionnaireEntity> list = questionnaireService.queryList(params);
        //查询下一页有没有数据
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(list);
        return httpResponseEntity;
    }

    /**
     * 发送邮件
     */
    @RequestMapping("/sendEmail/{email}/{questionnaireId}/{emailContent}")
    public HttpResponseEntity sendEmail(@PathVariable("email")String email,@PathVariable("questionnaireId") Integer questionnaireId,@PathVariable("emailContent")String emailContent) throws MessagingException {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            questionnaireService.sendEmail(email,questionnaireId,emailContent);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage("发送成功");
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }

        return httpResponseEntity;
    }



}


