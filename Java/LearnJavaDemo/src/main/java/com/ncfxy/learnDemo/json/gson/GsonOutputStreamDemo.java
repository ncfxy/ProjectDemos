package com.ncfxy.learnDemo.json.gson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Gson包对输出流操作的示例
 */
public class GsonOutputStreamDemo {

    public static void main(String[] args) {
        GsonOutputStreamDemo demo = new GsonOutputStreamDemo();
//        demo.outputToString();
            demo.usingJsonWriter();
    }

    /**
     * 将对象直接转化成其他对象
     */
    public void outputToString(){
        Gson gson = new Gson();
        User user = new User("DaMing", 24, "ik@example.com");
        gson.toJson(user, System.out);
        // Use StringBuffer
        StringBuffer buffer = new StringBuffer();
        gson.toJson(user, buffer);
        System.out.println(buffer.toString());

        // User StringBuilder
        StringBuilder builder = new StringBuilder();
        gson.toJson(user, builder);
        System.out.println(builder.toString());
    }

    /**
     * 使用JsonWrite进行输出
     */
    public void usingJsonWriter(){
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));
        try {
            writer.beginObject()
                    .name("name").value("DaMing")
                    .name("age").value(24)
                    .name("email").nullValue()
                    .endObject();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class User {
        public User(String name, int age, String emailAddress){
            this.name = name;
            this.age = age;
            this.emailAddress = emailAddress;
        }
        public String name;
        public int age;
        public String emailAddress;
    }
}
