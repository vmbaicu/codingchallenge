package com.softvision.presentation

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SplashReducerTest {

    private lateinit var mainReducer: Main.Reducer

    @BeforeEach
    fun setUp() {
        mainReducer = Main.Reducer()
    }

    @Test
    fun `given load data, when main reducer received the LoadInProgress change, then the correct state should be generated`() {
        // given
        val loadingFalse = true

        // when
        val state = mainReducer(Main.State.INITIAL, Main.Change.LoadInProgress)

        // then
        assertThat(state).isEqualTo(Main.State.INITIAL.copy(loading = loadingFalse))
    }

    @Test
    fun `given load data, when main reducer received the ShowData change, then the correct state should be generated`() {
        // given
        val data = ""

        // when
        val state = mainReducer(Main.State.INITIAL, Main.Change.ShowData(data))

        // then
        assertThat(state).isEqualTo(Main.State.INITIAL.copy(csvText = data))
    }

    @Test
    fun `given load data, when main reducer received the Error change, then the correct state should be generated`() {
        // given
        val error = ""

        // when
        val state = mainReducer(Main.State.INITIAL, Main.Change.Error(error))

        // then
        assertThat(state).isEqualTo(Main.State.INITIAL.copy(errorMsg = error))
    }
}
