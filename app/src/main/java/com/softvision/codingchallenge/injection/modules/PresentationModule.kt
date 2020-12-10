/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.codingchallenge.injection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softvision.codingchallenge.injection.utils.ViewModelFactory
import com.softvision.codingchallenge.injection.utils.ViewModelKey
import com.softvision.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Module that provides all dependencies from the presentation package/layer.
 */
@ExperimentalCoroutinesApi
@Module
@Suppress("TooManyFunctions")
abstract class PresentationModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}
