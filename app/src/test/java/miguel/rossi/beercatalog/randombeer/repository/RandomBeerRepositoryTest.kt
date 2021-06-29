package miguel.rossi.beercatalog.randombeer.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import miguel.rossi.beercatalog.randombeer.domain.RandomBeerModel
import miguel.rossi.beercatalog.randombeer.network.NetworkResponse
import miguel.rossi.beercatalog.randombeer.network.RandomBeerService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Before
import org.junit.Test

class RandomBeerRepositoryTest {

    private lateinit var repository: RandomBeerRepository

    private val beerService: RandomBeerService = mockk(relaxed = true)

    @Before
    fun setUp() {
        repository = RandomBeerRepository(beerService)
    }

    @Test
    fun `When retrieve a successful beer response, then return SuccessfulResponse`() = runBlocking {
        // Given
        coEvery { beerService.getRandomBeer() } returns getResponse()

        // When
        val beerModel = repository.fetchRandomBeer()

        // Then
        assertThat(beerModel, instanceOf(SuccessfulResponse::class.java))

        beerModel as SuccessfulResponse
        assertThat(beerModel.randomBeerModel, equalTo(getBeerModel()))
    }

    @Test
    fun `When retrieve beer fails, then return FailedResponse`() = runBlocking {
        // Given
        val errorMessage = "No network"
        coEvery { beerService.getRandomBeer() } throws Exception(errorMessage)

        // When
        val beerModel = repository.fetchRandomBeer()

        // Then
        assertThat(beerModel, instanceOf(FailedResponse::class.java))

        beerModel as FailedResponse
        assertThat(beerModel.errorMessage, equalTo(errorMessage))
    }

    // region HELPERS
    private fun getResponse() = listOf(
        NetworkResponse(
            name = "name",
            abv = 0.5,
            description = "description",
            brewersTips = "brewersTips"
        )
    )

    private fun getBeerModel() = RandomBeerModel(
        getResponse()[0].name!!,
        getResponse()[0].abv!!,
        getResponse()[0].description!!,
        getResponse()[0].brewersTips!!
    )
    // endregion HELPERS
}
