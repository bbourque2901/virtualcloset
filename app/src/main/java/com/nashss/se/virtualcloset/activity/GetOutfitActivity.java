package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetOutfitActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;

    @Inject
    public GetOutfitActivity(OutfitDao outfitDao) {
        this.outfitDao = outfitDao;
    }

    public GetOutfitResult handleRequest(final GetOutfitRequest getOutfitRequest) {
        log.info("Received GetOutfitRequest {}", getOutfitRequest);
        String requestedId = getOutfitRequest.getId();
        Outfit outfit = outfitDao.getOutfit(requestedId);
        OutfitModel outfitModel = new ModelConverter().toOutfitModel(outfit);

        return GetOutfitResult.builder()
                .withOutfit(outfitModel)
                .build();
    }
}
