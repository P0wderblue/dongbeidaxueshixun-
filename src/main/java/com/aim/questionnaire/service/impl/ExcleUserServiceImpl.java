package com.aim.questionnaire.service.impl;

import com.aim.questionnaire.common.utils.ExcelExport;
import com.aim.questionnaire.dao.ExcleUserMapper;
import com.aim.questionnaire.entity.ExcleUserEntity;
import com.aim.questionnaire.service.ExcleUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service("excleUserService")
public class ExcleUserServiceImpl implements ExcleUserService {
    @Resource
    private ExcleUserMapper excleUserMapper;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<ExcleUserEntity> queryAll() {
        return this.excleUserMapper.queryAll();
    }

    /**
     * 查询所有数据带条件
     *
     * @param maps 实例对象
     * @return 实例对象集合
     */
    @Override
    public List<ExcleUserEntity> queryList(Map<String, Object> maps) {

        return this.excleUserMapper.queryList(maps);
    }
    /**
     * 查询所有数据带条件
     *
     * @param params,response  实例对象
     * @return 实例对象集合
     */
    @Override
    public void exportUser(@RequestParam Map<String, Object> params, HttpServletResponse response){
        ExcelExport ee = new ExcelExport("用户模板");
        String[] header = new String[]{"学号","用户类型", "姓名", "院校", "专业", "班级", "性别", "微信号", "QQ号", "手机号", "电子邮箱"};
        List<Map<String, Object>> list = new ArrayList<>();
        List<ExcleUserEntity> tempList = this.queryList(params);

        for(int i = 0;i<tempList.size();i++){
            ExcleUserEntity temp = tempList.get(i);
            Map<String,Object> temp1 = new LinkedHashMap();
            temp1.put("学号",temp.getStudentNumber());
            temp1.put("用户类型",temp.getRoleType());
            temp1.put("姓名",temp.getName());
            temp1.put("院校",temp.getAcademy());
            temp1.put("专业",temp.getMajor());
            temp1.put("班级",temp.getClassGrade());
            temp1.put("性别",temp.getSex());
            temp1.put("微信号",temp.getWechat());
            temp1.put("QQ号",temp.getQq());
            temp1.put("手机号",temp.getMobile());
            temp1.put("电子邮箱",temp.getMailbox());
            list.add(temp1);
        }

        ee.addSheetByMap("用户模板", list, header);
        ee.export(response);
    }


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ExcleUserEntity queryById(Integer id) {

        return excleUserMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param excleUser 实例对象
     * @return 实例对象
     */
    @Override
    public ExcleUserEntity insert(ExcleUserEntity excleUser) {
        this.excleUserMapper.insert(excleUser);
        return excleUser;
    }

    /**
     * 修改数据
     *
     * @param excleUser 实例对象
     * @return 实例对象
     */
    @Override
    public ExcleUserEntity update(ExcleUserEntity excleUser) {
        this.excleUserMapper.update(excleUser);
        return this.queryById(excleUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.excleUserMapper.deleteById(id) > 0;
    }

    /**
     * 查询符合条件的数据
     *
     * @param maps 请求参数
     * @return 查询结果
     */
    @Override
    public int queryTotal(Map<String, Object> maps) {
        return this.excleUserMapper.queryTotal(maps);
    }

    @Transactional
    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws IOException {

        boolean notNull = false;
        List<ExcleUserEntity> userList = new ArrayList<ExcleUserEntity>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new UnknownAccountException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        ExcleUserEntity user;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            user = new ExcleUserEntity();
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            String studentNumber = row.getCell(0).getStringCellValue();
            if (!studentNumber.isEmpty()) {
                user.setStudentNumber(studentNumber);

                if(null!=row.getCell(1)){
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    user.setRoleType(row.getCell(1).getStringCellValue());
                }
                if(null!=row.getCell(2)){
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    user.setName(row.getCell(2).getStringCellValue());
                }
                if(null!=row.getCell(3)){
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    user.setAcademy(row.getCell(3).getStringCellValue());
                }
                if(null!=row.getCell(4)){
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    user.setMajor(row.getCell(4).getStringCellValue());
                }
                if(null!=row.getCell(5)){
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    user.setClassGrade(row.getCell(5).getStringCellValue());
                }
                if(null!=row.getCell(6)){
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    user.setSex(row.getCell(6).getStringCellValue());
                }
                if(null!=row.getCell(7)){
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    user.setWechat(row.getCell(7).getStringCellValue());
                }
                if(null!=row.getCell(8)){
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    user.setQq(row.getCell(8).getStringCellValue());
                }
                if(null!=row.getCell(9)){
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    user.setMobile(row.getCell(9).getStringCellValue());
                }
                if(null!=row.getCell(10)){
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    user.setMailbox(row.getCell(10).getStringCellValue());
                }
                user.setCreateTime(new Date());
                userList.add(user);
            }
        }
        for (ExcleUserEntity userResord : userList) {
            String userId = excleUserMapper.selectStudentNumber(userResord.getStudentNumber());
            if (StringUtils.isBlank(userId)) {
                excleUserMapper.insert(userResord);
            } else {
                userResord.setId(Integer.parseInt(userId));
                excleUserMapper.update(userResord);
            }
        }
        return notNull;

    }

}

