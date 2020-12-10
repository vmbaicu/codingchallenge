/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.codingchallenge.injection.modules

import com.softvision.codingchallenge.ui.MainActivity
import com.softvision.codingchallenge.ui.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module that provides all dependencies from the UI package/layer and injects all activities/fragments.
 */
@Module
@Suppress("TooManyFunctions")
abstract class UiModule {
    // Activities
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    // Fragments
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment
}
