package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetSortedOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetSortedOutfitResult;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GetSortedOutfitActivityTest {

    @Mock
    private OutfitDao outfitDao;

    private GetSortedOutfitActivity getSortedOutfitActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getSortedOutfitActivity = new GetSortedOutfitActivity(outfitDao);
    }

    @Test
    public void handleRequest_validCustomerId_returnsSortedOutfits() {
        String customerId = "validCustomerId";
        Outfit outfit1 = new Outfit();
        outfit1.setId("outfit1");
        outfit1.setWornCount(3);
        outfit1.setCustomerId(customerId);

        Outfit outfit2 = new Outfit();
        outfit2.setId("outfit2");
        outfit2.setWornCount(5);
        outfit2.setCustomerId(customerId);

        List<Outfit> outfits = Arrays.asList(outfit2, outfit1);
        when(outfitDao.getOutfitsSortedByWornCount(customerId, false)).thenReturn(outfits);

        GetSortedOutfitRequest request = GetSortedOutfitRequest.builder()
                .withCustomerId(customerId)
                .build();

        GetSortedOutfitResult result = getSortedOutfitActivity.handleRequest(request);

        assertNotNull(result);
        assertEquals(2, result.getOutfits().size());
        assertEquals("outfit2", result.getOutfits().get(0).getId());
        assertEquals("outfit1", result.getOutfits().get(1).getId());
        verify(outfitDao, times(1)).getOutfitsSortedByWornCount(customerId, false);
    }

}