package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetUserClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetUserClothingResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.models.ClothingModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

public class GetUserClothingActivity {

    private final Logger log = LogManager.getLogger();
    private final ClothingDao clothingDao;

    /**
     * Instantiates a new GetUserClothingActivity object.
     *
     * @param clothingDao ClothingDao to access the clothing table.
     */
    @Inject
    public GetUserClothingActivity(ClothingDao clothingDao) {
        this.clothingDao = clothingDao;
    }

    /**
     * This method handles the incoming request by retrieving the user's clothing items from the database.
     * It then returns a list of clothing items.
     * If the user does not exist, this should throw a UserNotFoundException.
     *
     * @param getUserClothingRequest request object containing customerID
     * @return getUserClothingResult object containing the API defined {@link ClothingModel}
     */
    public GetUserClothingResult handleRequest(final GetUserClothingRequest getUserClothingRequest) {
        log.info("Received GetUserClothingRequest {}", getUserClothingRequest);

        String customerId = getUserClothingRequest.getCustomerId();

        List<Clothing> usersClothing = clothingDao.getAllClothingForUser(customerId);
        List<ClothingModel> usersClothingModels = new ModelConverter().toClothingModelList(usersClothing);

        return GetUserClothingResult.builder()
                .withClothing(usersClothingModels)
                .build();
    }
}
