/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

/**
 * Base class for a ViewModel that implements the MVI pattern and uses Coroutines and Flow.
 */
abstract class ReactiveViewModel<S : UiState, I : UiIntent, C : UiChange>(
    private var currentState: S
) : ViewModel() {

    /**
     * Use the ViewModel coroutine scope so all active coroutines are cancelled when destroyed
     * but use a background thread to process changes in state
     */
    private val coroutineContext = viewModelScope.coroutineContext + Dispatchers.Main

    /**
     * Store the coroutine that collects the Intent Flow so we can cancel it when another attaches.
     * We only support one IntentDispatcher at the moment.
     */
    private var intentsJob: Job? = null

    /**
     * Channel & Flow used to buffer emitted changes
     */
    private val changes = Channel<C>()
    private val changesFlow = flow {
        for (change in changes) {
            emit(change)
        }
    }

    /**
     * Helper function for emitting changes.
     */
    internal suspend fun change(change: C) {
        changes.send(change)
    }

    @ExperimentalCoroutinesApi
    val state: LiveData<S> = liveData(coroutineContext) {
        changesFlow.scan(currentState) { accumulator, value ->
            reducer(accumulator, value).also { newState ->
                currentState = newState
            }
        }.collect {
            emit(it)
        }
    }

    /**
     * Function called when the View wants to attach to this ViewModel
     */
    @ExperimentalCoroutinesApi
    fun bindTo(producer: IntentProducer<I>) {
        intentsJob?.cancel()
        intentsJob = viewModelScope.launch {
            producer.intents.flatMapMerge {
                process(it, currentState)
            }.collect { change ->
                changes.send(change)
            }
        }
    }

    abstract val reducer: UiReducer<S, C>

    protected abstract fun process(intent: I, state: S): Flow<C>
}

@ExperimentalCoroutinesApi
fun <VM : ReactiveViewModel<S, I, C>, S : UiState, I : UiIntent, C : UiChange, V> V.bind(
    viewModelClass: KClass<VM>,
    shared: Boolean = false
) where V : ReactiveView<S, I> {
    val provider = if (shared) {
        ViewModelProvider(requireActivity(), viewModelFactory)
    } else {
        ViewModelProvider(this, viewModelFactory)
    }
    provider.get(viewModelClass.java).let { viewModel ->
        viewModel.bindTo(this)
        viewModel.state.observe(viewLifecycleOwner, Observer { state -> render(state) })
    }
}
