package com.ncfxy.aws.demo;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Map;

@DynamoDBTable(tableName = "Movies")
public class MovieItem {
    private Integer year;
    private String title;
    private Map<String, String> info;

    @DynamoDBHashKey(attributeName = "year")
    public Integer getYear(){return year;}
    public void setYear(Integer year){this.year = year;}

    @DynamoDBRangeKey(attributeName = "title")
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    @DynamoDBTypeConverted(converter = InfoConverter.class)
    @DynamoDBAttribute(attributeName = "info")
    public Map<String, String> getInfo() {return info;}
    public void setInfo(Map<String, String> info) {this.info = info;}

}
