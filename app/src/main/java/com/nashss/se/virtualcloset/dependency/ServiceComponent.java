package com.nashss.se.virtualcloset.dependency;

import com.nashss.se.virtualcloset.activity.AddClothingToOutfitActivity;
import com.nashss.se.virtualcloset.activity.CreateClothingActivity;
import com.nashss.se.virtualcloset.activity.CreateOutfitActivity;
import com.nashss.se.virtualcloset.activity.DeleteClothingFromOutfitActivity;
import com.nashss.se.virtualcloset.activity.DeleteOutfitActivity;
import com.nashss.se.virtualcloset.activity.GetClothingFromOutfitActivity;
import com.nashss.se.virtualcloset.activity.GetOutfitActivity;
import com.nashss.se.virtualcloset.activity.GetSortedClothingActivity;
import com.nashss.se.virtualcloset.activity.GetSortedOutfitActivity;
import com.nashss.se.virtualcloset.activity.GetUserOutfitsActivity;
import com.nashss.se.virtualcloset.activity.UpdateOutfitActivity;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return GetOutfitActivity
     */
    GetOutfitActivity provideGetOutfitActivity();

    /**
     * Creates the relevant activity.
     * @return CreateOutfitActivity
     */
    CreateOutfitActivity provideCreateOutfitActivity();

    /**
     * Creates the relevant activity.
     * @return UpdateOutfitActivity
     */
    UpdateOutfitActivity provideUpdateOutfitActivity();

    /**
     * Creates the relevant activity.
     * @return AddClothingToOutfitActivity
     */
    AddClothingToOutfitActivity provideAddClothingToOutfitActivity();

    /**
     * Creates the relevant activity.
     * @return GetClothingFromOutfitActivity
     */
    GetClothingFromOutfitActivity provideGetClothingFromOutfitActivity();

    /**
     * Creates the relevant activity.
     * @return GetUserOutfitsActivity
     */
    GetUserOutfitsActivity provideGetUserOutfitsActivity();

    /**
     * Creates the relevant activity.
     * @return DeleteClothingFromOutfitActivity
     */
    DeleteClothingFromOutfitActivity provideDeleteClothingFromOutfitActivity();

    /**
     * Creates the relevant activity.
     * @return DeleteOutfitActivity
     */
    DeleteOutfitActivity provideDeleteOutfitActivity();

    /**
     * Creates the relevant activity.
     * @return GetSortedOutfitActivity
     */
    GetSortedOutfitActivity provideGetSortedOutfitActivity();

    /**
     * Creates the relevant activity.
     * @return GetSortedClothingActivity
     */
    GetSortedClothingActivity provideGetSortedClothingActivity();

    /**
     * Creates the relevant activity.
     * @return CreateClothingActivity
     */
    CreateClothingActivity provideCreateClothingActivity();
}


