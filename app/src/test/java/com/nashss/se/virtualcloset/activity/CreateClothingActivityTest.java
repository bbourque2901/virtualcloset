package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.CreateClothingRequest;
import com.nashss.se.virtualcloset.activity.results.CreateClothingResult;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import com.nashss.se.virtualcloset.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class CreateClothingActivityTest {
    @Mock
    private ClothingDao clothingDao;

    private CreateClothingActivity createClothingActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        createClothingActivity = new CreateClothingActivity(clothingDao);
    }

    @Test
    public void handleRequest_withAllField_createsAndSavesClothingWithALlFields() {
        //GIVEN
        String customerId = "customerId";
        String category = "category";
        String color = "color";
        String fit = "fit";
        String length = "length";
        String weather = "weather";
        String occasion = "occasion";

        CreateClothingRequest request = CreateClothingRequest.builder()
                .withCustomerId(customerId)
                .withCategory(category)
                .withColor(color)
                .withFit(fit)
                .withLength(length)
                .withOccasion(occasion)
                .withWeather(weather)
                .build();

        //WHEN
        CreateClothingResult result = createClothingActivity.handleRequest(request);

        //THEN
        verify(clothingDao).saveClothing(any(Clothing.class));

        assertNotNull(result.getClothing().getClothingId());
        assertEquals(customerId, result.getClothing().getCustomerId());
        assertEquals(category, result.getClothing().getCategory());
        assertEquals(color, result.getClothing().getColor());
        assertEquals(fit, result.getClothing().getFit());
        assertEquals(length, result.getClothing().getLength());
        assertEquals(weather, result.getClothing().getWeather());
        assertEquals(occasion, result.getClothing().getOccasion());
    }

    @Test
    public void handleRequest_optionalFieldsEmpty_createsAndSavesOutfitWithoutThem() {
        //GIVEN
        String customerId = "customerId";
        String color = "color";
        String length = "length";

        CreateClothingRequest request = CreateClothingRequest.builder()
                .withCustomerId(customerId)
                .withColor(color)
                .withLength(length)
                .build();

        //WHEN
        CreateClothingResult result = createClothingActivity.handleRequest(request);

        //THEN
        assertNotNull(result.getClothing().getClothingId());
        assertEquals(customerId, result.getClothing().getCustomerId());
        assertEquals(color, result.getClothing().getColor());
        assertEquals(length, result.getClothing().getLength());
        assertNull(result.getClothing().getFit());
        assertNull(result.getClothing().getCategory());
        assertNull(result.getClothing().getOccasion());
        assertNull(result.getClothing().getWeather());
    }

    @Test
    public void handleRequest_invalidCustomerId_throwsInvalidAttributeValueException() {
        //GIVEN
        CreateClothingRequest request = CreateClothingRequest.builder()
                .withCustomerId("awfulId*#(")
                .build();

        //WHEN and THEN
        assertThrows(InvalidAttributeValueException.class, () -> createClothingActivity.handleRequest(request));
    }
}