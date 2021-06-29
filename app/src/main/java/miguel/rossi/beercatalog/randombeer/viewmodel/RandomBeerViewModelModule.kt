package miguel.rossi.beercatalog.randombeer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import miguel.rossi.beercatalog.main.viewmodel.ViewModelFactory
import miguel.rossi.beercatalog.main.viewmodel.ViewModelKey

@Suppress("unused")
@Module
abstract class RandomBeerViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RandomBeerViewModel::class)
    abstract fun bindRandomBeerViewModel(userViewModel: RandomBeerViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
