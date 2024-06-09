package com.nashss.se.virtualcloset.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.nashss.se.virtualcloset.exceptions.ClothingNotFoundException;
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

class ClothingDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    private ClothingDao clothingDao;

    @Mock
    private PaginatedScanList<Clothing> scanList;
    @Captor
    ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        clothingDao = new ClothingDao(dynamoDBMapper);
    }

    @Test
    public void getClothingItem_withExistingClothingId_loadsClothingItem() {
        String clothingId = "clothingId";
        Clothing clothing = new Clothing();
        clothing.setClothingId(clothingId);

        when(dynamoDBMapper.load(Clothing.class, clothingId)).thenReturn(clothing);

        Clothing result = clothingDao.getClothing(clothingId);

        verify(dynamoDBMapper).load(Clothing.class, clothingId);
        assertEquals(clothing, result);
    }

    @Test
    public void getClothingItem_withNonExistentClothingId_throwsClothingNotFoundException() {
        String badClothingId = "nuh uh";

        when(dynamoDBMapper.load(Clothing.class, badClothingId)).thenReturn(null);

        assertThrows(ClothingNotFoundException.class, () -> clothingDao.getClothing(badClothingId));
    }

    @Test
    public void getAllClothingForUser_withCustomerId_returnsListOfClothing() {
        //GIVEN
        String id = "id";
        when(dynamoDBMapper.scan(eq(Clothing.class), any(DynamoDBScanExpression.class))).thenReturn(scanList);

        //WHEN
        List<Clothing> results = clothingDao.getAllClothingForUser(id);

        //THEN
        assertNotNull(results);
        verify(dynamoDBMapper).scan(eq(Clothing.class), scanExpressionArgumentCaptor.capture());
        assertTrue(!results.isEmpty());
    }

    @Test
    public void getAllClothingForUser_withNullId_ThrowsUserNotFoundException() {
        //GIVEN
        String id = null;

        when(dynamoDBMapper.scan(eq(Clothing.class), any(DynamoDBScanExpression.class))).thenReturn(scanList);

        //WHEN+THEN
        assertThrows(UserNotFoundException.class, () -> clothingDao.getAllClothingForUser(id));
    }

}