package com.nashss.se.virtualcloset.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.nashss.se.virtualcloset.exceptions.OutfitNotFoundException;
import com.nashss.se.virtualcloset.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class OutfitDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    private OutfitDao outfitDao;
    @Mock
    private PaginatedScanList<Outfit> scanList;
    @Captor
    ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor;

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

    @Test
    public void saveOutfit_callsMapperWithOutfit() {
        //GIVEN
        Outfit outfit = new Outfit();

        //WHEN
        Outfit result = outfitDao.saveOutfit(outfit);

        //THEN
        verify(dynamoDBMapper).save(outfit);
        assertEquals(outfit, result);
    }

    @Test
    public void getAllOutfitsForUser_withCustomerId_returnsListofOutfits() {
        //GIVEN
        String id = "id";
        when(dynamoDBMapper.scan(eq(Outfit.class), any(DynamoDBScanExpression.class))).thenReturn(scanList);

        //WHEN
        List<Outfit> results = outfitDao.getAllOutfitsForUser(id);

        //THEN
        assertNotNull(results);
        verify(dynamoDBMapper).scan(eq(Outfit.class), scanExpressionArgumentCaptor.capture());
        assertTrue(!results.isEmpty());
    }

    @Test
    public void getAllOutfitsForUser_withNullId_throwsUserNotFoundException() {
        //GIVEN
        String id = null;

        when(dynamoDBMapper.scan(eq(Outfit.class), any(DynamoDBScanExpression.class))).thenReturn(scanList);

        //WHEN + THEN
        assertThrows(UserNotFoundException.class, () -> outfitDao.getAllOutfitsForUser(id));
    }
}