package com.laoying.pubsub;

import com.rabbitmq.client.*;

public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";
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
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String message = "";
            for (int i = 0; i < 10; i++) {
                message = "消息" + i;
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}