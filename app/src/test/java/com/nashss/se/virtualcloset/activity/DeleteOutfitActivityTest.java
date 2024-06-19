package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.DeleteOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.DeleteOutfitResult;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class DeleteOutfitActivityTest {
    @Mock
    private OutfitDao outfitDao;

    private DeleteOutfitActivity deleteOutfitActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        deleteOutfitActivity = new DeleteOutfitActivity(outfitDao);
    }

    @Test
    public void handleRequest_savedOutfitFound_returnsDeletedOutfitModelInResult() {
        //GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        String expectedCustomerId = "customerid";

        Outfit outfit = new Outfit();
        outfit.setId(expectedId);
        outfit.setName(expectedName);
        outfit.setCustomerId(expectedCustomerId);
        outfit.setWornCount(0);

        when(outfitDao.removeOutfit(expectedId)).thenReturn(outfit);

        DeleteOutfitRequest request = DeleteOutfitRequest.builder()
                .withId(expectedId)
                .withCustomerId(expectedCustomerId)
                .build();

        //WHEN
        DeleteOutfitResult result = deleteOutfitActivity.handleRequest(request);

        //THEN
        assertEquals(expectedId, result.getOutfit().getId());
        assertEquals(expectedName, result.getOutfit().getName());
        assertEquals(expectedCustomerId, result.getOutfit().getCustomerId());
    }

    @Test
    public void handleRequest_deletedOutfit_deletesFromOutfits() {
        //given
        String outfitId = "outfitId";

        when(outfitDao.removeOutfit(outfitId)).thenReturn(null);

        DeleteOutfitRequest request = DeleteOutfitRequest.builder()
                .withId(outfitId)
                .build();

        //WHEN
        DeleteOutfitResult result = deleteOutfitActivity.handleRequest(request);

        //THEN
        assertNull(result.getOutfit());
    }
}