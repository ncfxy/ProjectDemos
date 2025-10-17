package com.ncfxy.learnDemo.json.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

/**
 * Gson中做字段映射的方式：
 * 1. 使用@SerializedName 注解，拥有最高优先级
 * 2. 实现FieldNamingStrategy:
 *          IDENTITY	{"emailAddress":"ikidou@example.com"}
 *          LOWER_CASE_WITH_DASHES	{"email-address":"ikidou@example.com"}
 *          LOWER_CASE_WITH_UNDERSCORES	{"email_address":"ikidou@example.com"}
 *          UPPER_CAMEL_CASE	{"EmailAddress":"ikidou@example.com"}
 *          UPPER_CAMEL_CASE_WITH_SPACES	{"Email Address":"ikidou@example.com"}
 */
public class GsonMappingDemo {
    public static void main(String[] args) {
        GsonMappingDemo demo = new GsonMappingDemo();
        demo.usingDefaultPolicy();
    }

    public void usingDefaultPolicy(){
        UserWithOutSerializedName user = new UserWithOutSerializedName();
        user.name = "John";
        user.age = 24;
        user.emailAddress = "haha@example.com";

        Gson gson = new GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
                .create();
        System.out.println(gson.toJson(user));

        gson = new GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                .create();
        System.out.println(gson.toJson(user));

        gson = new GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        System.out.println(gson.toJson(user));

        gson = new GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
        System.out.println(gson.toJson(user));

        gson = new GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES)
                .create();
        System.out.println(gson.toJson(user));

        gson = new GsonBuilder()
                .setFieldNamingStrategy(new FieldNamingStrategy() {
                    @Override
                    public String translateName(Field f) {
                        // 实现自己的规则
                        return f.getName() + "123";
                    }
                })
                .create();
        System.out.println(gson.toJson(user));
    }




    private class UserWithOutSerializedName {
        public String name;
        public int age;
        public String emailAddress;
    }

    private class User {
        public User(String name, int age){
            this.name = name;
            this.age = age;
        }
        // 存在多个的时候，以最后一个为准
        @SerializedName(value="name", alternate = {"Name", "NAME"})
        public String name;
        public int age;
        // 使用这个注解可以指定序列化后对应的字段
        @SerializedName("email_address")
        public String emailAddress;
    }

}
