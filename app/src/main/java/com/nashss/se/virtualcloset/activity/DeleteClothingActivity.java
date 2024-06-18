package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.DeleteClothingRequest;
import com.nashss.se.virtualcloset.activity.results.DeleteClothingResult;
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

public class DeleteClothingActivity {
    private final Logger log = LogManager.getLogger();
    private final ClothingDao clothingDao;
    private final OutfitDao outfitDao;

    /**
     * Instantiates a new DeleteClothingActivity object.
     *
     * @param clothingDao clothingDao to access the clothing table.
     * @param outfitDao outfitDao to access the outfits table.
     */
    @Inject
    public DeleteClothingActivity(ClothingDao clothingDao, OutfitDao outfitDao) {
        this.clothingDao = clothingDao;
        this.outfitDao = outfitDao;
    }

    /**
     * This method removes the requested clothing item from the database.
     * If the clothing item doesn't exist, it should throw a ClothingNotFoundException
     *
     * @param deleteClothingRequest request object with clothing id
     * @return deleteClothingResult result object containing the API defined clothingmodel
     */
    public DeleteClothingResult handleRequest(final DeleteClothingRequest deleteClothingRequest) {
        log.info("Received DeleteClothingRequest {}", deleteClothingRequest);

        String reqClothingId = deleteClothingRequest.getClothingId();

        Clothing clothing = clothingDao.removeClothing(reqClothingId);

        if (clothing == null) {
            log.warn("Clothing with id {} not found or already deleted", reqClothingId);
            return DeleteClothingResult.builder()
                    .build();
        }

        List<Outfit> outfitsWithClothing = outfitDao.getOutfitsByClothingId(reqClothingId);

        for (Outfit outfit : outfitsWithClothing) {
            List<Clothing> clothingItems = new ArrayList<>(outfit.getClothingItems());
            clothingItems.removeIf(item -> item.getClothingId().equals(reqClothingId));
            outfit.setClothingItems(clothingItems);
            outfitDao.saveOutfit(outfit);
        }

        ClothingModel clothingModel = new ModelConverter().toClothingModel(clothing);

        return DeleteClothingResult.builder()
                .withClothing(clothingModel)
                .build();
    }
}
