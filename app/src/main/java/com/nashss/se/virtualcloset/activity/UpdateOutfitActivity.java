package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.UpdateOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.UpdateOutfitResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.exceptions.InvalidAttributeValueException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateOutfitActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;

    /**
     * Instantiates a new UpdateOutfitActivity object.
     *
     * @param outfitDao OutfitDao to access the outfits table.
     */
    @Inject
    public UpdateOutfitActivity(OutfitDao outfitDao) {
        this.outfitDao = outfitDao;
    }

    /**
     * This method retrieves the outfit, updates it, and returns the updated outfit.
     *
     * If the outfit does not exist, it should throw a OutfitNotFoundException.
     *
     * If the outfit name or customer Id has invalid characters, it should throw an InvalidAttributeValueException.
     *
     * @param updateOutfitRequest request object with the outfitID, outfit name, and customerID.
     * @return updateOutfitResult result object with the updated outfit.
     */
    public UpdateOutfitResult handleRequest(final UpdateOutfitRequest updateOutfitRequest) {
        log.info("Received UpdateOutfitRequest {}", updateOutfitRequest);

        if (!outfitDao.isValidString(updateOutfitRequest.getName())) {
            throw new InvalidAttributeValueException("Outfitlist name [" + updateOutfitRequest.getName() +
                    "] contains illegal characters");
        }

        Outfit outfit = outfitDao.getOutfit(updateOutfitRequest.getId());

        if (!outfit.getCustomerId().equals(updateOutfitRequest.getCustomerId())) {
            throw new SecurityException("You have to be the creator of the outfit to update it!");
        }

        outfit.setName(updateOutfitRequest.getName());
        outfit = outfitDao.saveOutfit(outfit);

        return UpdateOutfitResult.builder()
                .withOutfit(new ModelConverter().toOutfitModel(outfit))
                .build();
    }
}
