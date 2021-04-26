package com.laoying;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.HashMap;
import java.util.Map;

public class NewTask {
    private static final String TASK_QUEUE_NAME = "task_queue";
    private static final String EXCHANGE = "exchange_work";
    public static final String ROUTING_KEY = "routingKeyWork";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(
                "192.168.73.133");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 准备备份交换器参数  名字：myAe
            Map<String, Object> argsMap = new HashMap<>();
            argsMap.put("alternate-exchange", "myAe");
            // 声明不同交换器，direct方式 ，设置备份交换器argsMap
            channel.exchangeDeclare("normalExchange", "direct", true, false, argsMap);
            //声明 备份交换器myAe，fanout方式
            channel.exchangeDeclare("myAe", "fanout", true, false, null);
            // 声明一个队列，并绑定普通交换器
            channel.queueDeclare("normalQueue", true, false, false, null);
            channel.queueBind("normalQueue", "normalExchange", "normalKey");
            // 声明一个队列，并绑定备份交换器
            channel.queueDeclare("unroutedQueue", true, false, false, null);
            channel.queueBind("unroutedQueue", "myAe", "");

            String message = "";
            String routingKey = "normalKey";
            for (int i = 0; i < 2; i++) {
                if (i == 1) {
                    routingKey = "";
                }
                message = "...消息" + i;
                channel.basicPublish("normalExchange", routingKey, true,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes("UTF-8"));

                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}