package com.nashss.se.virtualcloset.dynamodb;

import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;
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

/**
 * Accesses data for an outfit using {@link Outfit} to represent the model in DynamoDB.
 */
@Singleton
public class OutfitDao {
    private final DynamoDBMapper dynamoDBMapper;

    /**
     * Instantiates a OutfitDao object.
     *
     * @param dynamoDBMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     */
    @Inject
    public OutfitDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    /**
     * Returns the {@link Outfit} corresponding to the specified id.
     *
     * @param id the Outfit ID
     * @return the stored outfit, or null if none was found.
     */
    public Outfit getOutfit(String id) {
        Outfit outfit = this.dynamoDBMapper.load(Outfit.class, id);

        if (outfit == null) {
            throw new OutfitNotFoundException("Could not find outfit with id " + id);
        }
        return outfit;
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
     * Saves the given outfit.
     *
     * @param outfit The outfit to save
     * @return The outfit object that was saved
     */
    public Outfit saveOutfit(Outfit outfit) {
        this.dynamoDBMapper.save(outfit);
        return outfit;
    }

    /**
     * Searches outfit table for outfits with a matching customerId.
     * @param customerId the customerId being searched
     * @return list of outfit objects w/ customerId
     */
    public List<Outfit> getAllOutfitsForUser(String customerId) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

        if (customerId == null) {
            throw new UserNotFoundException("That user doesn't exist yet!");
        }

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":customerId", new AttributeValue().withS(customerId));

        dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
        dynamoDBScanExpression.setFilterExpression("customerId = :customerId");

        return this.dynamoDBMapper.scan(Outfit.class, dynamoDBScanExpression);
    }

    /**
     * Removes the outfit corresponding to the specified id.
     *
     * @param id the outfit id
     * @return the removed outfit, or null if none was found
     */
    public Outfit removeOutfit(String id) {
        Outfit outfit = this.dynamoDBMapper.load(Outfit.class, id);

        if (outfit == null) {
            throw new OutfitNotFoundException("Could not find outfit with id " + id);
        }

        dynamoDBMapper.delete(outfit);

        return outfit;
    }

    /**
     * Sorts outfits by worncount.
     *
     * @param customerId the customerId of the individual sorting their outfits.
     * @param ascending order outfits are displayed in.
     * @return the sorted list of outfits.
     */
    public List<Outfit> getOutfitsSortedByWornCount(String customerId, boolean ascending) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":customerId", new AttributeValue().withS(customerId));

        DynamoDBQueryExpression<Outfit> queryExpression = new DynamoDBQueryExpression<Outfit>()
                .withKeyConditionExpression("customerId = :customerId")
                .withExpressionAttributeValues(valueMap)
                .withIndexName("outfit-worncount-index")
                .withScanIndexForward(ascending)
                .withConsistentRead(false)
                .withLimit(5);

        return dynamoDBMapper.query(Outfit.class, queryExpression);

    }
}

