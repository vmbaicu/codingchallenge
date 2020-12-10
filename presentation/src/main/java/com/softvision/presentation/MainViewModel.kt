/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */

package com.softvision.presentation

import com.softvision.architecture.ReactiveViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel
@ExperimentalCoroutinesApi
@Inject constructor(
    override val reducer: Main.Reducer,
    private val mainInteractor: MainInteractor
) : ReactiveViewModel<Main.State, Main.Intent, Main.Change>(
    Main.State.INITIAL
) {

    @ExperimentalCoroutinesApi
    override fun process(intent: Main.Intent, state: Main.State): Flow<Main.Change> =
        when (intent) {
            is Main.Intent.LoadData -> mainInteractor(intent)
        }
}
