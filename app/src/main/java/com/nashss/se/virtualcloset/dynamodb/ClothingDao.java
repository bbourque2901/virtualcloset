package com.nashss.se.virtualcloset.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.virtualcloset.exceptions.ClothingNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
