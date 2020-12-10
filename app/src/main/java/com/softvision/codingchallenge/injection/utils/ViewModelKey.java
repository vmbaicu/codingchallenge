/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */

package com.softvision.codingchallenge.injection.utils;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Annotation used for Dagger multibinding. It links specific ViewModels so when we use the
 * ViewModelProvider Factory, the correct instance is provided.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
