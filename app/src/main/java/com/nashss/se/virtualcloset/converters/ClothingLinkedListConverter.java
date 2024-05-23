package com.nashss.se.virtualcloset.converters;

import com.nashss.se.virtualcloset.dynamodb.Clothing;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import com.google.gson.Gson;
import com.google.common.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;


public class ClothingLinkedListConverter implements DynamoDBTypeConverter<String, List> {
    private static final Gson GSON = new Gson();
    private final Logger log = LogManager.getLogger();

    @Override
    public String convert(List listToBeConverted) {
        return GSON.toJson(listToBeConverted);
    }

    @Override
    public List unconvert(String dynamoDbRepresentation) {
        return GSON.fromJson(dynamoDbRepresentation, new TypeToken<LinkedList<Clothing>>() { } .getType());
    }
}
