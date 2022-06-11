package com.projectManagement.service;

import com.projectManagement.dao.ProjectEntityMapper;
import com.projectManagement.dao.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wln on 2018\8\6 0006.
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectEntityMapper projectEntityMapper;


    /**
     * 添加项目
     * @param projectEntity
     * @return
     */
    public int addProjectInfo(ProjectEntity projectEntity, String user) {
        
        return 0;
    }

    /**
     * 修改项目
     * @param projectEntity
     * @return
     */
    public int modifyProjectInfo(ProjectEntity projectEntity,String user) {
       
        return 0;
    }

    /**
     * 删除项目
     * @param projectEntity
     * @return
     */
    public int deleteProjectById(ProjectEntity projectEntity) {
       
        return 0;
    }

    /**
     * 查询项目列表
     * @param projectEntity
     * @return
     */
    public List<Map<String, Object>> queryProjectList(ProjectEntity projectEntity) {
//        List<Object> resultList = new ArrayList<Object>();
        List<Map<String, Object>> resultList = projectEntityMapper.queryProjectList(projectEntity);
        return resultList;
//        return resultList;

    }

    /**
     * 查询全部项目的名字接口
     * @return
     */
    public List<Map<String,Object>> queryAllProjectName() {
        return null;
    }
}
