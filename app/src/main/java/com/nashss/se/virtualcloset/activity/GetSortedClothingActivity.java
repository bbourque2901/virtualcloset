package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetSortedClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetSortedClothingResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;

import com.nashss.se.virtualcloset.models.ClothingModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

public class GetSortedClothingActivity {
    private final Logger log = LogManager.getLogger();
    private final ClothingDao clothingDao;

    /**
     * Instantiates new GetSortedClothingActivity object.
     *
     * @param clothingDao to access clothing table.
     */
    @Inject
    GetSortedClothingActivity(ClothingDao clothingDao) {
        this.clothingDao = clothingDao;
    }

    /**
     * This method sorts the customer's clothes by their worn count in ascending order.
     *
     * @param getSortedClothingRequest Request containing specified customer and their sort order.
     * @return getSortedClothingResult with the updated clothing list.
     */
    public GetSortedClothingResult handleRequest(final GetSortedClothingRequest getSortedClothingRequest) {
        log.info("Received GetSortedClothingRequest {}", getSortedClothingRequest);

        String customerId = getSortedClothingRequest.getCustomerId();
        boolean ascending = getSortedClothingRequest.isAscending();

        List<Clothing> clothes = clothingDao.getClothingSortedByWornCount(customerId, ascending);
        List<ClothingModel> clothingModels = new ModelConverter().toClothingModelList(clothes);

        return GetSortedClothingResult.builder()
                .withClothes(clothingModels)
                .build();
    }
}
