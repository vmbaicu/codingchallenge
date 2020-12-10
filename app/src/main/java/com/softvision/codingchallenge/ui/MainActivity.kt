package com.softvision.codingchallenge.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.softvision.architecture.ReactiveView
import com.softvision.codingchallenge.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        (supportFragmentManager.fragments.first() as? NavHostFragment)?.childFragmentManager?.fragments?.get(
            0
        ).let { fragment ->
            if (fragment is ReactiveView<*, *>) {
                val isBackPressedConsumed = fragment.onBackPressed()
                if (!isBackPressedConsumed) {
                    super.onBackPressed()
                }
            } else {
                super.onBackPressed()
            }
        }
    }
}
