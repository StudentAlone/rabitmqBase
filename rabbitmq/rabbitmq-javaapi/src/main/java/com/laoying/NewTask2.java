package com.laoying;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class NewTask2 {
    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "queue_demo";

    public static void main(String[] args) throws IOException,
            TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(
                "192.168.73.133");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 声明死信交换器
            channel.exchangeDeclare("exchange.dlx", "direct", true);
            // 什么普通交换器
            channel.exchangeDeclare("exchange.normal", "fanout", true);
            // 设置TTL和 DLX 和 DLK
            Map<String, Object> arg = new HashMap<>();
            arg.put("x-message-ttl", 10000);
            arg.put("x-dead-letter-exchange", "exchange.dlx");
            // DLK ,没有特殊指定，则使用原队列的路由键，这里设置了
            arg.put("x-dead-letter-routing-key", "routingkey");
            // 声明队列，绑定普通交换器，加入参数 - 死信交换器信息
            channel.queueDeclare("queue.normal", true, false, false, arg);
            channel.queueBind("queue.normal", "exchange.normal", "");
            // 声明队列，绑定死信交换器
            channel.queueDeclare("queue.dlx", true, false, false, null);
            channel.queueBind("queue.dlx", "exchange.dlx", "routingkey");
           // 发送一条消息，fanout类型，routingKey随意
            channel.basicPublish("exchange.normal", "rk",
                    MessageProperties.PERSISTENT_TEXT_PLAIN, "dlx".getBytes());
        }
    }


}