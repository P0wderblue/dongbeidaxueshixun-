package com.userManagement.controller;

import com.userManagement.beans.HttpResponseEntity;
import com.userManagement.beans.ResPageData;
import com.userManagement.common.Constans;
import com.userManagement.dao.UserEntityMapper;
import com.userManagement.dao.entity.UserEntity;
import com.userManagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
     * @param
     * @return
     */
    @RequestMapping(value="/userLogin",method= RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity userLogin(@RequestBody UserEntity userEntity) {       //接收请求体到UserEntity对象中
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        try {
            List<UserEntity> hasUser = userEntityMapper.selectUserInfo(userEntity);     //从数据库中查询用户名，密码符合的User
            if(CollectionUtils.isEmpty(hasUser) ) {         //若查询结果为空
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage(Constans.LOGIN_USERNAME_PASSWORD_MESSAGE);
            }else {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setData(userEntity);
                httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
            }

        } catch (Exception e) {
            logger.info("userLogin 用户登录>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 查询用户列表（模糊搜索）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryUserList",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryUserList(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        Integer pageNumber = (Integer) map.get("pageNumber");
        Integer pageSize = (Integer) map.get("pageSize");
        List<Map<String, Object>> userList = userEntityMapper.queryUserList(map);

        ResPageData resPageData = new ResPageData();
        resPageData.setPageNumber(pageNumber);
        resPageData.setPageSize(pageSize);
        resPageData.setList(userList);
//        List<Map<String, Object>> list = new ArrayList<>();
//        Integer base =(pageNumber -1) * pageSize;
//        for (int i=0;i< pageSize && i<userList.size() - base;i++){
//            list.add(userList.get(i + base));
//        }
//        System.out.println(list);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(resPageData);
        httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
        return httpResponseEntity;
    }
    /**
     * 创建用户的基本信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/addUserInfo",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addUserInfo(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int result = userService.addUserInfo(map);
            if(result == 3) {       //数据库中已经有相同的
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
     * @param map
     * @return
     */
    @RequestMapping(value = "/modifyUserInfo",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUserInfo(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }


    /**
     *  根据用户id查询用户基本信息
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/selectUserInfoById",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity selectUserInfoById(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }



    /**
     * 修改用户状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/modifyUserStatus",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUserStatus(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }
    /**
     *  删除用户信息
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/deleteUserInfoById",method = RequestMethod.POST, headers = "Accept=application/json")
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
     * @return
     */
    @RequestMapping(value = "/error")
    public HttpResponseEntity logout() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }
}
