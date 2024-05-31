package com.nashss.se.virtualcloset.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.virtualcloset.exceptions.ClothingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ClothingDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    private ClothingDao clothingDao;

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

}