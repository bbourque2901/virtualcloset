package com.nashss.se.virtualcloset.converters;

import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.models.OutfitModel;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {
    public OutfitModel toOutfitModel(Outfit outfit) {
        List<String> tags = null;
        if (outfit.getTags() != null) {
            tags = new ArrayList<>(outfit.getTags());
        }
        return OutfitModel.builder()
                .withId(outfit.getId())
                .withName(outfit.getName())
                .withTags(tags)
                .withWornCount(outfit.getWornCount())
                .build();
    }
}
