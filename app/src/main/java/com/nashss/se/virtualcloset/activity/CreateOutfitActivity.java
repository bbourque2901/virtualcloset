package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.CreateOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.CreateOutfitResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.exceptions.InvalidAttributeValueException;
import com.nashss.se.virtualcloset.models.OutfitModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;

public class CreateOutfitActivity {
    private final Logger log = LogManager.getLogger();
    private final OutfitDao outfitDao;

    /**
     * Instantiates a new CreateOutfitActivity object.
     *
     * @param outfitDao OutfitDao to access the outfits table.
     */
    @Inject
    public CreateOutfitActivity(OutfitDao outfitDao) {
        this.outfitDao = outfitDao;
    }

    /**
     * This method creates a new outfit with the provided outfit name and customerId from request.
     *
     * It then returns a newly created outfit.
     *
     * If the provided outfit name or customer Id has invalid characters, it throws an
     * InvalidAttributeValueException
     *
     * @param createOutfitRequest request object with outfit name and customer ID
     * @return createOutfitResult result outfit object.
     */
    public CreateOutfitResult handleRequest(final CreateOutfitRequest createOutfitRequest) {
        log.info("Received CreateOutfitRequest {}", createOutfitRequest);

        if (!outfitDao.isValidString(createOutfitRequest.getName())) {
            throw new InvalidAttributeValueException("Outfitlist name [" + createOutfitRequest.getName() +
                    "] contains illegal characters");
        }

        Set<String> outfitTags = null;
        if (createOutfitRequest.getTags() != null) {
            outfitTags = new HashSet<>(createOutfitRequest.getTags());
        }

        Outfit newOutfit = new Outfit();
        newOutfit.setId(UUID.randomUUID().toString());
        newOutfit.setName(createOutfitRequest.getName());
        newOutfit.setCustomerId(createOutfitRequest.getCustomerId());
        newOutfit.setClothingItems(new ArrayList<>());
        newOutfit.setWornCount(0);
        newOutfit.setTags(outfitTags);

        outfitDao.saveOutfit(newOutfit);

        OutfitModel outfitModel = new ModelConverter().toOutfitModel(newOutfit);
        return CreateOutfitResult.builder()
                .withOutfit(outfitModel)
                .build();
    }
}
