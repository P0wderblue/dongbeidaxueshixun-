package com.aim.questionnaire.service;

import com.aim.questionnaire.entity.UserEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by wln on 2018\8\9 0009.
 */
@Service
public interface UserService {

    void exportUser(Map<String, Object> params, HttpServletResponse response);


    public PageInfo<UserEntity> queryUserList(Map<String, Object> map);

    /**
     * 创建用户的基本信息
     * @param map
     * @return
     */
    public int addUserInfo(Map<String,Object> map) ;

    /**
     * 编辑用户的基本信息
     * @param map
     * @return
     */
    public int modifyUserInfo(Map<String, Object> map) ;

    /**
     * 修改用户状态
     * @param map
     * @return
     */
    public int modifyUserStatus(Map<String, Object> map);

    /**
     * 根据id查询用户信息
     * @param userEntity
     * @return
     */
    public Map<String,Object> selectUserInfoById(UserEntity userEntity) ;

    /**
     * 删除用户信息
     * @param userEntity
     * @return
     */
    public int deteleUserInfoById(UserEntity userEntity);

    public UserEntity login(String username, String password);
}
