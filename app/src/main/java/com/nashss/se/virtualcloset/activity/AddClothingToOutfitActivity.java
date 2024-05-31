package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.AddClothingToOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.AddClothingToOutfitResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;

import com.nashss.se.virtualcloset.models.ClothingModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;

public class AddClothingToOutfitActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;
    private final ClothingDao clothingDao;

    /**
     * Instantiates a new AddClothingToOutfitActivity object.
     *
     * @param outfitDao OutfitDao to access the outfit table.
     * @param clothingDao ClothingDao to access the clothing table.
     */
    @Inject
    public AddClothingToOutfitActivity(OutfitDao outfitDao, ClothingDao clothingDao) {
        this.outfitDao = outfitDao;
        this.clothingDao = clothingDao;
    }

    /**
     * This method handles the incoming request by adding clothing
     * to an outfit and persisting the updated outfit.
     * <p>
     * It then returns the updated clothing list of the outfit.
     * <p>
     * If the outfit does not exist, this should throw a OutfitNotFoundException.
     * <p>
     * If the clothing does not exist, this should throw a ClothingNotFoundException.
     *
     * @param addClothingToOutfitRequest request object containing the outfit ID and a clothingID
     *                                 to retrieve the clothing data
     * @return addClothingToOutfitResult result object containing the outfit's updated list of
     *                                 API defined {@link ClothingModel}s
     */
    public AddClothingToOutfitResult handleRequest(final AddClothingToOutfitRequest addClothingToOutfitRequest) {
        log.info("Received AddClothingToOutfitRequest {}", addClothingToOutfitRequest);

        String clothingId = addClothingToOutfitRequest.getClothingId();

        Outfit outfit = outfitDao.getOutfit(addClothingToOutfitRequest.getId());

        if (!outfit.getCustomerId().equals(addClothingToOutfitRequest.getCustomerId())) {
            throw new SecurityException("This has to be your outfit to add clothes to it!");
        }

        Clothing newClothingItem = clothingDao.getClothing(clothingId);
        LinkedList<Clothing> clothingItems = (LinkedList<Clothing>) (outfit.getClothingItems());
        clothingItems.add(newClothingItem);

        outfit.setClothingItems(clothingItems);
        outfit = outfitDao.saveOutfit(outfit);

        List<ClothingModel> clothingModels = new ModelConverter().toClothingModelList(outfit.getClothingItems());
        return AddClothingToOutfitResult.builder()
                .withClothingList(clothingModels)
                .build();


    }
}
