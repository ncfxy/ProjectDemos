package com.ncfxy.aws.demo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.HashMap;
import java.util.Map;

public class UseMapperDemo {
    public static void main(String[] args) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        MovieItem movieItem = new MovieItem();
        movieItem.setYear(2015);
        movieItem.setTitle("This is a test title");
        Map<String, String> info = new HashMap<String, String>();
        info.put("author", "ncfxy");
        movieItem.setInfo(info);
        try {
            mapper.save(movieItem);
        }catch (Exception e) {
            System.err.println("Unable to retrieve data: ");
            System.err.println(e.getMessage());
        }
    }
}
