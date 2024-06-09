package com.nashss.se.virtualcloset.dynamodb;

import com.nashss.se.virtualcloset.exceptions.ClothingNotFoundException;
import com.nashss.se.virtualcloset.exceptions.UserNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClothingDao {
    private final DynamoDBMapper dynamoDBMapper;

    /**
     * Instantiates an ClothingDao object.
     *
     * @param dynamoDBMapper the {@link DynamoDBMapper} used to interact with the clothing table
     */
    @Inject
    public ClothingDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    /**
     * Retrieves clothing by clothingID.
     *
     * If not found, throws ClothingNotFoundException.
     *
     * @param clothingId The id to look up
     * @return The corresponding clothing item if found
     */
    public Clothing getClothing(String clothingId) {
        Clothing clothing = dynamoDBMapper.load(Clothing.class, clothingId);
        if (null == clothing) {
            throw new ClothingNotFoundException("This clothing item doesn't exist yet!");
        }
        return clothing;
    }

    /**
     * Sorts clothing by worncount.
     *
     * @param customerId the customerId of the individual sorting their clothes.
     * @param ascending order the clothes are displayed in.
     * @return the sorted list of clothes.
     */
    public List<Clothing> getClothingSortedByWornCount(String customerId, boolean ascending) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":customerId", new AttributeValue().withS(customerId));

        DynamoDBQueryExpression<Clothing> queryExpression = new DynamoDBQueryExpression<Clothing>()
                .withKeyConditionExpression("customerId = :customerId")
                .withExpressionAttributeValues(valueMap)
                .withIndexName("clothing-worncount")
                .withScanIndexForward(ascending)
                .withConsistentRead(false);

        return dynamoDBMapper.query(Clothing.class, queryExpression);
    }

    /**
     * Checks if outfit attributes are valid.
     *
     * @param string String to check for invalid characters.
     * @return if outfit attributes are valid.
     */
    public static boolean isValidString(String string) {
        String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ -@+ 1234567890.";
        return StringUtils.containsOnly(string, validChars);
    }

    /**
     * Saves the given clothing item.
     *
     * @param clothing The clothing item to save
     * @return The Clothing object that was saved
     */
    public Clothing saveClothing(Clothing clothing) {
        this.dynamoDBMapper.save(clothing);
        return clothing;
    }

    /**
     * Searches clothing table for clothing items matching the customerId.
     * @param customerId the customerId being searched.
     * @return list of clothing objects with customerId.
     */
    public List<Clothing> getAllClothingForUser(String customerId) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

        if (customerId == null) {
            throw new UserNotFoundException("That user doesn't exist yet!");
        }

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":customerId", new AttributeValue().withS(customerId));

        dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
        dynamoDBScanExpression.setFilterExpression("customerId = :customerId");

        return this.dynamoDBMapper.scan(Clothing.class, dynamoDBScanExpression);
    }
}
