package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetClothingResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.models.ClothingModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetClothingActivity {
    private final Logger log = LogManager.getLogger();
    private final ClothingDao clothingDao;

    /**
     * Instantiates a new GetClothingActivity object.
     *
     * @param clothingDao ClothingDao to access the clothing table.
     */
    @Inject
    public GetClothingActivity(ClothingDao clothingDao) {
        this.clothingDao = clothingDao;
    }

    /**
     * This method handles the incoming request by retrieving the clothing item from the database.
     *
     * It then returns the clothing item.
     *
     * If the clothing item doesn't exist, this should throw a ClothingNotFoundException.
     * @param getClothingRequest request object containing the clothingId
     * @return result object containing the API defined {@link ClothingModel}
     */
    public GetClothingResult handleRequest(final GetClothingRequest getClothingRequest) {
        log.info("Received GetClothingRequest {}", getClothingRequest);

        String requestedClothingId = getClothingRequest.getClothingId();
        Clothing clothing = clothingDao.getClothing(requestedClothingId);

        ClothingModel clothingModel = new ModelConverter().toClothingModel(clothing);

        return GetClothingResult.builder()
                .withClothing(clothingModel)
                .build();
    }
}
