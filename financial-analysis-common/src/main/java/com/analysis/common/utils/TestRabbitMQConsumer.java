package com.analysis.common.utils;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author lvshuzheng
 * @className TestRabbitMQConsumer
 * @description
 * @date 2020/4/29
 */
@Component
public class TestRabbitMQConsumer {
    /**
    * @Description: listen1、listen2共同消费同一个数据
    * @param: object
    * @return: void
    * @Date: 2020/4/29
    */
    @RabbitListener(queuesToDeclare=@Queue("test1"))
    public void listen1(Object object){
        System.out.println("test1: " + object);
    }
    @RabbitListener(queuesToDeclare=@Queue("test1"))
    public void listen11(Object object){
        System.out.println("test11: " + object);
    }


    /**
    * @Description:  fanoutListen1、fanoutListen2订阅广播模式  生产者中指定的exchange有效，routingkey不生效 写""即可
    * @param: object
    * @return: void
    * @Date: 2020/4/29
    */
    @RabbitListener(bindings =@QueueBinding(
            value = @Queue(value = "fanoutTestQueue1"),
            exchange = @Exchange(value = "fanout.test.exchange",type = ExchangeTypes.FANOUT)
    ))
    public void fanoutListen1(Object object){
        System.out.println("fanoutListener1: " + object);
    }

    @RabbitListener(bindings =@QueueBinding(
            value = @Queue(value = "fanoutTestQueue2"),
            exchange = @Exchange(value = "fanout.test.exchange",type = ExchangeTypes.FANOUT)
    ))
    public void fanoutListen2(Object object){
        System.out.println("fanoutListener2: " + object);
    }

    /**
    * @Description:  directListen1、directListen2订阅路由模式 生产者指定exchange、routing 消费者使用key监听必须保证
    * 监听的key与生产的routingKey一致 exchange前缀匹配上即可
    * @param: object 
    * @return: void
    * @Date: 2020/4/29 
    */ 
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "direct.test.queue1"),
            exchange = @Exchange(value = "direct.exchange.test1",type = ExchangeTypes.DIRECT),
            key = "direct12345"
    ))
    public void directListen1(Object object){
        System.out.println("directListen1: " + object);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "direct.test.queue2"),
            exchange = @Exchange(value = "direct.test.exchange2",type = ExchangeTypes.DIRECT),
            key = "direct2"
    ))
    public void directListen2(Object object){
        System.out.println("directListen2: " + object);
    }

    /**
    * @Description:  topicListen1、topicListen2通过key监听生产者routingKey 支持.*匹配
    * @param: object
    * @return: void
    * @Date: 2020/4/29
    */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "topic.test.queue1"),
            exchange = @Exchange(value = "topic1.exchange.test",type = ExchangeTypes.TOPIC),
            key = "test.*"
    ))
    public void topicListen1(Object object){
        System.out.println("topicListen1: " + object);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "topic.test.queue2"),
            exchange = @Exchange(value = "topic1.exchange.test",type = ExchangeTypes.TOPIC),
            key = "a.test.*"
    ))
    public void topicListen2(Object object){
        System.out.println("topicListen2: " + object);
    }



}
