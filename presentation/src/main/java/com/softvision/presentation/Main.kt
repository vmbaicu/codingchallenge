/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.presentation

import android.util.Log
import com.softvision.architecture.UiChange
import com.softvision.architecture.UiIntent
import com.softvision.architecture.UiReducer
import com.softvision.architecture.UiState
import javax.inject.Inject

interface Main {
    data class State(
        val csvText: String? = null,
        val loading: Boolean = false,
        val errorMsg: String? = null
    ) : UiState {
        companion object {
            val INITIAL = State()
        }
    }

    sealed class Intent : UiIntent {
        object LoadData : Intent()
    }

    sealed class Change : UiChange {
        object LoadInProgress : Change()
        data class ShowData(val csvText: String) : Change()
        data class Error(val errorMsg: String?) : Change()
    }

    class Reducer @Inject constructor() :
        UiReducer<State, Change> {
        override fun invoke(state: State, change: Change): State = when (change) {
            is Change.LoadInProgress -> state.copy(
                loading = true
            )
            is Change.ShowData -> state.copy(
                loading = false,
                csvText = state.csvText
            )
            is Change.Error -> state.copy(
                loading = false,
                errorMsg = state.errorMsg
            )
        }
    }
}
