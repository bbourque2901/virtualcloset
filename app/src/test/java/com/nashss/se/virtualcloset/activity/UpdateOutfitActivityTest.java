package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.activity.requests.UpdateOutfitRequest;
import com.nashss.se.virtualcloset.activity.results.UpdateOutfitResult;
import com.nashss.se.virtualcloset.dynamodb.Outfit;
import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import com.nashss.se.virtualcloset.exceptions.InvalidAttributeValueException;
import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class UpdateOutfitActivityTest {
    @Mock
    private OutfitDao outfitDao;

    private UpdateOutfitActivity updateOutfitActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        updateOutfitActivity = new UpdateOutfitActivity(outfitDao);
    }

    @Test
    public void handleRequst_goodRequest_updateOutfitName() {
        //GIVEN
        String id = "id";
        String expectedCustomerId = "expectedCustomerId";
        String expectedName = "name";
        int expectedWornCount = 11;

        UpdateOutfitRequest request = UpdateOutfitRequest.builder()
                .withId(id)
                .withCustomerId(expectedCustomerId)
                .withName(expectedName)
                .build();

        Outfit beginningOutfit = new Outfit();
        beginningOutfit.setCustomerId(expectedCustomerId);
        beginningOutfit.setName("old name");
        beginningOutfit.setWornCount(expectedWornCount);

        when(outfitDao.getOutfit(id)).thenReturn(beginningOutfit);
        when(outfitDao.saveOutfit(beginningOutfit)).thenReturn(beginningOutfit);

        //WHEN
        UpdateOutfitResult result = updateOutfitActivity.handleRequest(request);

        //THEN
        assertEquals(expectedName, result.getOutfit().getName());
        assertEquals(expectedCustomerId, result.getOutfit().getCustomerId());
        assertEquals(expectedWornCount, result.getOutfit().getWornCount());
    }

    @Test
    public void handleRequest_invalidName_throwsInvalidAttributeValueException() {
        //GIVEN
        UpdateOutfitRequest request = UpdateOutfitRequest.builder()
                .withName("I'm illegal")
                .withCustomerId("customerId")
                .build();

        //WHEN and THEN
        assertThrows(InvalidAttributeValueException.class, () -> updateOutfitActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_outfitDoesNotExist_throwsOutfitNotFoundException() {
        //GIVEN & WHEN
        String id = "id";
        String name = "name";
        String customerId = "customerId";
        UpdateOutfitRequest request = UpdateOutfitRequest.builder()
                .withId(id)
                .withName(name)
                .withCustomerId(customerId)
                .build();

        when(outfitDao.getOutfit(id)).thenThrow(new OutfitNotFoundException());

        //THEN
        assertThrows(OutfitNotFoundException.class, () -> updateOutfitActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_customerIdNotMatch_throwsSecurityException() {
        //GIVEN
        String id = "id";
        String name = "name";
        String customerId = "customerId";

        UpdateOutfitRequest request = UpdateOutfitRequest.builder()
                .withId(id)
                .withName(name)
                .withCustomerId(customerId)
                .build();

        Outfit differentCustomerIdOutfit = new Outfit();
        differentCustomerIdOutfit.setCustomerId("different id");

        when(outfitDao.getOutfit(id)).thenReturn(differentCustomerIdOutfit);

        //THEN
        assertThrows(SecurityException.class, () -> updateOutfitActivity.handleRequest(request));
    }
}