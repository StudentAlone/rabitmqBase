package com.api;

import org.apache.kafka.clients.producer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 生产者
 * KafkaProducer：需要创建一个生产者对象，用来发送数据
 * ProducerConfig：获取所需的一系列配置参数
 * ProducerRecord：每条数据都要封装成一个 ProducerRecord 对象
 */
public class CustomProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        // 1. kafka集群环境：props.put("bootstrap.servers",
        //      "192.168.44.161:9093,192.168.44.161:9094,192.168.44.161:9095");
        // 2. 关注  ProducerConfig 和 CommonClientConfigs
        //      相应的配置信息
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.73.135:9092");
        // 0 发出去就确认 | 1(all) leader 落盘就确认| all(-1) 所有Follower同步完才确认
        props.put("acks", "all");
        //异常自动重试次数
        props.put("retries", 1);
        //批次大小-多少条数据发送一次，默认16K
        props.put("batch.size", 16384);
        //等待时间-批量发送的等待时间
        props.put("linger.ms", 1);
        //RecordAccumulator 缓冲区大小
        // 客户端缓冲区大小，默认32M，满了也会触发消息发送
        props.put("buffer.memory", 33554432);
        // 获取元数据时生产者的阻塞时间，超时后抛出异常
        props.put("max.block.ms",3000);
        // kv 序列化器
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        // 消息的幂等性 <PID,Partition,SeqNumber>
        // 无法保证跨分区跨会话
        //  除非开启事务
       // props.put("enable.idempotence",true);

        // 构建拦截链
        List<String> interceptors = new ArrayList<>();
        interceptors.add("com.api.TimeInterceptor");
                interceptors.add("com.api.CounterInterceptor");
        // 添加拦截器 -- interceptor.classes
       props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);

        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            // Callback 也可以不要
            // Future<RecordMetadata> send(ProducerRecord<K, V> record);
            producer.send(new ProducerRecord<String, String>("first",
                    Integer.toString(i), Integer.toString(i)), new Callback() {
                // 回调函数，该方法会在 Producer 收到 ack 时调用，为异步调用
                @Override
                public void onCompletion(RecordMetadata metadata,
                                         Exception exception) {
                    if (exception == null) {
                        System.out.println("success->" +
                                metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });

            // 同步发送   不要.get()就是异步发送
            // producer.send(new ProducerRecord<String, String>("first",
            //  Integer.toString(i), Integer.toString(i))).get();

        }
        producer.close();
    }
}
