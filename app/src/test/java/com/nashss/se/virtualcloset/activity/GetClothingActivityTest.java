package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetClothingResult;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetClothingActivityTest {
    @Mock
    private ClothingDao clothingDao;

    private GetClothingActivity getClothingActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getClothingActivity = new GetClothingActivity(clothingDao);
    }

    @Test
    public void handleRequest_savedClothingFound_returnsClothingModelInResult() {
        //GIVEN
        String expectedId = "id";
        String expectedCategory = "category";
        String expectedColor = "color";
        String expectedLength = "length";
        int expectedWornCount = 0;

        Clothing clothing = new Clothing();
        clothing.setClothingId(expectedId);
        clothing.setCategory(expectedCategory);
        clothing.setColor(expectedColor);
        clothing.setLength(expectedLength);
        clothing.setWornCount(expectedWornCount);

        when(clothingDao.getClothing(expectedId)).thenReturn(clothing);

        GetClothingRequest request = GetClothingRequest.builder()
                .withClothingId(expectedId)
                .build();

        //WHEN
        GetClothingResult result = getClothingActivity.handleRequest(request);

        //THEN
        assertEquals(expectedId, result.getClothing().getClothingId());
        assertEquals(expectedCategory, result.getClothing().getCategory());
        assertEquals(expectedColor, result.getClothing().getColor());
        assertEquals(expectedLength, result.getClothing().getLength());
        assertEquals(expectedWornCount, result.getClothing().getWornCount());
    }
}