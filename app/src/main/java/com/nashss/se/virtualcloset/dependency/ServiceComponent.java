package com.nashss.se.virtualcloset.dependency;

import com.nashss.se.virtualcloset.activity.GetOutfitActivity;

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
}


