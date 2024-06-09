package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.IncrementOutfitWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementOutfitWornCountResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.models.OutfitModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class IncrementOutfitWornCountActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;

    /**
     * Instantiates new IncrementOutfitWornCountActivity object.
     * @param outfitDao outfitDao accesses the outfit table
     */
    @Inject
    public IncrementOutfitWornCountActivity(OutfitDao outfitDao) {
        this.outfitDao = outfitDao;
    }

    /**
     * This method increases the worn count of the specified outfit by 1.
     * @param request request with the outfit id
     * @return outfit model with incremented worn count
     */
    public IncrementOutfitWornCountResult handleRequest(final IncrementOutfitWornCountRequest request) {
        log.info("Received IncrementOutfitWornCountRequest {}", request);

        String requestedId = request.getId();
        Outfit outfit = outfitDao.getOutfit(requestedId);

        outfit.setWornCount(outfit.getWornCount() + 1);
        outfitDao.saveOutfit(outfit);

        OutfitModel outfitModel = new ModelConverter().toOutfitModel(outfit);

        return IncrementOutfitWornCountResult.builder()
                .withOutfit(outfitModel)
                .build();
    }
}
