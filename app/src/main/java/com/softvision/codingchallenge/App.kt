/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.codingchallenge

import com.softvision.codingchallenge.injection.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .create(this)
            .build()
    }
}
