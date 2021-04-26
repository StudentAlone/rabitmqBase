package com.laoying;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Clear {
    private static final String TASK_QUEUE_NAME = "task_queue";
    private static final String EXCHANGE = "exchange_work";
    public static final String ROUTING_KEY = "routingKeyWork";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(
                "192.168.73.133");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDelete(TASK_QUEUE_NAME);
            channel.exchangeDelete(EXCHANGE);
        }
    }
}
