/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.codingchallenge.injection.modules

import com.softvision.data.repository.FileRepositoryImpl
import com.softvision.domain.repository.FileRepository
import dagger.Binds
import dagger.Module

/**
 * Module that provides all dependencies from the data layer.
 */
@Module
abstract class DataModule {
    @Binds
    abstract fun bindFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository
}
