package com.nashss.se.virtualcloset.activity;

import com.nashss.se.virtualcloset.dynamodb.OutfitDao;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UpdateOutfitActivityTest {
    @Mock
    private OutfitDao outfitDao;

    private UpdateOutfitActivity updateOutfitActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        updateOutfitActivity
    }

}