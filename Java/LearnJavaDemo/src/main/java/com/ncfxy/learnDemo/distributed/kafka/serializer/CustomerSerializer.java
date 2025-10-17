package com.ncfxy.learnDemo.distributed.kafka.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class CustomerSerializer implements Serializer<Customer> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // 不做任何配置
    }

    @Override
    /**
     * Customer对象被序列化成：
     * 表示customerID的4字节整数
     * 表示customerName长度的4字节整数（如果customerName长度为空， 则长度为0）
     * 表示customerName的N个字节
     */
    public byte[] serialize(String topic, Customer data) {
        try{
            byte[] serializedName;
            int stringSize;
            if(data == null){
                return null;
            }else {
                if(data.getName() != null) {
                    serializedName = data.getName().getBytes("UTF-8");
                    stringSize = serializedName.length;
                } else {
                    serializedName = new byte[0];
                    stringSize = 0;
                }
            }
            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + stringSize);
            buffer.putInt(data.getID());
            buffer.putInt(stringSize);
            buffer.put(serializedName);
            return buffer.array();
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Customer to byte[] " + e);
        }
    }

    @Override
    public void close() {
        // 不需要关闭任何东西
    }
}
