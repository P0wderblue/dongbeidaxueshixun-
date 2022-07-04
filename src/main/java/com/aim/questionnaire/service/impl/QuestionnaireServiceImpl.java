package com.aim.questionnaire.service.impl;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.dao.QuestionnaireMapper;
import com.aim.questionnaire.entity.ProjectEntity;
import com.aim.questionnaire.entity.QuestionnaireEntity;
import com.aim.questionnaire.service.ProjectService;
import com.aim.questionnaire.service.QuestionnaireService;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service("questionnaireService")
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Resource
    private QuestionnaireMapper questionnaireMapper;
    @Resource
    private ProjectService projectService;
    //发送邮件的类
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @Override
    public List<QuestionnaireEntity> queryAll() {
        return this.questionnaireMapper.queryAll();
    }

    /**
     * 查询所有数据带条件
     *
     * @param maps 实例对象
     * @return 实例对象集合
     */
    @Override
    public List<QuestionnaireEntity> queryList(Map<String, Object> maps) {
        return this.questionnaireMapper.queryList(maps);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QuestionnaireEntity queryById(Integer id) {
        return this.questionnaireMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param questionnaire 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public QuestionnaireEntity insert(QuestionnaireEntity questionnaire) {
        //查询重复的调查名称
        if (questionnaireMapper.queryInquiryMame(questionnaire.getInquiryName()) > 0) {
            throw new UnknownAccountException("问卷调查名称已重复，请修改后重新提交!");
        }
        //根据项目id查询项目名称
        ProjectEntity projectEntity = projectService.queryById(questionnaire.getProjectId());
        //保存问卷，返回问卷id，调用问卷问题表，保存问卷问题。
        questionnaire.setUserId("1");
        questionnaire.setCreateName("admin");
        questionnaire.setCreateDate(new Date());
        questionnaire.setProjectName(projectEntity.getProjectName());
        this.questionnaireMapper.insert(questionnaire);

        return questionnaire;
    }

    /**
     * 修改数据
     *
     * @param questionnaire 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public QuestionnaireEntity update(QuestionnaireEntity questionnaire) {
        if (questionnaireMapper.queryInquiryMameId(questionnaire.getInquiryName(), questionnaire.getId()) > 0) {
            throw new UnknownAccountException("问卷调查名称已重复，请修改后重新提交!");
        }
        QuestionnaireEntity questionnaireEntity = questionnaireMapper.queryById(questionnaire.getId());
        if (questionnaireEntity.getIsStart() == 0) {
            throw new UnknownAccountException("该问卷是启动状态，请停止后进行修改!");
        }
        if (questionnaireEntity.getClickNumber() > 0) {
            throw new UnknownAccountException("该问卷已被查看，不能修改!");
        }
        questionnaire.setUpdateUserName("admin");
        questionnaire.setUpdateDate(new Date());

        //根据项目id查询项目名称
        ProjectEntity projectEntity = projectService.queryById(questionnaire.getProjectId());
        questionnaire.setProjectName(projectEntity.getProjectName());
        //修改问卷
        this.questionnaireMapper.update(questionnaire);
        //修改问卷问题
        return this.queryById(questionnaire.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(Integer id, Boolean isProject) {
        QuestionnaireEntity questionnaireEntity = questionnaireMapper.queryById(id);
        if (questionnaireEntity.getIsStart() == 0) {
            throw new UnknownAccountException("该问卷是启动状态，请停止后进行删除!");
        }
        if (questionnaireEntity.getClickNumber() > 0 && isProject == false) {
            throw new UnknownAccountException("该问卷已被查看，不能删除!");
        }
        questionnaireEntity.setIsDelete(1);
        //删除问卷
        return this.questionnaireMapper.update(questionnaireEntity) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteProjectById(Integer id) {
        QuestionnaireEntity questionnaireEntity = questionnaireMapper.queryById(id);
        if (questionnaireEntity.getIsStart() == 0) {
            throw new UnknownAccountException("该项目下有启动的问卷，请停止后进行删除!");
        }
        if (questionnaireEntity.getClickNumber() > 0) {
            throw new UnknownAccountException("该项目下有被查看的问卷，不能删除!");
        }
        questionnaireEntity.setIsDelete(1);
        //删除问卷
        return this.questionnaireMapper.update(questionnaireEntity) > 0;
    }

    /**
     * 统计符合条件数据的数量
     *
     * @param maps 查询参数
     * @return 查询结果
     */
    @Override
    public int queryTotal(Map<String, Object> maps) {
        return questionnaireMapper.queryTotal(maps);
    }

    @Override
    public int editQuestionnaireStart(Integer id) {
        QuestionnaireEntity questionnaireEntity = questionnaireMapper.queryById(id);
        if (questionnaireEntity.getIsStart() == 1) {
            questionnaireEntity.setIsStart(0);
        } else {
            questionnaireEntity.setIsStart(1);
        }
        return this.questionnaireMapper.update(questionnaireEntity);
    }

    @Override
    public QuestionnaireEntity queryInfo(Integer id) {
        QuestionnaireEntity questionnaireEntity = questionnaireMapper.queryById(id);
        if (questionnaireEntity.getIsStart() == 0) {
            throw new UnknownAccountException("该问卷是启动状态，请停止后进行修改!");
        }
        if (questionnaireEntity.getClickNumber() > 0) {
            throw new UnknownAccountException("该问卷已被查看，不能修改!");
        }
        return questionnaireEntity;
    }

    /**
     * 根据问卷id查询问卷下的问题
     *
     * @param questionnaireIssueId 问卷id
     * @return 查询结果
     */
    @Override
    @Transactional
    public QuestionnaireEntity queryQuestionnaireIssueId(Integer questionnaireIssueId) {
        QuestionnaireEntity questionnaireEntity = questionnaireMapper.queryById(questionnaireIssueId);
        if (!DateUtil.isEffectiveDate(new Date(), questionnaireEntity.getStartTime(), questionnaireEntity.getEndTime())) {
            throw new UnknownAccountException("当前时间不在问卷的答题时间，无法进行答题!");
        }
        if (questionnaireEntity.getIsStart() == 1) {
            throw new UnknownAccountException("该问卷已停止，无法进行答题!");
        }
        //添加点击量
        questionnaireEntity.setClickNumber(questionnaireEntity.getClickNumber());
        questionnaireMapper.update(questionnaireEntity);
        return questionnaireEntity;
    }

    @Override
    public QuestionnaireEntity insertImport(QuestionnaireEntity questionnaire) {
        //查询重复的调查名称
        if (questionnaireMapper.queryInquiryMame(questionnaire.getInquiryName()) > 0) {
            throw new UnknownAccountException("问卷调查名称已重复，请修改后重新提交!");
        }
        //根据项目id查询项目名称
        ProjectEntity projectEntity = projectService.queryById(questionnaire.getProjectId());
        //保存问卷，返回问卷id，调用问卷问题表，保存问卷问题。
        questionnaire.setUserId("1");
        questionnaire.setCreateName("admin");
        questionnaire.setCreateDate(new Date());
        questionnaire.setProjectName(projectEntity.getProjectName());

        QuestionnaireEntity importQuestionnaire = questionnaireMapper.queryById(questionnaire.getImportQuestionnaireId());
        questionnaire.setQuestionName(importQuestionnaire.getQuestionName());
        questionnaire.setQuestionContent(importQuestionnaire.getQuestionContent());
        questionnaire.setQuestionList(importQuestionnaire.getQuestionList());


        this.questionnaireMapper.insert(questionnaire);
        return questionnaire;
    }

    @Override
    public QuestionnaireEntity updateImport(QuestionnaireEntity questionnaire) {
        if (questionnaireMapper.queryInquiryMameId(questionnaire.getInquiryName(), questionnaire.getId()) > 0) {
            throw new UnknownAccountException("问卷调查名称已重复，请修改后重新提交!");
        }
        QuestionnaireEntity questionnaireEntity = questionnaireMapper.queryById(questionnaire.getId());
        if (questionnaireEntity.getIsStart() == 0) {
            throw new UnknownAccountException("该问卷是启动状态，请停止后进行修改!");
        }
        if (questionnaireEntity.getClickNumber() > 0) {
            throw new UnknownAccountException("该问卷已被查看，不能修改!");
        }
        questionnaire.setUpdateUserName("admin");
        questionnaire.setUpdateDate(new Date());

        //根据项目id查询项目名称
        ProjectEntity projectEntity = projectService.queryById(questionnaire.getProjectId());
        questionnaire.setProjectName(projectEntity.getProjectName());

        //查詢原來的问卷
        QuestionnaireEntity importQuestionnaire = questionnaireMapper.queryById(questionnaire.getImportQuestionnaireId());
        questionnaire.setQuestionName(importQuestionnaire.getQuestionName());
        questionnaire.setQuestionContent(importQuestionnaire.getQuestionContent());
        questionnaire.setQuestionList(importQuestionnaire.getQuestionList());
        //修改问卷
        this.questionnaireMapper.update(questionnaire);
        return this.queryById(questionnaire.getId());
    }

    @Override
    public QuestionnaireEntity queryObject(Integer id) {
        return questionnaireMapper.queryById(id);
    }

    @Override
    public int sendEmail(String email, Integer questionnaireId,String emailContent) {
        try {
            email(email,questionnaireId, emailContent);
        } catch (MailException e) {
            throw new UnknownAccountException("邮件发送失败");
        } catch (Exception e) {
            throw new UnknownAccountException("邮件发送失败");
        }
        return 0;
    }

    @Override
    public int updateData(QuestionnaireEntity questionnaire) {
        return questionnaireMapper.update(questionnaire);
    }

    private void email(String email, Integer questionnaireId,String emailContent) throws Exception {
        //创建一个配置文件并保存
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("446715229@qq.com", "yonvlsklttwzcaga");
            }
        });

        //开启debug模式
        session.setDebug(true);
        //获取连接对象
        Transport transport = session.getTransport();
        //连接服务器
        transport.connect("smtp.qq.com", "446715229@qq.com", "yonvlsklttwzcaga");
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("446715229@qq.com"));
        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //邮件标题
        mimeMessage.setSubject("参与答卷");
//        //邮件内容
//        mimeMessage.setContent("您好！\n" +
//                "请填写一下我的问卷。\n" +
//                "问卷地址：" + "http://127.0.0.1:8085/pages/answerQuestionnaire.html?questionnaireIssueId=" + questionnaireId +
//                "\n" +
//                "提示：此问卷地址仅允许填写一次，请勿转发。\n" ,"text/html;charset=UTF-8");

        //邮件内容
        mimeMessage.setContent( emailContent+" 问卷地址：" + "http://127.0.0.1:8085/pages/answerQuestionnaire.html?questionnaireIssueId=" + questionnaireId +
                "\n" ,"text/html;charset=UTF-8");

        //发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        //关闭连接
        transport.close();
    }
}

