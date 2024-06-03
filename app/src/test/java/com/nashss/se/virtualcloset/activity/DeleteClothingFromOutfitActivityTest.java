package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.DeleteClothingFromOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.DeleteClothingFromOutfitResult;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.exceptions.ClothingNotFoundException;
import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class DeleteClothingFromOutfitActivityTest {
    @Mock
    private OutfitDao outfitDao;
    @Mock
    private ClothingDao clothingDao;
    private DeleteClothingFromOutfitActivity deleteClothingFromOutfitActivity;

    @BeforeEach
    void setUp() {
        initMocks(this);
        this.deleteClothingFromOutfitActivity = new DeleteClothingFromOutfitActivity(outfitDao, clothingDao);
    }

    @Test
    public void handleRequest_validRequest_removedFromOutfit() {
        //GIVEN
        Clothing existingItem = new Clothing();
        existingItem.setClothingId("existingid1");
        existingItem.setWornCount(1);
        existingItem.setCategory("category");
        existingItem.setFit("fit");
        existingItem.setColor("color");
        existingItem.setOccasion("occasion");
        existingItem.setWeather("weather");
        existingItem.setLength("length");

        Clothing byeClothing = new Clothing();
        byeClothing.setClothingId("existingid");
        byeClothing.setWornCount(1);
        byeClothing.setCategory("category");
        byeClothing.setFit("fit");
        byeClothing.setColor("color");
        byeClothing.setOccasion("occasion");
        byeClothing.setWeather("weather");
        byeClothing.setLength("length");

        String removeId = byeClothing.getClothingId();

        Outfit ogOutfit = new Outfit();
        ogOutfit.setId("id1");
        ogOutfit.setName("name");
        ogOutfit.setCustomerId("customerId");
        ogOutfit.setTags(null);
        ogOutfit.setWornCount(0);
        ogOutfit.setClothingItems(List.of(existingItem, byeClothing));

        String outfitId = ogOutfit.getId();
        String customerId = ogOutfit.getCustomerId();

        when(outfitDao.getOutfit(outfitId)).thenReturn(ogOutfit);
        when(outfitDao.saveOutfit(ogOutfit)).thenReturn(ogOutfit);
        when(clothingDao.getClothing(removeId)).thenReturn(byeClothing);

        DeleteClothingFromOutfitRequest request = DeleteClothingFromOutfitRequest.builder()
                .withClothingId(removeId)
                .withId(outfitId)
                .withCustomerId(customerId)
                .build();

        //WHEN
        DeleteClothingFromOutfitResult result = deleteClothingFromOutfitActivity.handleRequest(request);

        //THEN
        assertEquals(1, result.getClothingModels().size());
        assertFalse(ogOutfit.getClothingItems().contains(byeClothing));
    }

    @Test
    public void handleRequest_noMatchingOutfitId_throwsOutfitNotFoundException() {
        //GIVEN
        String outfitId = "outfitid";
        DeleteClothingFromOutfitRequest request = DeleteClothingFromOutfitRequest.builder()
                .withId(outfitId)
                .withClothingId("clothingid")
                .withCustomerId("customerid")
                .build();

        when(outfitDao.getOutfit(outfitId)).thenThrow(new OutfitNotFoundException());

        //WHEN & THEN
        assertThrows(OutfitNotFoundException.class, () -> deleteClothingFromOutfitActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_noMatchingClothing_throwsClothingNotFoundException() {
        //GIVEN
        Outfit outfit = new Outfit();
        outfit.setId("id1");
        outfit.setName("name");
        outfit.setCustomerId("customerId");
        outfit.setTags(null);
        outfit.setWornCount(0);

        String outfitId = outfit.getId();
        String customerId = outfit.getCustomerId();
        String clothingId = "id";
        DeleteClothingFromOutfitRequest request = DeleteClothingFromOutfitRequest.builder()
                .withCustomerId(customerId)
                .withClothingId(clothingId)
                .withId(outfitId)
                .build();

        //WHEN
        when(outfitDao.getOutfit(outfitId)).thenReturn(outfit);
        when(clothingDao.getClothing(clothingId)).thenThrow(new ClothingNotFoundException());

        //THEN
        assertThrows(ClothingNotFoundException.class, () -> deleteClothingFromOutfitActivity.handleRequest(request));
    }

}