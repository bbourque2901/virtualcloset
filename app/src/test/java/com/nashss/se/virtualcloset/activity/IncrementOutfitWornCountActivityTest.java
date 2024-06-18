package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.IncrementOutfitWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementOutfitWornCountResult;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class IncrementOutfitWornCountActivityTest {

    @Mock
    private OutfitDao outfitDao;

    @Mock
    private ClothingDao clothingDao;

    private IncrementOutfitWornCountActivity incrementOutfitWornCountActivity;

    @BeforeEach
    void setUp() {
        initMocks(this);
        incrementOutfitWornCountActivity = new IncrementOutfitWornCountActivity(outfitDao, clothingDao);
    }

    @Test
    public void handleRequest_validId_incrementsWornCountBy1() {
        String outfitId = "validId";
        Outfit outfit = new Outfit();
        outfit.setId(outfitId);
        outfit.setCustomerId("customerId");
        outfit.setWornCount(5);

        when(outfitDao.getOutfit(outfitId)).thenReturn(outfit);
        when(outfitDao.saveOutfit(any(Outfit.class))).thenReturn(outfit);

        IncrementOutfitWornCountRequest request = IncrementOutfitWornCountRequest.builder()
                .withId(outfitId)
                .build();

        IncrementOutfitWornCountResult result = incrementOutfitWornCountActivity.handleRequest(request);

        assertNotNull(result);
        assertEquals(6, result.getOutfit().getWornCount());
    }

    @Test
    public void handleRequest_validId_incrementsClothingWornCount() {
        String outfitId = "id";
        Outfit outfit = new Outfit();
        outfit.setId(outfitId);
        outfit.setCustomerId("customerId");
        outfit.setWornCount(5);

        Clothing clothing1 = new Clothing();
        clothing1.setClothingId("clothing1");
        clothing1.setWornCount(3);

        Clothing clothing2 = new Clothing();
        clothing2.setClothingId("clothing2");
        clothing2.setWornCount(2);

        outfit.setClothingItems(List.of(clothing1, clothing2));

        when(outfitDao.getOutfit(outfitId)).thenReturn(outfit);
        when(outfitDao.saveOutfit(any(Outfit.class))).thenReturn(outfit);
        when(clothingDao.saveClothing(any(Clothing.class))).thenReturn(clothing1).thenReturn(clothing2);

        IncrementOutfitWornCountRequest request = IncrementOutfitWornCountRequest.builder()
                .withId(outfitId)
                .withCustomerId("customerId")
                .build();

        IncrementOutfitWornCountResult result = incrementOutfitWornCountActivity.handleRequest(request);

        assertEquals(6, result.getOutfit().getWornCount());
        assertEquals(4, clothing1.getWornCount());
        assertEquals(3, clothing2.getWornCount());
    }
}