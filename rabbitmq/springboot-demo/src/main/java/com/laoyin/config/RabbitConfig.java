package com.laoyin.config;

import com.laoyin.confirm.ConfirmCallBackListener;
import com.laoyin.confirm.ReturnCallBackListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    @Bean("updatedRabbitTemplate")
    public RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setConfirmCallback(new ConfirmCallBackListener());
        rabbitTemplate.setReturnCallback(new ReturnCallBackListener());
        return rabbitTemplate;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    // 两个交换机
    @Bean("topicExchange")
    public TopicExchange getTopicExchange() {
        return new TopicExchange("TOPIC_EXCHANGE");
    }

    @Bean("fanoutExchange")
    public FanoutExchange getFanoutExchange() {
        return new FanoutExchange("FANOUT_EXCHANGE");
    }

    // 三个队列
    // 没绑定，默认绑定default交换器
    @Bean("firstQueue")
    public Queue getFirstQueue() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", 6000);
        Queue queue = new Queue("FIRST_QUEUE", false, false, true, args);
        return queue;
    }

    @Bean("secondQueue")
    public Queue getSecondQueue() {
        return new Queue("SECOND_QUEUE");
    }

    @Bean("thirdQueue")
    public Queue getThirdQueue() {
        return new Queue("THIRD_QUEUE");
    }

    // 两个绑定
    @Bean
    public Binding bindSecond(@Qualifier("secondQueue") Queue queue, @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#.test.#");
    }

    @Bean
    public Binding bindThird(@Qualifier("thirdQueue") Queue queue, @Qualifier("fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}
