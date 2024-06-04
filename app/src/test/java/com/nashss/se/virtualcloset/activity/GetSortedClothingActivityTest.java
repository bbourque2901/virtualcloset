package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetSortedClothingRequest;
import com.nashss.se.virtualcloset.activity.results.GetSortedClothingResult;
import com.nashss.se.virtualcloset.dynamodb.Clothing;
import com.nashss.se.virtualcloset.dynamodb.ClothingDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GetSortedClothingActivityTest {
    @Mock
    private ClothingDao clothingDao;
    private GetSortedClothingActivity getSortedClothingActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getSortedClothingActivity = new GetSortedClothingActivity(clothingDao);
    }

    @Test
    public void handleRequest_validCustomerId_returnsSortedClothing() {
        String customerId = "customerId";
        Clothing clothing1 = new Clothing();
        clothing1.setClothingId("clothing1");
        clothing1.setWornCount(3);
        clothing1.setCustomerId(customerId);

        Clothing clothing2 = new Clothing();
        clothing2.setCustomerId(customerId);
        clothing2.setWornCount(5);
        clothing2.setClothingId("clothing2");

        List<Clothing> clothes = Arrays.asList(clothing2, clothing1);
        when(clothingDao.getClothingSortedByWornCount(customerId, false)).thenReturn(clothes);

        GetSortedClothingRequest request = GetSortedClothingRequest.builder()
                .withCustomerId(customerId)
                .build();

        GetSortedClothingResult result = getSortedClothingActivity.handleRequest(request);

        assertNotNull(result);
        assertEquals(2, result.getClothes().size());
        assertEquals("clothing2", result.getClothes().get(0).getClothingId());
        assertEquals("clothing1", result.getClothes().get(1).getClothingId());
        verify(clothingDao, times(1)).getClothingSortedByWornCount(customerId, false);
    }

}