/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.architecture

/**
 * StateRenderer needs to provide a way to consume (eg. display) the State. The View needs to
 * implement this.
 */
interface StateConsumer<S : UiState> {
    fun render(state: S)
}
