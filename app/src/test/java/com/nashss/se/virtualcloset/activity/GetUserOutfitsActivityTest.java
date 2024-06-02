package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.GetUserOutfitsRequest;
import com.nashss.se.virtualcloset.activity.results.GetUserOutfitsResult;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetUserOutfitsActivityTest {
    @Mock
    private OutfitDao outfitDao;

    private GetUserOutfitsActivity getUserOutfitsActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getUserOutfitsActivity = new GetUserOutfitsActivity(outfitDao);
    }

    @Test
    public void handleRequest_savedOutfitsFound_returnsListofOutfitsModelInResults() {
        //GIVEN
        String customerId = "customerID";

        Outfit outfit1 = new Outfit();
        outfit1.setId("id1");
        outfit1.setName("name");
        outfit1.setCustomerId(customerId);
        outfit1.setTags(null);
        outfit1.setWornCount(0);

        Outfit outfit2 = new Outfit();
        outfit2.setId("id2");
        outfit2.setName("name2");
        outfit2.setCustomerId(customerId);
        outfit2.setTags(null);
        outfit2.setWornCount(0);

        List<Outfit> outfits = List.of(outfit1, outfit2);

        when(outfitDao.getAllOutfitsForUser(outfit1.getCustomerId())).thenReturn(outfits);

        GetUserOutfitsRequest request = GetUserOutfitsRequest.builder()
                .withCustomerId(outfit1.getCustomerId())
                .build();

        //WHEN
        GetUserOutfitsResult result = getUserOutfitsActivity.handleRequest(request);

        //THEN
        assertTrue(result.getOutfits().size() == 2);
        assertNotNull(result);
    }
}