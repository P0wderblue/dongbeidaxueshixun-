package com.projectManagement.controller;

import com.projectManagement.beans.HttpResponseEntity;
import com.projectManagement.common.Constans;
import com.projectManagement.dao.entity.ProjectEntity;
import com.projectManagement.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by wln on 2018\8\6 0006.
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;


    /**
     * 查询全部项目的信息
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/queryProjectList",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectList(@RequestBody(required = false) ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        List<Map<String, Object>> objects = projectService.queryProjectList(projectEntity);
        System.out.println(objects);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(objects);
        httpResponseEntity.setMessage(null);
        return httpResponseEntity;
    }

    /**
     * 根据id删除项目
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/deleteProjectById",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteProjectById(ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
           
        return httpResponseEntity;
    }

    /**
     * 添加项目
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/addProjectInfo",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
            
        return httpResponseEntity;
    }

    /**
     * 根据项目id修改项目
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/modifyProjectInfo",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
            
        return httpResponseEntity;
    }



    /**
     * 查询全部项目的名字接口
     * @return
     */
    @RequestMapping(value = "/queryAllProjectName",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryAllProjectName() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
          
        return httpResponseEntity;
    }
}
