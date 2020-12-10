/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.codingchallenge.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import com.softvision.architecture.ReactiveView
import com.softvision.architecture.bind
import com.softvision.architecture.intent
import com.softvision.codingchallenge.R
import com.softvision.presentation.Main
import com.softvision.presentation.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainFragment : ReactiveView<Main.State, Main.Intent>() {
    override val contentLayoutResource = R.layout.fragment_main

    override fun render(state: Main.State) {
        if (state.loading) {
            progress.visibility = View.VISIBLE
        } else {
            progress.visibility = View.GONE
        }

        state.errorMsg?.let {
            makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        state.csvText?.let {
            csv_text.text = it
        }
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind(MainViewModel::class)
        intent(Main.Intent.LoadData)
    }
}
