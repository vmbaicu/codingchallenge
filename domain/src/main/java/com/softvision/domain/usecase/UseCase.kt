/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.domain.usecase

/**
 * UseCases are *specific* business flows that may or may not receive parameters and output the desired result.
 *
 * There are 2 predefined types of usecases:
 * 1. Synchronous - synchronous execution of the business logic
 * 2. Async - async execution to be used with coroutines
 *
 * In order to enforce a good design usecases are not tightly coupled with interactors in any way but given
 * the current architecture the required business flows regardless of their complexity should be composed in
 * interactors using one or multiple usecases in order to achieve the desired result.
 */

/**
 * Base use case interface defining consumer params and a generic producer type.
 */
internal interface UseCase<in Params, out T>

abstract class SynchronousUseCase<in Params, out T> : UseCase<Params, T> {

    abstract fun execute(params: Params): T

    operator fun invoke(params: Params): T {
        return execute(params)
    }
}

abstract class AsyncUseCase<in Params, out T> : UseCase<Params, T> {

    abstract suspend fun execute(params: Params): T

    suspend operator fun invoke(params: Params): T {
        return execute(params)
    }
}
