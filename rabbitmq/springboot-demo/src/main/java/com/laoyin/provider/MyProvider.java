package com.laoyin.provider;

import com.laoyin.MessageEntity;
import com.laoyin.confirm.ConfirmCallBackListener;
import com.laoyin.confirm.ReturnCallBackListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MyProvider {

    @Autowired
    private AmqpTemplate updatedRabbitTemplate;

    public void send(){
        // 发送4条消息
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setAge(11);
        messageEntity.setName("name");

        updatedRabbitTemplate.convertAndSend("","FIRST_QUEUE",messageEntity);
//        updatedRabbitTemplate.convertAndSend("TOPIC_EXCHANGE","shanghai.test.teacher","-------- a topic msg : shanghai.teacher");
//        updatedRabbitTemplate.convertAndSend("TOPIC_EXCHANGE","changsha.test.student","-------- a topic msg : changsha.student");
//        updatedRabbitTemplate.convertAndSend("FANOUT_EXCHANGE","","-------- a fanout msg");

    }

}
