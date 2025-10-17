package com.ncfxy.learnDemo.json.gson;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;

/**
 * Gson提供的最基本的用法
 * 1. 对基础数据类型进行转化
 * 2. 对pojo对象进行转化
 * 3. 对数组及泛型的转化
 */
public class GsonBasicDemo {
    public static void main(String[] args){
        GsonBasicDemo a = new GsonBasicDemo();
        a.basicTypeOperation();
        a.pojoOperation();
        a.arrayOperation();
    }

    /**
     * Gson对基础类型的操作
     */
    public void basicTypeOperation(){
        Gson gson = new Gson();
        // 从String解析成基本类型
        int i = gson.fromJson("100", int.class);            // 100
        double d = gson.fromJson("\"99.99\"", double.class);    // 99.99
        boolean b = gson.fromJson("true", boolean.class);       // true
        String str = gson.fromJson("String", String.class);     // String
        System.out.println(i);
        System.out.println(d);
        System.out.println(b);
        System.out.println(str);

        String jsonNumber = gson.toJson(100);       // 100
        String jsonBoolean = gson.toJson(false);    // false
        String jsonString = gson.toJson("String"); //"String"
        System.out.println(jsonNumber);
        System.out.println(jsonBoolean);
        System.out.println(jsonString);
    }

    /**
     * 对数组及泛型的支持
     */
    public void arrayOperation() {
        Gson gson = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);
        Arrays.stream(strings).forEach(System.out::print);

        // 对Java泛型的支持,使用TypeToken
        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());
        stringList.stream().forEach(System.out::print);
    }

    /**
     * Gson对pojo对象的操作
     */
    public void pojoOperation(){
        Gson gson = new Gson();
        User user = new User("怪盗kidou",24);
        String jsonObject = gson.toJson(user); // {"name":"怪盗kidou","age":24}
        System.out.println(jsonObject);
        String jsonString = "{\"name\":\"怪盗kidou\",\"age\":24, \"email_address\":\"www@example.com\"}";
        user = gson.fromJson(jsonString, User.class);

        jsonString = "{\"name\":\"怪盗kidou123\",\"Name\":\"怪盗kidou\",\"age\":24, \"email_address\":\"www@example.com\"}";
        user = gson.fromJson(jsonString, User.class);
        System.out.println(user);

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
