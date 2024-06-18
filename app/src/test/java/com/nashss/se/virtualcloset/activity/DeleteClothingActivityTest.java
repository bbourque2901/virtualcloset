package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.DeleteClothingRequest;
import com.nashss.se.virtualcloset.activity.results.DeleteClothingResult;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class DeleteClothingActivityTest {

    @Mock
    private ClothingDao clothingDao;

    @Mock
    private OutfitDao outfitDao;

    private DeleteClothingActivity deleteClothingActivity;

    @BeforeEach
    void setUp() {
        initMocks(this);
        deleteClothingActivity = new DeleteClothingActivity(clothingDao, outfitDao);
    }

    @Test
    public void handleRequest_savedClothingFound_returnsDeletedClothingModelInResult() {
        //GIVEN
        String clothingId = "clothingId";
        String expectedCustomerId = "customerid";

        Clothing clothing = new Clothing();
        clothing.setClothingId(clothingId);
        clothing.setCustomerId(expectedCustomerId);
        clothing.setWornCount(0);

        when(clothingDao.removeClothing(clothingId)).thenReturn(clothing);

        DeleteClothingRequest request = DeleteClothingRequest.builder()
                .withClothingId(clothingId)
                .build();

        //WHEN
        DeleteClothingResult result = deleteClothingActivity.handleRequest(request);

        //THEN
        assertEquals(clothingId, result.getClothing().getClothingId());
        assertEquals(expectedCustomerId, result.getClothing().getCustomerId());
    }

    @Test
    public void handleRequest_deleteClothing_deletesFromClothes() {
        //GIVEN
        String clothingId = "clothingId";

        when(clothingDao.removeClothing(clothingId)).thenReturn(null);

        DeleteClothingRequest request = DeleteClothingRequest.builder()
                .withClothingId(clothingId)
                .build();

        //WHEN
        DeleteClothingResult result = deleteClothingActivity.handleRequest(request);

        //THEN
        assertNull(result.getClothing());
    }
}