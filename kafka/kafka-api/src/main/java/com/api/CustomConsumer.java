package com.api;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * 需要用到的类：
 * KafkaConsumer：需要创建一个消费者对象，用来消费数据
 * ConsumerConfig：获取所需的一系列配置参数
 * ConsuemrRecord：每条数据都要封装成一个 ConsumerRecord 对象
 *
 * 为了使我们能够专注于自己的业务逻辑，Kafka 提供了自动提交 offset 的功能。
 * 自动提交 offset 的相关参数：
 * enable.auto.commit：是否开启自动提交 offset 功能
 * auto.commit.interval.ms：自动提交 offset 的时间间隔
 *
 * offset重置：从哪里开始消费
 *  auto.offset.reset
 *  解释：ConsumerConfig.AUTO_OFFSET_RESET_DOC =
 *      "What to do when there is no initial offset in Kafka
 *      or if the current offset does not exist any more on the server
 *      (e.g. because that data has been deleted):
 *      <ul>
 *          <li>earliest: automatically reset the offset to the earliest offset
 *          <li>latest: automatically reset the offset to the latest offset</li>
 *          <li>none: throw exception to the consumer if no previous offset is found for the consumer's group</li>
 *          <li>anything else: throw exception to the consumer.</li>
 *      </ul>";
 */
public class CustomConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        // 1. 集群环境：props.put("bootstrap.servers",
        //      "192.168.44.161:9093,192.168.44.161:9094,192.168.44.161:9095");
        // 2. 关注  ConsumerConfig 和 CommonClientConfigs
        //      相应的配置信息
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.73.135:9092");
        // 消费者组
        props.put("group.id", "test");
        // 是否自动提交偏移量，只有commit之后才更新消费组的 offset
        props.put("enable.auto.commit", "true");
        // 消费者自动提交的间隔
        props.put("auto.commit.interval.ms", "1000");
        // 从最早的数据开始消费 earliest | latest | none
        // auto.offset.reset
        // 触发重置条件：
        //      1. no initial offset 没有初始化（比如换一个group.id去消费，patition的增删变化）
        //      2. current offset does not exist （比如数据被删除了）
        // 重置时设置为：earliest ，将会从头开始消费
        //             latest:从最后的offset位置，消费最新的消息
        //             none:没有找到之前的offset，会抛出异常
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        // kv 序列化器
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        // 订阅topic
        consumer.subscribe(Arrays.asList("first"));
        while (true) {
            ConsumerRecords<String, String> records =consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value " +
                        " = %s%n", record.offset(), record.key(), record.value());
            }
            // 1.上面的属性，关闭自动提交，可以执行手动提交
            // props.put("enable.auto.commit", "false");
            // 2. 手动提交
            //  2.1 手动-同步提交 -由于同步提交 offset 有失败重试机制，故更加可靠
            // consumer.commitSync();
            // 2.2 手动-异步提交
            // consumer.commitAsync();
        }
    }
}
