package com.laoyin.consumer;

import com.laoyin.MessageEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component

public class FirstConsumer {

    @RabbitListener(queues = "FIRST_QUEUE" ,containerFactory = "rabbitListenerContainerFactory")
    @RabbitHandler
    public void process(@Payload MessageEntity messageEntity , Channel channel, @Headers Map<String,Object> headers){
        System.out.println(channel.getNextPublishSeqNo());
        System.out.println(headers.get(AmqpHeaders.DELIVERY_TAG));

        System.out.println(" first queue received msg : " + messageEntity.getName());
    }
}
