package miguel.rossi.beercatalog.randombeer.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Suppress("unused")
@Module
class RandomBeerNetworkModule {

    @Provides
    @Singleton
    fun provideRandomBeerService(httpClient: Retrofit): RandomBeerService =
        httpClient.create(RandomBeerService::class.java)
}
