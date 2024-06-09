package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetUserClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetUserClothingResult;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetUserClothingActivityTest {

    @Mock
    private ClothingDao clothingDao;

    private GetUserClothingActivity getUserClothingActivity;

    @BeforeEach
    void setUp() {
        initMocks(this);
        getUserClothingActivity = new GetUserClothingActivity(clothingDao);
    }

    @Test
    public void handleRequest_savedClothingFound_returnsListOfClothingModelsInResult() {
        //GIVEN
        String customerId = "customerId";

        Clothing clothing1 = new Clothing();
        clothing1.setClothingId("clothingId1");
        clothing1.setColor("color");
        clothing1.setLength("long");
        clothing1.setCustomerId(customerId);
        clothing1.setWornCount(0);

        Clothing clothing2 = new Clothing();
        clothing2.setClothingId("clothingId2");
        clothing2.setColor("co");
        clothing2.setLength("short");
        clothing2.setCustomerId(customerId);
        clothing2.setWornCount(0);

        List<Clothing> clothingList = List.of(clothing1, clothing2);

        when(clothingDao.getAllClothingForUser(clothing1.getCustomerId())).thenReturn(clothingList);

        GetUserClothingRequest request = GetUserClothingRequest.builder()
                .withCustomerId(clothing1.getCustomerId())
                .build();

        //WHEN
        GetUserClothingResult result = getUserClothingActivity.handleRequest(request);

        //THEN
        assertTrue(result.getClothing().size() == 2);
        assertNotNull(result);
    }
}