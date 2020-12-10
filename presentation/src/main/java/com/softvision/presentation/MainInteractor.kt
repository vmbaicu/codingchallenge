/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.presentation

import com.softvision.architecture.FlowInteractor
import com.softvision.domain.usecase.GetCSVDataUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainInteractor @Inject constructor(
    private val getCSVDataUseCase: GetCSVDataUseCase
) :
    FlowInteractor<Main.Intent.LoadData, Main.Change>() {

    override suspend fun FlowCollector<Main.Change>.execute(intent: Main.Intent.LoadData) {
        emitAll(
            getData()
                .onStart {
                    emit(Main.Change.LoadInProgress)
                }
                .catch { error ->
                    error.message?.let {
                        emit(Main.Change.Error(it))
                    }
                }
        )
    }

    @Throws(Exception::class)
    private suspend fun getData() = flow {
        val csvData = getCSVDataUseCase(null)
        csvData?.let {
            emit(
                Main.Change.ShowData(it)
            )
        }
    }
}
