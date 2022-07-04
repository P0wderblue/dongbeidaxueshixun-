package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.ExcelExport;
import com.aim.questionnaire.common.utils.PageUtils;
import com.aim.questionnaire.common.utils.Query;
import com.aim.questionnaire.entity.ExcleUserEntity;
import com.aim.questionnaire.service.ExcleUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excleUser")
public class ExcleUserController {
    /**
     * 服务对象
     */
    @Resource
    private ExcleUserService excleUserService;

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
        List<ExcleUserEntity> list = excleUserService.queryList(query);
        //查询下一页有没有数据
        int count = excleUserService.queryTotal(query);
        PageUtils pageUtils = new PageUtils(list, count, query.getLimit(), query.getPage());
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(pageUtils);
        return httpResponseEntity;
    }


    /**
     * 导入excel方法
     *
     * @param file 请求文件
     * @return 导路数据结果
     */
    @RequestMapping("/importExcel")
    public HttpResponseEntity importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String fileName = file.getOriginalFilename();
        try {
            excleUserService.batchImport(fileName, file);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage("导入用户成功");
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }
        return httpResponseEntity;
    }

    @RequestMapping("/exportUser")
    public HttpResponseEntity exportUser(@RequestParam Map<String, Object> params, HttpServletResponse response) {
       excleUserService.exportUser(params, response);
        return null;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("queryById/{id}")
    public HttpResponseEntity queryById(@PathVariable("id") Integer id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        ExcleUserEntity excleUser=excleUserService.queryById(id);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(excleUser);
        return httpResponseEntity;
    }


    /**
     * 编辑数据
     *
     * @param excleUser 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public HttpResponseEntity edit(@RequestBody ExcleUserEntity excleUser) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        this.excleUserService.update(excleUser);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
        return httpResponseEntity;
    }

}


