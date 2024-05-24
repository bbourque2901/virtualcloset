package com.nashss.se.virtualcloset.activity;

import com.google.common.collect.Sets;
import com.nashss.se.virtualcloset.activity.requests.GetOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.GetOutfitResult;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetOutfitActivityTest {
    @Mock
    private OutfitDao outfitDao;
    private GetOutfitActivity getOutfitActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getOutfitActivity = new GetOutfitActivity(outfitDao);
    }

    @Test
    public void handleRequest_savedOutfitFound_returnsOutfitModelInResult() {
        //GIVEN
        String expectedId = "id";
        String expectedName = "name";
        String expectedCustomerId = "customerId";
        List<String> expectedTags = List.of("tag");
        int expectedWornCount = 0;

        Outfit outfit = new Outfit();
        outfit.setId(expectedId);
        outfit.setName(expectedName);
        outfit.setCustomerId(expectedCustomerId);
        outfit.setTags(Sets.newHashSet(expectedTags));
        outfit.setWornCount(expectedWornCount);

        when(outfitDao.getOutfit(expectedId)).thenReturn(outfit);

        GetOutfitRequest request = GetOutfitRequest.builder()
                .withId(expectedId)
                .build();

        //WHEN
        GetOutfitResult result = getOutfitActivity.handleRequest(request);

        //THEN
        assertEquals(expectedId, result.getOutfit().getId());
        assertEquals(expectedName, result.getOutfit().getName());
        assertEquals(expectedWornCount, result.getOutfit().getWornCount());
        assertEquals(expectedTags, result.getOutfit().getTags());
    }

}