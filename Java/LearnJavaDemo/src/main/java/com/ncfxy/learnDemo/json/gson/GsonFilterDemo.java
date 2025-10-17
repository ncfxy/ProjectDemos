package com.ncfxy.learnDemo.json.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * Gson中提供的四种过滤方式
 * 1. @Expose 注解过滤
 * 2. @Since @Until 注解过滤
 * 3. Java Modifier过滤
 * 4. ExclusionStrategy接口过滤
 */
public class GsonFilterDemo {

    public static void main(String[] args) {
        GsonFilterDemo demo = new GsonFilterDemo();
        demo.usingExpose();
        demo.sinceUntilFilter();
        demo.modifiersFilter();
        demo.exclusionStrategyFilter();

    }

    /**
     * 给予expose注解进行序列化的过滤
     */
    public void usingExpose() {
        Category category = new Category(1, "电脑", null, null);
        category.setChildren(Arrays.asList(new Category(100, "笔记本", null, category),
                new Category(101, "台式机", null, category)));
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        System.out.println(gson.toJson(category));
    }

    /**
     * 基于Since和Until注解进行过滤
     */
    public void sinceUntilFilter(){
        SinceUntilSample sinceUntilSample = new SinceUntilSample();
        sinceUntilSample.since = "since";
        sinceUntilSample.until = "until";
        // 4 <= version <= 7时，输出{"since":"since","until":"until"}
        Gson gson = new GsonBuilder().setVersion(5).create();
        System.out.println(gson.toJson(sinceUntilSample));
        // version < 4时，输出{"until":"until"}
        gson = new GsonBuilder().setVersion(3).create();
        System.out.println(gson.toJson(sinceUntilSample));
        // version > 7时，输出{"since":"since"}
        gson = new GsonBuilder().setVersion(7).create();
        System.out.println(gson.toJson(sinceUntilSample));
    }

    /**
     * 使用Java反射包中提供的Modifer进行过滤
     */
    public void modifiersFilter() {
        ModifierSample modifierSample = new ModifierSample();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.STATIC, Modifier.PRIVATE)
                .create();
        System.out.println(gson.toJson(modifierSample));
    }

    /**
     * 实现Gson提供的ExclusionStrategy接口过滤
     */
    public void exclusionStrategyFilter() {
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        // return true为排除
                        if("finalField".equals(f.getName())) return true;
                        // 按expose注解排除
                        Expose expose = f.getAnnotation(Expose.class);
                        if(expose != null && expose.deserialize() == false) return true;
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 直接排除某个类， return true为排除
                        return (clazz == int.class || clazz == Integer.class);
//                        return false;
                    }
                }).create();
        System.out.println(gson.toJson(123));
        System.out.println(new Gson().toJson(123));
    }


    private class ModifierSample {
        final String finalField = "final";
        public String publicField = "public";
        protected String protectedField = "protected";
        String defaultField = "default";
        private String privateField = "private";
    }

    private class SinceUntilSample{
        @Since(4)
        public String since;
        @Until(6)
        public String until;
    }

    @Data
    @AllArgsConstructor
    private class Category {
        @Expose
        public int id;
        @Expose
        public String name;
        @Expose
        private List<Category> children;
        // 因业务需要增加，但并不需要序列化
        public Category parent;
    }
}
