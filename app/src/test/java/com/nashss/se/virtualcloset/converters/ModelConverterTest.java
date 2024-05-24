package com.nashss.se.virtualcloset.converters;

import com.google.common.collect.Sets;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
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

}