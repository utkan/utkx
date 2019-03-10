package io.utkan.marvel.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.utkan.marvel.domain.interactor.GetCharacters
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    getCharacters: GetCharacters
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    private var state: ViewState?
        get() = _viewState.value
        set(value) {
            if (_viewState.value != value) {
                _viewState.value = value
            }
        }
    val viewState: LiveData<ViewState> = _viewState

    init {
        state = ViewState.Loading
        getCharacters.execute(
            { throwable ->
                _viewState.postValue(ViewState.Error(throwable.localizedMessage))
            }, { results ->
                //                state = ViewState.CharacterList(results)
                _viewState.postValue(ViewState.CharacterList(results.map {
                    CharacterViewModel(
                        id = it.id,
                        name = it.name,
                        thumbnail = it.thumbnail
                    )
                }))
            }
        )
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val message: String?) : ViewState()
        data class CharacterList(val characters: List<CharacterViewModel>) : ViewState()
    }
}
