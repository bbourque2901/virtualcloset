package com.nashss.se.virtualcloset.dynamodb;

import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import org.apache.commons.lang3.StringUtils;

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
     * @return if outfit attributes are valid.
     */
    public static boolean isValidString(String name) {
        String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ -";
        return StringUtils.containsOnly(name, validChars);
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
}

