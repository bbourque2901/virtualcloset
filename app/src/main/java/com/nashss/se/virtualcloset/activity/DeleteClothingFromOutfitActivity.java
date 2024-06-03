package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.DeleteClothingFromOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.DeleteClothingFromOutfitResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.models.ClothingModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class DeleteClothingFromOutfitActivity {
    private final Logger log = LogManager.getLogger();
    private final ClothingDao clothingDao;
    private final OutfitDao outfitDao;

    /**
     * Instantiates a new DeleteClothingFromOutfitActivity object.
     *
     * @param outfitDao OutfitDao to access the outfits table.
     * @param clothingDao ClothingDao to access the clothing table.
     */
    @Inject
    public DeleteClothingFromOutfitActivity(OutfitDao outfitDao, ClothingDao clothingDao) {
        this.clothingDao = clothingDao;
        this.outfitDao = outfitDao;
    }

    /**
     * Handles incoming request by deleting the requested clothing item from the requested outfit.
     * It returns the updated list of items in the outfit.
     * If the outfit does not exist, it should throw an OutfitNotFoundException.
     * If the clothing item does not exist, it shoud throw a ClothingNotFoundException.
     * @param deleteClothingRequest request object with specified outfitId and clothingId
     * @return deleteClothingFromOutfitResult result object returning updated outfit
     */
    public DeleteClothingFromOutfitResult handleRequest(final DeleteClothingFromOutfitRequest deleteClothingRequest) {
        log.info("Received DeleteClothingFromOutfitRequest {}", deleteClothingRequest);

        Outfit outfit = outfitDao.getOutfit(deleteClothingRequest.getId());

        if (!outfit.getCustomerId().equals(deleteClothingRequest.getCustomerId())) {
            throw new SecurityException("Make sure you're only removing clothes from your outfits!");
        }

        Clothing deletedClothing = clothingDao.getClothing(deleteClothingRequest.getClothingId());

        List<Clothing> clothing = new ArrayList<>(outfit.getClothingItems());
        clothing.remove(deletedClothing);

        outfit.setClothingItems(clothing);
        outfit = outfitDao.saveOutfit(outfit);

        List<ClothingModel> clothingModels = new ModelConverter().toClothingModelList(outfit.getClothingItems());

        return DeleteClothingFromOutfitResult.builder()
                .withClothingModels(clothingModels)
                .build();
    }
}
