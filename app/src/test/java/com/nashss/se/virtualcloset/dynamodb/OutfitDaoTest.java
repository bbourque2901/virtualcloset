package com.nashss.se.virtualcloset.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class OutfitDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    private OutfitDao outfitDao;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        outfitDao = new OutfitDao(dynamoDBMapper);
    }

    @Test
    public void getOutfit_withOutfitId_callsMapperWithPartitionKey() {
        //GIVEN
        String outfitId = "outfirId";
        when(dynamoDBMapper.load(Outfit.class, outfitId)).thenReturn(new Outfit());

        //WHEN
        Outfit outfit = outfitDao.getOutfit(outfitId);

        //THEN
        assertNotNull(outfit);
        verify(dynamoDBMapper).load(Outfit.class, outfitId);
    }

    @Test
    public void getOutfit_outfitIdNotFound_throwsOutfitNotFoundException() {
        //GIVEN
        String nonexistentOutfitId = "no id";
        when(dynamoDBMapper.load(Outfit.class, nonexistentOutfitId)).thenReturn(null);

        //WHEN & THEN
        assertThrows(OutfitNotFoundException.class, () -> outfitDao.getOutfit(nonexistentOutfitId));
    }
}