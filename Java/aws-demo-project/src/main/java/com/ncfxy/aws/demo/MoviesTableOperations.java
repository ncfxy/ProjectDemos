package com.ncfxy.aws.demo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.*;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MoviesTableOperations {
    public static void main(String[] args) throws Exception{
        MoviesTableOperations instance = new MoviesTableOperations();
//        instance.createTable();
//        instance.loadData();
//        instance.testAddItem();
//        instance.testGetItem();
//        instance.testUpdateItem();
//        instance.testAtomicityAdd();
//        instance.testConditionalUpdate();
//        instance.testDeleteItem();
//        instance.testQuery();
//        instance.testScan();
        instance.deleteTable();
    }

    private Table getTable() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("Movies");
        return table;
    }

    public void deleteTable() {
        Table table = getTable();
        try {
            System.out.println("Attempting to delete table; please wait...");
            table.delete();
            table.waitForDelete();
            System.out.print("Success.");

        }
        catch (Exception e) {
            System.err.println("Unable to delete table: ");
            System.err.println(e.getMessage());
        }
    }

    public void testScan() {
        Table table = getTable();
        ScanSpec scanSpec = new ScanSpec().withProjectionExpression("#yr, title, info.rating")
                .withFilterExpression("#yr between :start_yr and :end_yr").withNameMap(new NameMap().with("#yr", "year"))
                .withValueMap(new ValueMap().withNumber(":start_yr", 1950).withNumber(":end_yr", 1959));
        try {
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);

            Iterator<Item> iter = items.iterator();
            while (iter.hasNext()) {
                Item item = iter.next();
                System.out.println(item.toString());
            }

        }
        catch (Exception e) {
            System.err.println("Unable to scan the table:");
            System.err.println(e.getMessage());
        }
    }

    public void testQuery() {
        Table table = getTable();
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#yr", "year");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":yyyy", 1985);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        try {
            System.out.println("Movies from 1985");
            items = table.query(querySpec);
            iterator = items.iterator();
            while(iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getNumber("year") + ": " + item.getString("title"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query movies from 1985");
            System.err.println(e.getMessage());
        }

        valueMap.put(":yyyy", 1992);
        valueMap.put(":letter1", "A");
        valueMap.put(":letter2", "L");
        querySpec.withProjectionExpression("#yr, title, info.generes, info.actors[0]")
                .withKeyConditionExpression("#yr = :yyyy and title between :letter1 and :letter2").withNameMap(nameMap)
                .withValueMap(valueMap);
        try {
            System.out.println("Movies from 1992 - titles A-L, with genres and lead actor");
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getNumber("year") + ": " + item.getString("title") + " " + item.getMap("info"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query movies from 1992:");
            System.err.println(e.getMessage());
        }
    }

    public void testDeleteItem() throws Exception {
        Table table = getTable();
        int year = 2015;
        String title = "The Big New Movie";
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("year", year, "title", title));
//                .withConditionExpression("info.rating <= : val")
//                .withValueMap(new ValueMap().withNumber(":val", 5.0));
        // Conditional delete (we expect this to fail)
        try {
            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
        }
        catch (Exception e) {
            System.err.println("Unable to delete item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void testConditionalUpdate() throws  Exception {
        Table table = getTable();
        int year = 2015;
        String title = "The Big New Movie";

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(new PrimaryKey("year", year, "title", title))
                .withUpdateExpression("remove info.actors[0]")
                .withConditionExpression("size(info.actors) >= :num").withValueMap(new ValueMap().withNumber(":num", 3))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        // Conditional update (we expect this to fail)
        try {
            System.out.println("Attempting a conditional update...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void testAtomicityAdd() throws Exception {
        Table table = getTable();
        int year = 2015;
        String title = "The Big New Movie";
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("year", year, "title", title)
                .withUpdateExpression("set info.rating = info.rating + :val")
                .withValueMap(new ValueMap().withNumber(":val", 1)).withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Incrementing an atomic counter...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void testUpdateItem() throws  Exception {
        Table table = getTable();
        int year = 2015;
        String title = "The Big New Movie";
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("year", year, "title", title)
                .withUpdateExpression("set info.rating = :r, info.plot = :p, info.actors = :a")
                .withValueMap(new ValueMap().withNumber(":r", 5.5).withString(":p", "Everything happens all at once.")
                    .withList(":a", Arrays.asList("Larry", "Moe", "Curly")))
                    .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }

    public void testGetItem()  throws  Exception {
        Table table = getTable();
        int year = 2015;
        String title = "The Big New Movie";
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("year", year, "title", title);

        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            System.out.println("GetItem succeeded: " + outcome);
        }catch (Exception e) {
            System.err.println("Unable to read item: " + year + " " + title);
            System.err.println(e.getMessage());
        }
    }



    public void testAddItem() throws Exception {
        Table table = getTable();
        int year = 2015;
        String title = "The Big New Movie";

        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("plot", "Nothing happens at all.");
        infoMap.put("rating", 0);
        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("year", year, "title", title).withMap("info", infoMap));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + year + " " + title);
            System.err.println(e.getMessage());
        }

    }

    public void loadData() throws Exception{
        Table table = getTable();

        JsonParser parser = new JsonFactory().createParser(new File("moviedata.json"));

        JsonNode rootNode = new ObjectMapper().readTree(parser);
        Iterator<JsonNode> iter = rootNode.iterator();

        ObjectNode currentNode;

        while(iter.hasNext()) {
            currentNode = (ObjectNode) iter.next();
            int year = currentNode.path("year").asInt();
            String title = currentNode.path("title").asText();

            try{
                table.putItem(new Item().withPrimaryKey("year", year, "title", title)
                    .withJSON("info", currentNode.path("info").toString()));
                System.out.println("PutItem succeeded: " + year + " " + title);
            } catch (Exception e) {
                System.err.println("Unable to add movie: " + year + " " + title);
                System.err.println(e.getMessage());
                break;
            }

        }
        parser.close();
    }

    public void createTable() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Movies";

        try{
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("year", KeyType.HASH),
                            new KeySchemaElement("title", KeyType.RANGE)),
                    Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title", ScalarAttributeType.S)),
                    new ProvisionedThroughput(5L, 5L));
            table.waitForActive();
            System.out.println("Success. Table status: " + table.getDescription().getTableStatus());
        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }
}
