package com.nashss.se.virtualcloset.converters;

import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.models.ClothingModel;
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

    /**
     * Converts a provided clothing item into a ClothingModel representation.
     *
     * @param clothing the Clothing item to convert to ClothingModel
     * @return the converted ClothingModel with fields mapped from clothing
     */
    public ClothingModel toClothingModel(Clothing clothing) {
        return ClothingModel.builder()
                .withClothingId(clothing.getClothingId())
                .withCategory(clothing.getCategory())
                .withColor(clothing.getColor())
                .withFit(clothing.getFit())
                .withLength(clothing.getLength())
                .withOccasion(clothing.getOccasion())
                .withWeather(clothing.getWeather())
                .withWornCount(clothing.getWornCount())
                .build();
    }

    /**
     * Converts a list of clothing items to a list of ClothingModels.
     *
     * @param clothing The clothing items to convert to ClothingModels
     * @return The converted list of ClothingModels
     */
    public List<ClothingModel> toClothingModelList(List<Clothing> clothing) {
        List<ClothingModel> clothingModels = new ArrayList<>();

        for (Clothing clothingItem : clothing) {
            clothingModels.add(toClothingModel(clothingItem));
        }

        return clothingModels;
    }
}
