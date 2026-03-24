package com.ncfxy.aws.demo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.util.HashMap;
import java.util.Map;

public class InfoConverter implements DynamoDBTypeConverter<String, Map<String, String>> {

    public String convert(Map<String, String> map) {
        return map.toString();
    }

    public Map<String, String> unconvert(String s) {
        return new HashMap<String, String>();
    }
}
