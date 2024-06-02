package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetUserOutfitsRequest;
import com.nashss.se.virtualcloset.activity.results.GetUserOutfitsResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.models.OutfitModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

public class GetUserOutfitsActivity {

    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;

    /**
     * Instantiates a new GetUserOutfitsActivity object.
     *
     * @param outfitDao OutfitDao to access the outfit table.
     */
    @Inject
    public GetUserOutfitsActivity(OutfitDao outfitDao) {
        this.outfitDao = outfitDao;
    }

    /**
     * This method handles the incoming request by retrieving the user's outfits from the database.
     * It then returns a list of outfits.
     * If the user does not exist, this should throw a UserNotFoundException.
     *
     * @param getUserOutfitsRequest request object containing the customer ID
     * @return getUserOutfitsResult result object containing the API defined {@link OutfitModel}
     */
    public GetUserOutfitsResult handleRequest(final GetUserOutfitsRequest getUserOutfitsRequest) {
        log.info("Received GetUserOutfitsRequest {}", getUserOutfitsRequest);

        String customerId =  getUserOutfitsRequest.getCustomerId();

        List<Outfit> usersOutfits = outfitDao.getAllOutfitsForUser(customerId);
        List<OutfitModel> usersOutfitModels = new ModelConverter().toOutfitModelList(usersOutfits);

        return GetUserOutfitsResult.builder()
                .withOutfits(usersOutfitModels)
                .build();
    }
}
