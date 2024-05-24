package com.nashss.se.virtualcloset.converters;

import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.models.OutfitModel;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {

    /**
     * Converts a provided {@link Outfit} into a {@link OutfitModel} representation.
     *
     * @param outfit the outfit to convert
     * @return the converted outfit
     */
    public OutfitModel toOutfitModel(Outfit outfit) {
        List<String> tags = null;
        if (outfit.getTags() != null) {
            tags = new ArrayList<>(outfit.getTags());
        }
        return OutfitModel.builder()
                .withId(outfit.getId())
                .withName(outfit.getName())
                .withCustomerId(outfit.getCustomerId())
                .withTags(tags)
                .withWornCount(outfit.getWornCount())
                .build();
    }
}
