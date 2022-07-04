package com.aim.questionnaire.service.impl;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.ExcelExport;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.QuestionnaireMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.entity.UserEntity;
import com.aim.questionnaire.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by wln on 2018\8\9 0009.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private QuestionnaireMapper questionnaireEntityMapper;

    public void exportUser(Map<String, Object> params, HttpServletResponse response){
        ExcelExport ee = new ExcelExport("用户模板");
        String[] header = new String[]{"用户账号","用户密码", "开始时间", "结束时间"};
        List<Map<String, Object>> list = new ArrayList<>();
        //PageInfo<UserEntity> tempList = userService.queryUserList(params);
        List<UserEntity> tempList = userEntityMapper.queryUserList(params);
        for(int i = 0;i<tempList.size();i++){
            UserEntity temp = tempList.get(i);
            Map<String,Object> temp1 = new LinkedHashMap();
            temp1.put("用户账号",temp.getUsername());
            temp1.put("用户类型",temp.getPassword());
            temp1.put("开始时间",temp.getStartTime());
            temp1.put("结束时间",temp.getStopTime());

            //System.out.println(tempList.get(i).getName());
            list.add(temp1);
        }

        //System.out.println();
        ee.addSheetByMap("用户模板", list, header);
        ee.export(response);
    }
    public PageInfo<UserEntity> queryUserList(Map<String,Object> map) {

        String pageNum = (map.get("pageNum")).toString();
        String pageSize = (map.get("pageSize")).toString();


        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<UserEntity> userEntities = userEntityMapper.queryUserList(map);
        PageInfo<UserEntity> userEntityPageInfo = new PageInfo<>(userEntities);


        return userEntityPageInfo;
    }

    /**
     * 创建用户的基本信息
     * @param map
     * @return
     */
    public int addUserInfo(Map<String,Object> map) {

        if(map.get("username") != null) {
            int userResult = userEntityMapper.queryExistUser(map);
            if(userResult != 0) {
                //用户名已经存在
                return 3;
            }
        }

//        String id = UUIDUtil.getOneUUID();
//        map.put("id",id);
        //创建时间
        Date date = DateUtil.getCreateTime();
        map.put("creationDate",date);
        map.put("lastUpdateDate",date);
        //创建人
        String user = "admin";
        map.put("createdBy",user);
        map.put("lastUpdatedBy",user);
        //前台传入的时间戳转换
        String startTimeStr = map.get("startTime").toString();
        String endTimeStr = map.get("stopTime").toString();
        Date startTime = DateUtil.getMyTime(startTimeStr);
        Date endTime = DateUtil.getMyTime(endTimeStr);
        map.put("startTime",startTime);
        map.put("stopTime",endTime);

        int result = userEntityMapper.addUserInfo(map);
        return result;
    }

    /**
     * 编辑用户的基本信息
     * @param map
     * @return
     */
    public int modifyUserInfo(Map<String, Object> map) {

        return 0;
    }

    /**
     * 修改用户状态
     * @param map
     * @return
     */
    public int modifyUserStatus(Map<String, Object> map) {

        return 0;
    }

    /**
     * 根据id查询用户信息
     * @param userEntity
     * @return
     */
    public Map<String,Object> selectUserInfoById(UserEntity userEntity) {

        return null;
    }

    /**
     * 删除用户信息
     * @param userEntity
     * @return
     */
    public int deteleUserInfoById(UserEntity userEntity) {

        return 0;
    }

    public UserEntity login(String username, String password) {
        return userEntityMapper.login(username,password);
    }
}
