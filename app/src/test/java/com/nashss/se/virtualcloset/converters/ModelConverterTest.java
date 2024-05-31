package com.nashss.se.virtualcloset.converters;

import com.google.common.collect.Sets;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.models.ClothingModel;
import com.nashss.se.virtualcloset.models.OutfitModel;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    public void toOutfitModel_withTags_convertsOutfit() {
        Outfit outfit = new Outfit();
        outfit.setId("id");
        outfit.setName("name");
        outfit.setCustomerId("customerId");
        outfit.setTags(Sets.newHashSet("tag"));
        outfit.setWornCount(0);

        OutfitModel outfitModel = modelConverter.toOutfitModel(outfit);
        assertEquals(outfit.getId(), outfitModel.getId());
        assertEquals(outfit.getName(), outfitModel.getName());
        assertEquals(outfit.getCustomerId(), outfitModel.getCustomerId());
        assertEquals(outfit.getTags(), Set.copyOf(outfitModel.getTags()));
        assertEquals(outfit.getWornCount(), outfitModel.getWornCount());
    }

    @Test
    public void toOutfitModel_nullTags_convertsOutfit() {
        Outfit outfit = new Outfit();
        outfit.setId("id");
        outfit.setName("name");
        outfit.setCustomerId("customerId");
        outfit.setTags(null);
        outfit.setWornCount(0);

        OutfitModel outfitModel = modelConverter.toOutfitModel(outfit);
        assertEquals(outfit.getId(), outfitModel.getId());
        assertEquals(outfit.getName(), outfitModel.getName());
        assertEquals(outfit.getCustomerId(), outfitModel.getCustomerId());
        assertNull(outfitModel.getTags());
        assertEquals(outfit.getWornCount(), outfitModel.getWornCount());
    }

    @Test
    public void toClothingModel_withAllValues_convertsClothing() {
        Clothing newClothing = new Clothing();
        newClothing.setClothingId("clothingId");
        newClothing.setWornCount(1);
        newClothing.setCategory("category");
        newClothing.setFit("fit");
        newClothing.setColor("color");
        newClothing.setOccasion("occasion");
        newClothing.setWeather("weather");
        newClothing.setLength("length");

        ClothingModel result = modelConverter.toClothingModel(newClothing);

        assertEquals(newClothing.getClothingId(), result.getClothingId());
        assertEquals(newClothing.getWornCount(), result.getWornCount());
        assertEquals(newClothing.getCategory(), result.getCategory());
        assertEquals(newClothing.getFit(), result.getFit());
        assertEquals(newClothing.getColor(), result.getColor());
        assertEquals(newClothing.getOccasion(), result.getOccasion());
        assertEquals(newClothing.getWeather(), result.getWeather());
        assertEquals(newClothing.getLength(), result.getLength());
    }

    @Test
    public void toClothingModel_withNullValues_convertsClothing() {
        Clothing newClothing = new Clothing();
        newClothing.setClothingId("clothingId");
        newClothing.setWornCount(1);
        newClothing.setCategory(null);
        newClothing.setFit(null);
        newClothing.setColor("color");
        newClothing.setOccasion(null);
        newClothing.setWeather(null);
        newClothing.setLength("length");

        ClothingModel result = modelConverter.toClothingModel(newClothing);

        assertEquals(newClothing.getClothingId(), result.getClothingId());
        assertEquals(newClothing.getWornCount(), result.getWornCount());
        assertNull(result.getCategory());
        assertNull(result.getFit());
        assertEquals(newClothing.getColor(), result.getColor());
        assertNull(result.getOccasion());
        assertNull(result.getWeather());
        assertEquals(newClothing.getLength(), result.getLength());
    }

}