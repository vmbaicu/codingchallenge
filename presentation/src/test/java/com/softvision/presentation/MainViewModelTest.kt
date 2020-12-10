package com.softvision.presentation

import androidx.lifecycle.Observer
import com.softvision.architecture.ChannelIntentProducer
import com.softvision.architecture.IntentProducer
import com.softvision.presentation.utils.ThreadingExtension
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(ThreadingExtension::class)
class MainViewModelTest :
    IntentProducer<Main.Intent> by ChannelIntentProducer() {

    @MockK(relaxed = true)
    private lateinit var mainReducer: Main.Reducer

    @MockK(relaxed = true)
    private lateinit var mainInteractor: MainInteractor

    @MockK(relaxed = true)
    private lateinit var observer: Observer<Main.State>

    private lateinit var mainViewModel: MainViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        mainViewModel = MainViewModel(
            mainReducer,
            mainInteractor
        )

        mainViewModel.bindTo(this)
        mainViewModel.state.observeForever(observer)
    }

    @AfterEach
    fun tearDown() {
        mainViewModel.state.removeObserver(observer)
    }

    @Test
    fun `given load data, when the load is successful, then correct information is rendered`() =
        runBlockingTest {
            // given
            val state = Main.State.INITIAL.copy(csvText = "")
            val change = Main.Change.ShowData("")

            every { mainInteractor(any()) } returns flowOf(change)
            every { mainReducer.invoke(any(), change) } returns state

            // when
            produce(LOAD_DATA_INTENT)

            // then
            verify {
                observer.onChanged(state)
            }
        }

    companion object {
        private val LOAD_DATA_INTENT = Main.Intent.LoadData
    }
}
