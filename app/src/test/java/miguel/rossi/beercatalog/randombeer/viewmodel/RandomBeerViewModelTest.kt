package miguel.rossi.beercatalog.randombeer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import miguel.rossi.beercatalog.randombeer.domain.RandomBeerModel
import miguel.rossi.beercatalog.randombeer.repository.FailedResponse
import miguel.rossi.beercatalog.randombeer.repository.Repository
import miguel.rossi.beercatalog.randombeer.repository.SuccessfulResponse
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RandomBeerViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: RandomBeerViewModel

    private val repository: Repository = mockk(relaxed = true)

    private val state: Observer<RandomBeerState> = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()

        viewModel.state.removeObserver(state)
    }

    @Test
    fun `When starts, then fetch a beer`() {
        // Given
        coEvery { repository.fetchRandomBeer() } returns FailedResponse("")

        // When
        viewModel = RandomBeerViewModel(repository)

        // Then
        coVerify { repository.fetchRandomBeer() }
    }

    @Test
    fun `When requestedFreshBeer called, then fetch a beer`() {
        // Given
        coEvery { repository.fetchRandomBeer() } returns FailedResponse("")

        viewModel = RandomBeerViewModel(repository)

        // When
        viewModel.requestedFreshBeer()

        // Then
        coVerify { repository.fetchRandomBeer() }
    }

    @Test
    fun `When fetching beer, then change state to loading`() {
        // Given
        coEvery { repository.fetchRandomBeer() } returns FailedResponse("")

        viewModel = RandomBeerViewModel(repository)
        viewModel.state.observeForever(state)

        // When
        viewModel.requestedFreshBeer()

        // Then
        verify { state.onChanged(RandomBeerState.Loading) }
    }

    @Test
    fun `When fetching beer and SuccessfulResponse, then change state to Beer`() {
        // Given
        val beer = getBeerModel()
        coEvery { repository.fetchRandomBeer() } returns SuccessfulResponse(beer)

        val slot = slot<RandomBeerState>()
        every { state.onChanged(capture(slot)) } returns Unit

        viewModel = RandomBeerViewModel(repository)
        viewModel.state.observeForever(state)

        // When
        viewModel.requestedFreshBeer()

        // Then
        val capturedRandomBeerState = slot.captured
        assertThat(capturedRandomBeerState, instanceOf(RandomBeerState.Beer::class.java))

        capturedRandomBeerState as RandomBeerState.Beer
        assertThat(capturedRandomBeerState.beer, equalTo(beer))
    }

    @Test
    fun `When fetching beer and FailedResponse, then change state to Error`() {
        // Given
        val errorMessage = "error message"
        coEvery { repository.fetchRandomBeer() } returns FailedResponse(errorMessage)

        val slot = slot<RandomBeerState>()
        every { state.onChanged(capture(slot)) } returns Unit

        viewModel = RandomBeerViewModel(repository)
        viewModel.state.observeForever(state)

        // When
        viewModel.requestedFreshBeer()

        // Then
        val capturedRandomBeerState = slot.captured
        assertThat(capturedRandomBeerState, instanceOf(RandomBeerState.Error::class.java))

        capturedRandomBeerState as RandomBeerState.Error
        assertThat(capturedRandomBeerState.message, equalTo(errorMessage))
    }

    // region HELPERS
    private fun getBeerModel() = RandomBeerModel(
        name = "name",
        abv = 0.5,
        description = "description",
        brewersTips = "brewersTips"
    )
    // endregion HELPERS
}
