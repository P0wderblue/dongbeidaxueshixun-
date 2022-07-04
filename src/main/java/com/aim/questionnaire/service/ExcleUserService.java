package com.aim.questionnaire.service;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.entity.ExcleUserEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExcleUserService {

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    List<ExcleUserEntity> queryAll();

    /**
     * 查询所有数据带条件
     *
     * @param maps 实例对象
     * @return 实例对象集合
     */
    List<ExcleUserEntity> queryList(Map<String,Object> maps);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExcleUserEntity queryById(Integer id);

    /**
     * 新增数据
     *
     * @param excleUser 实例对象
     * @return 实例对象
     */
    ExcleUserEntity insert(ExcleUserEntity excleUser);

    /**
     * 修改数据
     *
     * @param excleUser 实例对象
     * @return 实例对象
     */
    ExcleUserEntity update(ExcleUserEntity excleUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询符合条件的数据
     * @param maps 请求参数
     * @return 查询结果
     */
    int queryTotal(Map<String,Object> maps);

    boolean batchImport(String fileName, MultipartFile file) throws IOException;
    void exportUser(@RequestParam Map<String, Object> params, HttpServletResponse response);
}
