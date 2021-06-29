package miguel.rossi.beercatalog.randombeer.network

import retrofit2.http.GET

interface RandomBeerService {

    @GET("random")
    suspend fun getRandomBeer(): List<NetworkResponse>
}
