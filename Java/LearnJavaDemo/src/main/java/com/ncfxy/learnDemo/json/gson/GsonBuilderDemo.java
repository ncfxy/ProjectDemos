package com.ncfxy.learnDemo.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *  GsonBuilder用来更改Gson的默认设置
 *  支持导出Null值、格式化输出、日期时间等
 */
public class GsonBuilderDemo {

    public static void main(String[] args) {
        GsonBuilderDemo demo = new GsonBuilderDemo();
        demo.exportNull();
    }

    /**
     * 如果字段为null也进行序列化
     */
    public void exportNull(){
        User user = new User("Keeper", 23);
        Gson gson = new Gson();
        System.out.println(gson.toJson(user));
        
        Gson gson1 = new GsonBuilder().serializeNulls().create();
        System.out.println(gson1.toJson(user));
    }

    public void otherUsage(){
        Gson gson = new GsonBuilder()
                // 序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序列化时均生效
                .setDateFormat("yyyy-MM-dd")
                // 生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                // 禁止转义html标签
                .disableHtmlEscaping()
                // 格式化输出
                .setPrettyPrinting()
                .create();
    }

    class User {
        public User(String name, int age){
            this.name = name;
            this.age = age;
        }
        private String name;
        private int age;
        private String email;
    }
}
