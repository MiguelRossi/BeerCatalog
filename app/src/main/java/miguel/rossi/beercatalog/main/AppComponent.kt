package miguel.rossi.beercatalog.main

import dagger.Component
import miguel.rossi.beercatalog.main.network.HttpClientModule
import miguel.rossi.beercatalog.main.network.ParserModule
import miguel.rossi.beercatalog.randombeer.network.RandomBeerNetworkModule
import miguel.rossi.beercatalog.randombeer.repository.RandomBeerRepositoryModule
import miguel.rossi.beercatalog.randombeer.view.RandomBeerFragment
import miguel.rossi.beercatalog.randombeer.viewmodel.RandomBeerViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HttpClientModule::class,
        ParserModule::class,
        RandomBeerNetworkModule::class,
        RandomBeerRepositoryModule::class,
        RandomBeerViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: RandomBeerFragment)
}
