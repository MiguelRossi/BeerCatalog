package miguel.rossi.beercatalog.randombeer.repository

import miguel.rossi.beercatalog.randombeer.network.RandomBeerService
import miguel.rossi.beercatalog.randombeer.network.toBeerModel
import javax.inject.Inject

private const val DEFAULT_ERROR_MESSAGE = "Oops! The beers are hot!"

class RandomBeerRepository @Inject constructor(
    private val beerService: RandomBeerService
) : Repository {

    override suspend fun fetchRandomBeer(): RandomBeerResponse {
        return try {
            val apiResponse = beerService.getRandomBeer()
            SuccessfulResponse(apiResponse.toBeerModel())
        } catch (e: Exception) {
            e.printStackTrace()
            FailedResponse(e.message ?: DEFAULT_ERROR_MESSAGE)
        }
    }
}
