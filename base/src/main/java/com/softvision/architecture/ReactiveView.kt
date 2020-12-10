/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.architecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.softvision.base.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_reactive_view.view.layout_stub
import kotlinx.coroutines.Job
import javax.inject.Inject

private interface CanInjectViewModel {
    var viewModelFactory: ViewModelProvider.Factory
}

abstract class ReactiveView<S : UiState, I : UiIntent> :
    DaggerFragment(), CanInjectViewModel, StateConsumer<S>, IntentProducer<I> by ChannelIntentProducer() {
    @get:LayoutRes
    abstract val contentLayoutResource: Int

    /**
     * Job used to cancel the channel sending keyboard events.
     */
    open var keyboardJob: Job? = null

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_reactive_view, container, false).apply {
            layout_stub.layoutResource = contentLayoutResource
            layout_stub.inflate()
        }

    open fun onBackPressed(): Boolean {
        return false
    }
}
