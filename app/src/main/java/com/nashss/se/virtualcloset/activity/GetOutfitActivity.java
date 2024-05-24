package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetOutfitResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.models.OutfitModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetOutfitActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;

    /**
     * Instantiates a new GetOutfitActivity object.
     *
     * @param outfitDao OutfitDao to access the outfits table.
     */
    @Inject
    public GetOutfitActivity(OutfitDao outfitDao) {
        this.outfitDao = outfitDao;
    }

    /**
     * This method handles the incoming request by retrieving the outfit from the database.
     * <p>
     * It then returns the outfit.
     * <p>
     * If the outfit does not exist, this should throw a OutfitNotFoundException.
     *
     * @param getOutfitRequest request object containing the outfit ID
     * @return getOutfitResult result object containing the API defined {@link OutfitModel}
     */
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
