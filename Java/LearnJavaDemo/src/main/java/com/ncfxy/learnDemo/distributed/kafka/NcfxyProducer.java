package com.ncfxy.learnDemo.distributed.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NcfxyProducer {

    public static void main(String[] args) {
        NcfxyProducer ncfxyProducer = new NcfxyProducer();
        ncfxyProducer.startProduce();
    }

    public void startProduce() {
        Producer<String, String> producer = new KafkaProducer<String, String>(getProperties());
        for(int i = 0;i < 10;i++){
//            sendSampleAsynchronized(producer,i);
            sendSampleSynchronized(producer, i);
        }
        producer.close();
    }

    public Properties getProperties() {
        Properties props = new Properties();
        // 前三个为必选属性
        // broker的地址清单
        props.put("bootstrap.servers", "myaliyun.ncfxy.site:9092");
        // key使用的序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value使用的序列化类
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 多少分区副本收到消息算成功
        props.put("acks", "all");
        // 生产者内存缓冲区的大小
        props.put("buffer.memory", 33554432);
        // 发送消息时使用的压缩方式，支持snappy，gzip或lz4
        props.put("compression.type", "gzip");
        // 如果遇到临时性错误，重发消息的次数
        props.put("retries", 0);
        // 重试时的间隔时间
        props.put("retry.backoff.ms", 100);
        // 多个消息会放在同一批次发送， 这是批次的大小， 批次满或者等待时间到会发送批次
        props.put("batch.size", 16384);
        // 发送一个批次前的等待时间
        props.put("linger.ms", 1);
        // 用来识别生产者
        props.put("client.id", "producer1");
        // 生产者在收到响应之前最多发多少消息
        // 设置为1可以保证按顺序写入服务器
        props.put("max.in.flight.requests.per.connection", 1);


        // 1. 等待同步副本返回消息确认的时间
        // 2. 生产者等待服务器返回响应的时间
        // 3. 生产者获取元数据的响应时间
        props.put("timeout.ms", 10000);
        props.put("request.timeout.ms", 10000);
        props.put("metadata.fetch.timeout.ms", 10000);

        // 获取元数据的阻塞时间
        props.put("max.block.ms", 10000);
        // 生产者发送请求的大小
        props.put("max.request.size", 33554432);

        // TCP socket接收和发送数据包的缓冲区大小
        props.put("receive.buffer.bytes", -1);
        props.put("send.buffer.bytes", -1);
        return props;
    }


    /**
     * 同步方式发送请求的例子
     * @param producer
     * @param num
     */
    public void sendSampleSynchronized(Producer producer, Integer num) {
        Future<RecordMetadata> f = producer.send(new ProducerRecord<String, String>("test", num.toString(), num.toString()));
        try {
            RecordMetadata data = f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Send: " + num);
    }

    /**
     * 使用异步方式发送请求的例子
     * @param producer
     * @param num
     */
    public void sendSampleAsynchronized(Producer producer, Integer num) {
        // 使用异步方式发送
        producer.send(new ProducerRecord<String, String>("test", num.toString(), num.toString()), new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                System.out.println("Asynchronized success: " + num);
            }
        });
    }
}
