package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.CreateClothingRequest;
import com.nashss.se.virtualcloset.activity.results.CreateClothingResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.exceptions.InvalidAttributeValueException;
import com.nashss.se.virtualcloset.models.ClothingModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.UUID;

public class CreateClothingActivity {
    private final Logger log = LogManager.getLogger();
    private final ClothingDao clothingDao;

    /**
     * Instantiates a new CreateClothingActivity object.
     *
     * @param clothingDao ClothingDao to access the clothing table.
     */
    @Inject
    public CreateClothingActivity(ClothingDao clothingDao) {
        this.clothingDao = clothingDao;
    }

    /**
     * This method creates a new clothing item with the provided clothing specifics from the request.
     *
     * It then returns a newly created clothing item.
     *
     * If the provided customerId has invalid characters, it throws an InvalidAttributeValueException.
     *
     * @param createClothingRequest request object with clothing specifics.
     * @return createClothingResult result clothing object.
     */
    public CreateClothingResult handleRequest(final CreateClothingRequest createClothingRequest) {
        log.info("Received CreateClothingRequest {}", createClothingRequest);

        if (!clothingDao.isValidString(createClothingRequest.getCustomerId())) {
            throw new InvalidAttributeValueException("CustomerId [" + createClothingRequest.getCustomerId() +
                    "] contains illegal characters");
        }
        String category = null;
        if (createClothingRequest.getCategory() != null) {
            category = createClothingRequest.getCategory();
        }
        String fit = null;
        if (createClothingRequest.getFit() != null) {
            fit = createClothingRequest.getFit();
        }
        String occasion = null;
        if (createClothingRequest.getOccasion() != null) {
            occasion = createClothingRequest.getOccasion();
        }
        String weather = null;
        if (createClothingRequest.getWeather() != null) {
            weather = createClothingRequest.getWeather();
        }

        Clothing newClothing = new Clothing();
        newClothing.setClothingId(UUID.randomUUID().toString());
        newClothing.setCustomerId(createClothingRequest.getCustomerId());
        newClothing.setWornCount(0);
        newClothing.setCategory(category);
        newClothing.setFit(fit);
        newClothing.setOccasion(occasion);
        newClothing.setWeather(weather);

        clothingDao.saveClothing(newClothing);

        ClothingModel clothingModel = new ModelConverter().toClothingModel(newClothing);
        return CreateClothingResult.builder()
                .withClothing(clothingModel)
                .build();
    }
}
