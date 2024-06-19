package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.IncrementClothingWornCountRequest;
import com.nashss.se.virtualcloset.activity.results.IncrementClothingWornCountResult;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class IncrementClothingWornCountActivityTest {

    @Mock
    private ClothingDao clothingDao;

    private IncrementClothingWornCountActivity incrementClothingWornCountActivity;
    @BeforeEach
    void setUp() {
        initMocks(this);
        incrementClothingWornCountActivity = new IncrementClothingWornCountActivity(clothingDao);
    }

    @Test
    public void handleRequest_validClothingId_incrementsWornCountBy1() {
        String clothingId = "validId";
        Clothing clothing = new Clothing();
        clothing.setClothingId(clothingId);
        clothing.setWornCount(5);
        clothing.setLength("long");
        clothing.setColor("blue");

        when(clothingDao.getClothing(clothingId)).thenReturn(clothing);

        when(clothingDao.saveClothing(any(Clothing.class))).thenReturn(clothing);

        IncrementClothingWornCountRequest request = IncrementClothingWornCountRequest.builder()
                .withClothingId(clothingId)
                .withCustomerId("customerId")
                .build();

        IncrementClothingWornCountResult result = incrementClothingWornCountActivity.handleRequest(request);

        assertNotNull(result);
        assertEquals(6, result.getClothing().getWornCount());
    }
}