package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetClothingFromOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetClothingFromOutfitResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.models.ClothingModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetClothingFromOutfitActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;

    /**
     * Instantiates a new GetClothingFromOutfitActivity object.
     *
     * @param outfitDao OutfitDao to access the outfit table.
     */
    @Inject
    public GetClothingFromOutfitActivity(OutfitDao outfitDao) {
        this.outfitDao = outfitDao;
    }

    /**
     * This method retrieves the outfit from the database.
     * It returns the outfit's clothing items.
     * If the outfit doesn't exist, a OutfitNotFoundException is thrown
     *
     * @param getClothingFromOutfitRequest request object with the outfit ID
     * @return getClothingFromOutfitResult result object with outfit's items
     */
    public GetClothingFromOutfitResult handleRequest(final GetClothingFromOutfitRequest getClothingFromOutfitRequest) {
        log.info("Received GetClothingFromOutfitRequest {}", getClothingFromOutfitRequest);

        Outfit outfit = outfitDao.getOutfit(getClothingFromOutfitRequest.getId());
        List<ClothingModel> clothingModels = new ModelConverter().toClothingModelList(outfit.getClothingItems());

        return GetClothingFromOutfitResult.builder()
                .withClothingList(clothingModels)
                .build();
    }
}
