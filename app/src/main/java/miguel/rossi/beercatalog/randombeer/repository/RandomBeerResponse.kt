package miguel.rossi.beercatalog.randombeer.repository

import miguel.rossi.beercatalog.randombeer.domain.RandomBeerModel

sealed class RandomBeerResponse

data class SuccessfulResponse(val randomBeerModel: RandomBeerModel) : RandomBeerResponse()

data class FailedResponse(val errorMessage: String) : RandomBeerResponse()
