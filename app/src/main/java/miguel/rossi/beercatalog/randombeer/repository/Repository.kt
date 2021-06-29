package miguel.rossi.beercatalog.randombeer.repository

interface Repository {
    suspend fun fetchRandomBeer(): RandomBeerResponse
}
