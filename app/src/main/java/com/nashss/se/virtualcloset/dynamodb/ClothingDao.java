package com.nashss.se.virtualcloset.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.virtualcloset.exceptions.ClothingNotFoundException;

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
}
