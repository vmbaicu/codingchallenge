/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */

package com.softvision.codingchallenge.injection.components

import android.app.Application
import com.softvision.codingchallenge.App
import com.softvision.codingchallenge.injection.modules.DataModule
import com.softvision.codingchallenge.injection.modules.DomainModule
import com.softvision.codingchallenge.injection.modules.PresentationModule
import com.softvision.codingchallenge.injection.modules.AppModule
import com.softvision.codingchallenge.injection.modules.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PresentationModule::class,
        DataModule::class,
        DomainModule::class,
        AppModule::class,
        UiModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun build(): AppComponent
    }
}
