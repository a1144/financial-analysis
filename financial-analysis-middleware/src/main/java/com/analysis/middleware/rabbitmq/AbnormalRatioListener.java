package com.analysis.middleware.rabbitmq;

import com.analysis.common.mail.MailSenders;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

/**
 * @author lvshuzheng
 * @className AbnormalRatioListener
 * @description 监听rabbitmq中涨跌率波动大的消息 并发送邮件
 * @date 2020/4/30
 */
@Component
public class AbnormalRatioListener {
    @Autowired
    private MailSenders mailSenders;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "abnormal.ratio.queue"),
            exchange = @Exchange(value = "financial.analysis.abnormal.ratio.exchange",type = ExchangeTypes.DIRECT),
            key = "abnormal.ratio.routingKey"
    ))
    public void abnormalRatioListener(Message message){
        System.out.println(message);
        System.out.println(message.toString());
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getBody());
                ObjectInputStream objectInputStream =  new ObjectInputStream(byteArrayInputStream)){
            Map<String,Object> messageMap = (Map)objectInputStream.readObject();
            String subject = (String)messageMap.get("name");
            List<Map<String,Object>> userMessages = (List)messageMap.get("userMessages");
            userMessages.stream().forEach(userMessage -> {
                String email = (String)userMessage.get("userEmail");
                String userName = (String)userMessage.get("userName");
                String content = "尊敬的: " + userName + "，您订阅的股票存在异常波动: " + messageMap.get("message").toString();
                mailSenders.sendSimpleMail(email,subject,content);
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
