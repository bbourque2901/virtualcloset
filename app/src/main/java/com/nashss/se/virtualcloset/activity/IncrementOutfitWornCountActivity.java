package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.IncrementOutfitWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementOutfitWornCountResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.models.OutfitModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

public class IncrementOutfitWornCountActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;
    private final ClothingDao clothingDao;

    /**
     * Instantiates new IncrementOutfitWornCountActivity object.
     * @param outfitDao outfitDao accesses the outfit table
     * @param clothingDao clothingDao accesses the clothing table
     */
    @Inject
    public IncrementOutfitWornCountActivity(OutfitDao outfitDao, ClothingDao clothingDao) {
        this.outfitDao = outfitDao;
        this.clothingDao = clothingDao;
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

        //also increment worn count for clothing items in the outfit
        List<Clothing> clothingItems = outfit.getClothingItems();
        if (clothingItems != null && !clothingItems.isEmpty()) {
            for (Clothing clothing : clothingItems) {
                clothing.setWornCount(clothing.getWornCount() + 1);
                clothingDao.saveClothing(clothing);
            }
        } else {
            log.info("No clothing items are in this outfit yet");
        }

        OutfitModel outfitModel = new ModelConverter().toOutfitModel(outfit);

        return IncrementOutfitWornCountResult.builder()
                .withOutfit(outfitModel)
                .build();
    }
}
