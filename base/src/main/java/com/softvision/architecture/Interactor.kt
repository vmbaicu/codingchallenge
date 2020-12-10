/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.architecture

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Entry point into the Domain / Business logic, an Interactor is responsible for transforming
 * the user's Intents into business flows that lead to triggering Changes to the View State
 * (zero or more changes).
 *
 * There are three predefined types of Interactors:
 * 1. Single: a business flow that implies an action is one shot - the user Intent triggers a
 * business flow that leads to an update of the screen.
 * 2. Flow: a business flow that implies an action is on going - the user Intent triggers a
 * business flow that will lead to multiple updates of the screen.
 * 3. Side Effect: a business flow that implies no UI updates - the user Intent triggers a
 * business flow that will affect the rest of the system, but will not have an feedback to the user.
 *
 * If needed, custom Interactors can be created - e.g. a Flow from the database can be returned
 * by a custom Interactor that runs the query, maps the data into Changes and returns the Flow
 * in the invoke operator.
 *
 * Interactors are linked to generic Intents and Changes, specifically so that they can be
 * customized for generic Intents (all of the Intents of a screen) or for specific Intents (only
 * a subclass of a screen's Intent).
 */
private interface Interactor<in I : UiIntent, out C : UiChange> {
    /**
     * Interprets the intent, triggering a business flow that can run asynchronously and post
     * updates to the UI in the Flow that is returned.
     */
    operator fun invoke(intent: I): Flow<C>
}

/**
 * An Interactor that transforms an Intent to an update of the screen.
 */
abstract class SingleInteractor<in I : UiIntent, out C : UiChange> :
    Interactor<I, C> {
    override fun invoke(intent: I): Flow<C> {
        return flow { emit(execute(intent)) }
    }

    abstract suspend fun execute(intent: I): C
}

/**
 * An Interactor that transforms an Intent into possibly multiple updates of the screen. Need to
 * subclass this and implement the execute method. Whenever an update needs to be posted in the
 * execute method, call the emit(Change) method.
 */
abstract class FlowInteractor<in I : UiIntent, C : UiChange> :
    Interactor<I, C> {

    @ExperimentalCoroutinesApi
    override fun invoke(intent: I): Flow<C> = asyncFlow {
        execute(intent)
    }

    abstract suspend fun FlowCollector<C>.execute(intent: I)
}

/**
 * An Interactor that transforms an Intent to a Side-Effect - does not trigger any changes in the
 * ViewState, but can affect other systems / components by overwriting the execute method.
 */
abstract class SideEffectInteractor<in I : UiIntent, out C : UiChange> :
    Interactor<I, C> {
    override fun invoke(intent: I): Flow<C> = flow {
        execute(intent)
    }

    abstract suspend fun execute(intent: I)
}

@ExperimentalCoroutinesApi
fun <T> asyncFlow(block: suspend FlowCollector<T>.() -> Unit): Flow<T> = channelFlow {
    coroutineScope {
        launch {
            flow(block).collect { send(it) }
            close()
        }
    }
    awaitClose()
}
