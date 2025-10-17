#Java 中使用JSON那些事
## 1. json-lib
 - 最早的应用的json解析工具，但目前基本已经不更新
 - 依赖于很多第三方包
	 - commons-beanutils.jar
	 - commons-collections.jar
	 - commons-lang.jar
	 - commons-logging.jar
	 - ezmorph.jar
 - 对json转换的支持不够好
	 - 有JSONObject、JSONArray、JSONNull三种类型，需要分别处理
	 - 不能自动将整个json自动转化成对象，需要自己一个一个进行转换
 - json-lib在功能和性能上面都不能满足现在互联网化的需求。



## 2. Jackson

## 3. Gson

```gradle
dependencies {
    compile 'com.google.code.gson:gson:2.8.2'
}
```

```xml
<dependency>
  <groupId>com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.8.2</version>
</dependency>
```

## 4. FastJson

## 参考链接
1. [各个JSON技术的比较](http://www.cnblogs.com/kunpengit/p/4001680.html)

1. [几种常用JSON库性能比较](http://vickyqi.com/2015/10/19/几种常用JSON库性能比较/)