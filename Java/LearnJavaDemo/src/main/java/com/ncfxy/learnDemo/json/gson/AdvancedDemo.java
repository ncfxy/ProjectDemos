package com.ncfxy.learnDemo.json.gson;


import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Gson 允许使用TypeAdapter来接管整个序列化的过程
 * JsonSerializer ： 只接管序列化过程
 * JsonDeserializer: 只接管反序列化过程
 */
public class AdvancedDemo {

    public void useTypeAdapter(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new TypeAdapter<User>() {
                    @Override
                    public void write(JsonWriter out, User value) throws IOException {
                        out.beginObject();
                        out.name("name").value(value.name);
                        out.name("age").value(value.age);
                        out.name("email").value(value.emailAddress);
                        out.endObject();
                    }

                    @Override
                    public User read(JsonReader in) throws IOException {
                        User user = new User();
                        in.beginObject();
                        while (in.hasNext()) {
                            switch (in.nextName()) {
                                case "name":
                                    user.name = in.nextString();
                                    break;
                                case "age":
                                    user.age = in.nextInt();
                                    break;
                                case "email":
                                case "email_address":
                                case "emailAddress":
                                    user.emailAddress = in.nextString();
                                    break;
                            }
                        }
                        in.endObject();
                        return user;
                    }
                })
                .create();

        new GsonBuilder().registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
            @Override
            public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return null;
            }
        }).create();

        new GsonBuilder().registerTypeAdapter(Integer.class, new JsonSerializer<Integer>() {
            @Override
            public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
                return null;
            }
        }).create();

        new GsonBuilder().registerTypeAdapterFactory(new TypeAdapterFactory() {
            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                return null;
            }
        }).create();
    }

    private class UserTypeAdapter extends TypeAdapter<User> {

        @Override
        public void write(JsonWriter out, User value) throws IOException {

        }

        @Override
        public User read(JsonReader in) throws IOException {
            return null;
        }
    }

    @JsonAdapter(UserTypeAdapter.class)
    private class User{
        public String name;
        public int age;
        public String emailAddress;
    }
}
