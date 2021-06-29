package miguel.rossi.beercatalog.randombeer.repository

import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class RandomBeerRepositoryModule {

    @Binds
    abstract fun bindsRepository(repository: RandomBeerRepository): Repository
}
