package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetClothingFromOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetClothingFromOutfitResult;
import com.nashss.se.virtualcloset.converters.ModelConverter;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetClothingFromOutfitActivityTest {

    @Mock
    private OutfitDao outfitDao;

    private GetClothingFromOutfitActivity getClothingFromOutfitActivity;
    @BeforeEach
    void setUp() {
        initMocks(this);
        getClothingFromOutfitActivity = new GetClothingFromOutfitActivity(outfitDao);
    }

    @Test
    public void handleRequest_outfitWithClothes_ReturnsClothesInOutfit() {
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

        GetClothingFromOutfitRequest request = GetClothingFromOutfitRequest.builder()
                .withId(outfitId)
                .build();
        when(outfitDao.getOutfit(outfitId)).thenReturn(beginningOutfit);

        GetClothingFromOutfitResult result = getClothingFromOutfitActivity.handleRequest(request);

        assertEquals(new ModelConverter().toClothingModelList(beginningOutfit.getClothingItems()), result.getClothingList());
    }

    @Test
    public void handleRequest_emptyOutfit_returnsEmptyList() {
        Outfit emptyOutfit = new Outfit();
        emptyOutfit.setClothingItems(new ArrayList<>());
        emptyOutfit.setId("id");
        String outfitId = emptyOutfit.getId();

        GetClothingFromOutfitRequest request = GetClothingFromOutfitRequest.builder()
                .withId(outfitId)
                .build();

        when(outfitDao.getOutfit(outfitId)).thenReturn(emptyOutfit);

        GetClothingFromOutfitResult result = getClothingFromOutfitActivity.handleRequest(request);

        assertTrue(result.getClothingList().isEmpty());

    }

    @Test
    public void handleRequest_noMatchingOutfitId_throwsOutfitNotFoundException() {
        String id = "id";
        GetClothingFromOutfitRequest request = GetClothingFromOutfitRequest.builder()
                .withId(id)
                .build();
        when(outfitDao.getOutfit(id)).thenThrow(new OutfitNotFoundException());

        assertThrows(OutfitNotFoundException.class, () -> getClothingFromOutfitActivity.handleRequest(request));
    }
}