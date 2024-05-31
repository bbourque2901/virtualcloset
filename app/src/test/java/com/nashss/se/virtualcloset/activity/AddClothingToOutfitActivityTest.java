package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.AddClothingToOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.AddClothingToOutfitResult;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.exceptions.ClothingNotFoundException;
import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AddClothingToOutfitActivityTest {
    @Mock
    private OutfitDao outfitDao;
    @Mock
    private ClothingDao clothingDao;

    private AddClothingToOutfitActivity addClothingToOutfitActivity;

    @BeforeEach
    void setUp() {
        initMocks(this);
        addClothingToOutfitActivity = new AddClothingToOutfitActivity(outfitDao, clothingDao);
    }

    @Test
    public void handleRequest_noMatchingOutfitId_throwsOutfitNotFoundException() {
        //GIVEN
        String outfitId = "non id";
        AddClothingToOutfitRequest request = AddClothingToOutfitRequest.builder()
                .withId(outfitId)
                .withClothingId("clothingId")
                .withCustomerId("customerid")
                .build();

        when(outfitDao.getOutfit(outfitId)).thenThrow(new OutfitNotFoundException());

        //WHEN + THEN
        assertThrows(OutfitNotFoundException.class, () -> addClothingToOutfitActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_noMatchingClothingItem_throwsClothingNotFoundException() {
        //GIVEN
        Outfit outfit = new Outfit();

        outfit.setCustomerId("customerId");
        outfit.setId("id");

        String customerId = outfit.getCustomerId();
        String id = outfit.getId();
        String clothingId = "nonexistent";

        AddClothingToOutfitRequest request = AddClothingToOutfitRequest.builder()
                .withId(id)
                .withCustomerId(customerId)
                .withClothingId(clothingId)
                .build();

        when(outfitDao.getOutfit("id")).thenReturn(outfit);
        when(clothingDao.getClothing(clothingId)).thenThrow(new ClothingNotFoundException());

        assertThrows(ClothingNotFoundException.class, () -> addClothingToOutfitActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_validRequest_addsClothingItemToOutfit() {
        //GIVEN
        Outfit beginningOutfit = new Outfit();

        Clothing existingItem = new Clothing();
        existingItem.setClothingId("existingid");
        existingItem.setWornCount(1);
        existingItem.setCategory("category");
        existingItem.setFit("fit");
        existingItem.setColor("color");
        existingItem.setOccasion("occasion");
        existingItem.setWeather("weather");
        existingItem.setLength("length");

        LinkedList<Clothing> clothingItems = new LinkedList<>();
        clothingItems.add(existingItem);

        beginningOutfit.setId("id");
        beginningOutfit.setCustomerId("customerId");
        beginningOutfit.setClothingItems(clothingItems);

        String customerId = beginningOutfit.getCustomerId();
        String outfitId = beginningOutfit.getId();

        Clothing newClothing = new Clothing();
        newClothing.setClothingId("clothingId");
        newClothing.setWornCount(1);
        newClothing.setCategory("category");
        newClothing.setFit("fit");
        newClothing.setColor("color");
        newClothing.setOccasion("occasion");
        newClothing.setWeather("weather");
        newClothing.setLength("length");


        String newClothingId = newClothing.getClothingId();

        when(outfitDao.getOutfit(outfitId)).thenReturn(beginningOutfit);
        when(outfitDao.saveOutfit(beginningOutfit)).thenReturn(beginningOutfit);
        when(clothingDao.getClothing(newClothingId)).thenReturn(newClothing);

        AddClothingToOutfitRequest request = AddClothingToOutfitRequest.builder()
                .withId(outfitId)
                .withCustomerId(customerId)
                .withClothingId(newClothingId)
                .build();

        //WHEN
        AddClothingToOutfitResult result = addClothingToOutfitActivity.handleRequest(request);

        //THEN
        verify(outfitDao).saveOutfit(beginningOutfit);
        assertEquals(2, result.getClothingList().size());
    }
}