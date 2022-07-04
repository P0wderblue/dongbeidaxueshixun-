package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.PageUtils;
import com.aim.questionnaire.common.utils.Query;
import com.aim.questionnaire.entity.ProjectEntity;
import com.aim.questionnaire.entity.UserEntity;
import com.aim.questionnaire.service.ProjectService;
import com.alibaba.fastjson2.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;


    /**
     * 根据登入用户查询所有登录信息
     *
     * @param map 请求信息
     * @return 查询结果
     */
    @RequestMapping(value = "/queryProjectList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectList(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        map.put("userId", "1");
        Query query = new Query(map);
        List<ProjectEntity> projectEntities = projectService.queryList(query);
        int num = projectService.queryCount(query);
        PageUtils pageUtils = new PageUtils(projectEntities, num, query.getLimit(), query.getPage());
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(pageUtils);
        return httpResponseEntity;
    }

    /**
     * 根据id删除项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/deleteProjectById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteProjectById(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
        this.projectService.deleteById(projectEntity.getId());
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setMessage(Constans.MAKE_MESSAGE);
        }  catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }
        return httpResponseEntity;
    }

    /**
     * 添加项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/addProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        //查询有没有相同的项目名称
        int conut = projectService.queryProjectName(projectEntity.getProjectName());
        if (conut > 0) {
            httpResponseEntity.setCode(Constans.LOGOUT_NO_CODE);
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("该项目名称已存在，请更改后重新提交");
            return httpResponseEntity;
        }
        projectEntity.setUserId("1");
        projectEntity.setCreatedId("1");
        projectEntity.setCreatedBy("admin");
        projectEntity.setCreationDate(new Date());
        projectService.insert(projectEntity);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(projectEntity);
        httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
        return httpResponseEntity;
    }

    /**
     * 根据项目id修改项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/modifyProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        int conut = projectService.queryProjectNameId(projectEntity.getProjectName(),projectEntity.getId());
        if (conut > 0) {
            httpResponseEntity.setCode(Constans.LOGOUT_NO_CODE);
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("该项目名称已存在，请更改后重新提交");
            return httpResponseEntity;
        }

        projectEntity.setLastUpdatedId("1");
        projectEntity.setLastUpdatedBy("admin");
        projectEntity.setLastUpdateDate(new Date());
        projectService.update(projectEntity);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(projectEntity);
        httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
        return httpResponseEntity;
    }

    /**
     * 查询该条数据是否能够修改
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/info/{id}")
    public HttpResponseEntity queryInfo(@PathVariable("id") Integer id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        //根据id查询问卷，
        int count = projectService.queryInfo(id);
        if (count > 0) {
            httpResponseEntity.setCode(Constans.LOGOUT_NO_CODE);
            httpResponseEntity.setMessage("该项目下有在进行中的问卷，无法修改");
        } else {
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
        }

        return httpResponseEntity;
    }


    /**
     * 查询该条数据是否能够修改
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/queryById/{id}")
    public HttpResponseEntity queryById(@PathVariable("id") Integer id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        ProjectEntity projectEntity = projectService.queryById(id);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        httpResponseEntity.setData(projectEntity);
        return httpResponseEntity;
    }

    /**
     * 查询全部项目的名字接口
     *
     * @return
     */
    @RequestMapping(value = "/queryAllProjectName", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryAllProjectName() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }

    @GetMapping(value = "/queryAll")
    public HttpResponseEntity queryAll(@RequestParam Map<String, Object> params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        //分页查询列表
        List<ProjectEntity> list = projectService.queryList(params);
        //查询下一页有没有数据
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(list);
        return httpResponseEntity;
    }
}
