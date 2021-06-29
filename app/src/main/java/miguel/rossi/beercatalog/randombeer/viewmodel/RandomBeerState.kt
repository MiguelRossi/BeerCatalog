package miguel.rossi.beercatalog.randombeer.viewmodel

import miguel.rossi.beercatalog.randombeer.domain.RandomBeerModel

sealed class RandomBeerState {
    object Loading : RandomBeerState()
    class Error(val message: String) : RandomBeerState()
    class Beer(val beer: RandomBeerModel) : RandomBeerState()
}
