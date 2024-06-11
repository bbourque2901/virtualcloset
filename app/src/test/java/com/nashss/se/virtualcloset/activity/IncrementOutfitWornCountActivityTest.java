package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.IncrementOutfitWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementOutfitWornCountResult;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class IncrementOutfitWornCountActivityTest {

    @Mock
    private OutfitDao outfitDao;

    private IncrementOutfitWornCountActivity incrementOutfitWornCountActivity;

    @BeforeEach
    void setUp() {
        initMocks(this);
        incrementOutfitWornCountActivity = new IncrementOutfitWornCountActivity(outfitDao);
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
}