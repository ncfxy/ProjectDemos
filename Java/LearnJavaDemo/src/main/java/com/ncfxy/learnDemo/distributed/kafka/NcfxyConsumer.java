package com.ncfxy.learnDemo.distributed.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

public class NcfxyConsumer {

    public static void main(String[] args){
        NcfxyConsumer ncfxyConsumer = new NcfxyConsumer();
//        ncfxyConsumer.startConsumer();
        ncfxyConsumer.asynCommit();
    }

    public void asynCommit() {
        Properties props = getProperties();
        props.put("enable.auto.commit", "false");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        // 订阅主题
        consumer.subscribe(Arrays.asList("test"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value=%s%n", record.offset(), record.key(), record.value());
                }
                // 异步提交当前偏移量
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                        if(exception != null) {
                            System.out.printf("Commit failed for offset %s\n Exception: %s.\n", offsets.toString(), exception.toString());
                        }
                    }
                });
            }
        } finally {
            try{
                // 同步提交当前偏移量
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }

    public Properties getProperties() {
        Properties props = new Properties();
        // 前三个为必选属性
        // broker的地址清单
        props.put("bootstrap.servers", "myaliyun.ncfxy.site:9092");
        // key使用的反序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value使用的反序列化类
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 消费群组id
        props.put("group.id", "test");

        // 从服务器上获取记录的最小字节数，等到有足够数据之后才返回
        props.put("fetch.min.bytes", 64);
        // broker的最长等待时间，达不到最小字节数时，超时也会返回
        props.put("fetch.max.wait.ms", 500);
        // 每个分区返回给消费者最大的字节数
        props.put("max.partition.fetch.bytes", 1024);

        // 消费者最长允许的断开时间
        // 消费者发送心跳的时间
        // 前者一般为后者的3倍
        props.put("group.min.session.timeout.ms", 3000);
        props.put("heartbeat.interval.ms", 1000);

        // 当没有偏移量时，消费者从何处读取数据latest, earliest
        props.put("auto.offset.reset", "latest");
        // 消费者是否自动提交偏移量，以及偏移量的提交间隔
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");

        // 分区分配给消费者的策略 Range, RoundRobin
        // org.apache.kafka.clients.consumer.RangeAssignor
        // org.apache.kafka.clients.consumer.RoundRobinAssignor
        props.put("partition.assignment.strategy", "org.apache.kafka.clients.consumer.RangeAssignor");

        // 标注客户端id
        props.put("client.id", "ncfxy_consumer_1");
        // 单次poll返回的记录数
        props.put("max.poll.records", 10);

        // TCP socket接收和发送数据包的缓冲区大小
        props.put("receive.buffer.bytes", -1);
        props.put("send.buffer.bytes", -1);
        return props;
    }

    public void startConsumer() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(getProperties());
        // 订阅主题
        consumer.subscribe(Arrays.asList("test"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value=%s%n", record.offset(), record.key(), record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 再平衡监听器
     */
    private class HandleRebalance implements ConsumerRebalanceListener {

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

        }
    }
}
