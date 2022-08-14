package com.analysis.common.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Description: 邮件发送demo
 * @param: null
 * @return:
 * @Date: 2020/4/30
 */
@Service
public class MailSenders {

    private static Logger logger = LoggerFactory.getLogger(MailSenders.class);

    private static String from = "1144950389@qq.com";

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * @Description: 发送简单邮件
     * @param: to  收件人
     * @param: subject  主题
     * @param: content  内容
     * @return: void
     * @Date: 2018/12/8
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        try {
            //JavaMailSender javaMailSender = new JavaMailSenderImpl();
            javaMailSender.send(simpleMailMessage);
            logger.info("简单邮件发送成功");
        } catch (Exception e) {
            logger.error("简单邮件发送失败");
            e.printStackTrace();
        }
    }

    /**
     * @Description: 发送html邮件
     * @param: to
     * @param: subject
     * @param: content
     * @return: void
     * @Date: 2018/12/8
     */
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("html邮件发送失败");
            e.printStackTrace();
        }

    }

    /**
     * @Description: 发送带附件的邮件
     * @param: to
     * @param: subject
     * @param: content
     * @param: filePath
     * @return: void
     * @Date: 2018/12/8
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, fileSystemResource);
            javaMailSender.send(message);
            logger.info("带附件的邮件发送成功");
        } catch (MessagingException e) {
            logger.error("带附件的邮件发送失败");
            e.printStackTrace();
        }
    }

    /**
     * @Description: 带本地资源的邮件
     * @param: to
     * @param: subject
     * @param: content
     * @param: rscPath  资源路径
     * @param: rscId    资源编号
     * @return: void
     * @Date: 2018/12/8
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath,
                                       String rscId) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo("m17854282392@163.com");
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource resource = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, resource);
            javaMailSender.send(message);
            logger.info("带图片资源的邮件发送成功");
        } catch (MessagingException e) {
            logger.error("带图片资源的邮件发送失败");
            e.printStackTrace();
        }

    }


}
