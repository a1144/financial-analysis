package com.analysis.common.utils;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lvshuzheng
 * @className RabbitMqSender
 * @description
 * @date 2020/4/29
 */
@Component
public class RabbitMQSender {
    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
    * @Description:  只通过路由发送消息
    * @param: routingKey
    * @param: message 
    * @return: void
    * @Date: 2020/4/29 
    */ 
    public void sendByRoutingKey(String routingKey,Object message){
        amqpTemplate.convertAndSend(routingKey,message);
    }

    public void sendByExchangeAndRoutingKey(String exchange,String routingKey,Object message){
        amqpTemplate.convertAndSend(exchange,routingKey,message);
    }

}
