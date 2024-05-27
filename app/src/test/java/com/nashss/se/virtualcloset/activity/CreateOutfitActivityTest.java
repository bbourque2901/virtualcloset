package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.CreateOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.CreateOutfitResult;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class CreateOutfitActivityTest {
    @Mock
    private OutfitDao outfitDao;
    private CreateOutfitActivity createOutfitActivity;

    @BeforeEach
    void setUp() {
        initMocks(this);
        createOutfitActivity = new CreateOutfitActivity(outfitDao);
    }

    @Test
    public void handleRequest_withTags_createsAndSavesOutfitWithTags() {
        //GIVEN
        String name = "name";
        String customerId = "customerId";
        List<String> tags = List.of("tag");

        CreateOutfitRequest request = CreateOutfitRequest.builder()
                .withName(name)
                .withCustomerId(customerId)
                .withTags(tags)
                .build();

        //WHEN
        CreateOutfitResult result = createOutfitActivity.handleRequest(request);

        //THEN
        verify(outfitDao).saveOutfit(any(Outfit.class));

        assertNotNull(result.getOutfit().getId());
        assertEquals(name, result.getOutfit().getName());
        assertEquals(customerId, result.getOutfit().getCustomerId());
        assertEquals(tags, result.getOutfit().getTags());
    }

    @Test
    public void handleRequest_noTags_createsAndSavesPlaylistWithoutTags() {
        //GIVEN
        String name = "name";
        String customerId = "customerId";

        CreateOutfitRequest request = CreateOutfitRequest.builder()
                .withName(name)
                .withCustomerId(customerId)
                .build();

        //WHEN
        CreateOutfitResult result = createOutfitActivity.handleRequest(request);

        assertNotNull(result.getOutfit().getId());
        assertEquals(name, result.getOutfit().getName());
        assertEquals(customerId, result.getOutfit().getCustomerId());
        assertNull(result.getOutfit().getTags());
    }

    @Test
    public void handleRequest_invalidName_throwsInvalidAttributeValueException() {
        //GIVEN
        CreateOutfitRequest request = CreateOutfitRequest.builder()
                .withName("I'm illegal")
                .withCustomerId("customerId")
                .build();

        //WHEN and THEN
        assertThrows(InvalidAttributeValueException.class, () -> createOutfitActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_invalidCustomerId_throwsInvalidAttributeValueException() {
        //GIVEN
        CreateOutfitRequest request = CreateOutfitRequest.builder()
                .withName("Great name")
                .withCustomerId("awfulId*#(")
                .build();

        //WHEN and THEN
        assertThrows(InvalidAttributeValueException.class, () -> createOutfitActivity.handleRequest(request));
    }
}