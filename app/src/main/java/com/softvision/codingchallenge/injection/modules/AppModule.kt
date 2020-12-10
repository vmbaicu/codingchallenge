/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */

package com.softvision.codingchallenge.injection.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideAppContext(app: Application): Context = app.applicationContext
}
