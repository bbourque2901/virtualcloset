package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.IncrementClothingWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementClothingWornCountResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.models.ClothingModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class IncrementClothingWornCountActivity {
    private final Logger log = LogManager.getLogger();
    private final ClothingDao clothingDao;

    /**
     * Instantiates a new IncrementClothingWornCountActivity object.
     * @param clothingDao clothingDao to access the clothing table
     */
    @Inject
    public IncrementClothingWornCountActivity(ClothingDao clothingDao) {
        this.clothingDao = clothingDao;
    }

    /**
     * This method increases the specified clothing's worn count by 1.
     * @param request request with clothingId
     * @return clothingModel with incremented worn count
     */
    public IncrementClothingWornCountResult handleRequest(final IncrementClothingWornCountRequest request) {
        log.info("Received IncrementClothingWornCountRequest {}", request);

        String clothingId = request.getClothingId();
        Clothing clothing = clothingDao.getClothing(clothingId);

        clothing.setWornCount(clothing.getWornCount() + 1);
        clothingDao.saveClothing(clothing);

        ClothingModel clothingModel = new ModelConverter().toClothingModel(clothing);

        return IncrementClothingWornCountResult.builder()
                .withClothing(clothingModel)
                .build();
    }
}
