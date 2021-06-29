package miguel.rossi.beercatalog.randombeer.network

import miguel.rossi.beercatalog.randombeer.domain.RandomBeerModel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

class NetworkResponseTest {

    @Test
    fun `When fetching an empty list, then return default values`() {
        // Given
        val emptyNetworkResponse = emptyList<NetworkResponse>()
        val emptyNetworkBeer = emptyNetworkBeer()

        // When
        val beerModel = emptyNetworkResponse.toBeerModel()

        // Then
        assertThat(emptyNetworkResponse.size, equalTo(0))
        assertThat(beerModel, equalTo(emptyNetworkBeer))
    }

    @Test
    fun `When fetching an item, then convert toBeerModel`() {
        // Given

        val networkResponse = getResponse()

        // When
        val beerModel = networkResponse.toBeerModel()

        // Then
        assertThat(beerModel.name, equalTo(networkResponse[0].name))
        assertThat(beerModel.abv, equalTo(networkResponse[0].abv))
        assertThat(beerModel.description, equalTo(networkResponse[0].description))
        assertThat(beerModel.brewersTips, equalTo(networkResponse[0].brewersTips))
    }

    @Test
    fun `When fetching an item with empty values, then convert toBeerModel with defaults`() {
        // Given
        val networkResponse = getResponseWithEmptyValues()

        // When
        val beerModel = networkResponse.toBeerModel()

        // Then
        assertThat(beerModel.name, equalTo(""))
        assertThat(beerModel.abv, equalTo(0.0))
        assertThat(beerModel.description, equalTo(""))
        assertThat(beerModel.brewersTips, equalTo(""))
    }

    // region HELPERS
    private fun emptyNetworkBeer() = RandomBeerModel(
        name = "",
        abv = 0.0,
        description = "",
        brewersTips = ""
    )

    private fun getResponse() = listOf(
        NetworkResponse(
            name = "name",
            abv = 0.5,
            description = "description",
            brewersTips = "brewersTips"
        )
    )

    private fun getResponseWithEmptyValues() = listOf(
        NetworkResponse(
            name = null,
            abv = null,
            description = null,
            brewersTips = null
        )
    )
    // endregion HELPERS
}
