package com.nashss.se.virtualcloset.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.nashss.se.virtualcloset.exceptions.UserNotFoundException;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}

