/*
 *
 *  * Copyright (c) Cognizant Softvision 2020.
 *  * All rights reserved.
 *
 */

package com.softvision.presentation.utils

import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible

/**
 * This is only used as an EXAMPLE, illustrating how to test private methods using reflection.
 * Please avoid using the this extension function, as the purpose is to test
 * the functionality and not the implementation.
 */
inline fun <reified T> T.callPrivateFun(name: String, vararg args: Any?): Any? =
    T::class
        .declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args)
