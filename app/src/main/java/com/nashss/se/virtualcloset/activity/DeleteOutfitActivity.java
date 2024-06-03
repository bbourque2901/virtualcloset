package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.DeleteOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.DeleteOutfitResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.models.OutfitModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteOutfitActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;

    /**
     * Instantiates a new DeleteOutfitActivity object.
     *
     * @param outfitDao outfitdao to access the outfit table.
     */
    @Inject
    public DeleteOutfitActivity(OutfitDao outfitDao) {
        this.outfitDao = outfitDao;
    }

    /**
     * This method removes the requested outfit from the database.
     * If the outfit doesn't exist, it should throw an OutfitNotFoundException
     *
     * @param deleteOutfitRequest request object with outfit id
     * @return deleteOutfitResult result object containg the API defined outfitmodel
     */
    public DeleteOutfitResult handleRequest(final DeleteOutfitRequest deleteOutfitRequest) {
        log.info("Received DeleteOutfitRequest {}", deleteOutfitRequest);

        String requestedId = deleteOutfitRequest.getId();

        Outfit outfit = outfitDao.removeOutfit(requestedId);

        OutfitModel outfitModel = new ModelConverter().toOutfitModel(outfit);

        return DeleteOutfitResult.builder()
                .withOutfit(outfitModel)
                .build();
    }
}
