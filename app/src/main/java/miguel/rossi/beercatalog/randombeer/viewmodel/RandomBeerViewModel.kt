package miguel.rossi.beercatalog.randombeer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import miguel.rossi.beercatalog.randombeer.repository.FailedResponse
import miguel.rossi.beercatalog.randombeer.repository.Repository
import miguel.rossi.beercatalog.randombeer.repository.SuccessfulResponse
import javax.inject.Inject

class RandomBeerViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<RandomBeerState>()
    val state: LiveData<RandomBeerState> = _state

    init {
        fetchRandomBeer()
    }

    private fun fetchRandomBeer() {
        _state.value = RandomBeerState.Loading
        viewModelScope.launch {
            when (val response = repository.fetchRandomBeer()) {
                is SuccessfulResponse -> _state.postValue(RandomBeerState.Beer(response.randomBeerModel))
                is FailedResponse -> _state.postValue(RandomBeerState.Error(response.errorMessage))
            }
        }
    }

    fun requestedFreshBeer() {
        fetchRandomBeer()
    }
}
