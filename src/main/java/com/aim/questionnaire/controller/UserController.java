package com.aim.questionnaire.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aim.questionnaire.common.utils.ExcelExport;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.entity.UserEntity;
import com.aim.questionnaire.service.UserService;

import javax.servlet.http.HttpServletResponse;


/**
 * Created by wln on 2018\8\9 0009.
 */
@RestController
@RequestMapping("/admin")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserEntityMapper userEntityMapper;

    /**
     * 用户登录
     *
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity userLogin(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {

            UserEntity user = userService.login(userEntity.getUsername(), userEntity.getPassword());
            if (null != user) {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setData(user);
                httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
            } else {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage(Constans.LOGIN_USERNAME_PASSWORD_MESSAGE);
            }
        } catch (Exception e) {
            logger.info("userLogin 用户登录>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(e.getLocalizedMessage());
        }
        return httpResponseEntity;
    }

    /**
     * 用户退出
     *
     * @return
     */
    @RequestMapping("/loginout")
    public HttpResponseEntity loginout() {
        HttpResponseEntity result = new HttpResponseEntity();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        result.setCode(Constans.SUCCESS_CODE);
        result.setMessage("退出成功");
        return result;
    }

    /**
     * 查询用户列表（模糊搜索）
     *
     * @param map pageNum  pageSize
     * @return
     */
    @RequestMapping(value = "/queryUserList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryUserList(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        PageInfo<UserEntity> pageInfo = userService.queryUserList(map);

        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        httpResponseEntity.setData(pageInfo);
        return httpResponseEntity;
    }

    /**
     * 创建用户的基本信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addUserInfo(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int result = userService.addUserInfo(map);
            if(result == 3) {
                httpResponseEntity.setCode(Constans.USER_USERNAME_CODE);
                httpResponseEntity.setMessage(Constans.USER_USERNAME_MESSAGE);
            }else {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
            }
        } catch (Exception e) {
            logger.info("addUserInfo 创建用户的基本信息>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 编辑用户的基本信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUserInfo(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }


    /**
     * 根据用户id查询用户基本信息
     *
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/selectUserInfoById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity selectUserInfoById(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }

    @RequestMapping("/exporteUser")
    public HttpResponseEntity exportUser(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        userService.exportUser( params,  response);

        return null;
    }
    /**
     * 修改用户状态
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/modifyUserStatus", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUserStatus(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }

    /**
     * 删除用户信息
     *
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/deleteUserInfoById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deteleUserInfoById(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        int sta =userEntityMapper.deteleUserInfoById(userEntity);
        if (sta > 0){
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        }
        return httpResponseEntity;
    }


    /**
     * 用户没有权限
     *
     * @return
     */
    @RequestMapping(value = "/error")
    public HttpResponseEntity logout() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }
}
