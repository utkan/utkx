package io.utkan.marvel.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.utkan.marvel.domain.interactor.CharacterViewTracker
import io.utkan.marvel.domain.interactor.GetCharacters
import io.utkan.marvel.domain.model.CharacterDomain
import javax.inject.Inject
import kotlin.properties.Delegates

class CharactersViewModel @Inject constructor(
    private val getCharacters: GetCharacters,
    private val tracker: CharacterViewTracker,
    application: Application
) : AndroidViewModel(application) {

    private val _viewState = MutableLiveData<ViewState>()
    private var state: ViewState?
        get() = _viewState.value
        set(value) {
            if (_viewState.value != value) {
                _viewState.value = value
            }
        }
    private var cancel: () -> Unit by Delegates.notNull()

    val viewState: LiveData<ViewState> = _viewState

    init {
        state = ViewState.Loading(false)
        getCharacters.execute(
            { throwable ->
                _viewState.postValue(ViewState.Error(throwable.localizedMessage, false))
            }, { results ->
                _viewState.postValue(
                    ViewState.CharacterList(
                        results.map { it.toViewModel() },
                        true
                    )
                )
            }
        )
    }

    override fun onCleared() {
        cancel()
        super.onCleared()
    }

    fun onGotoCategories() {
        cancel = getCharacters.execute(
            12,
            { throwable ->
                _viewState.postValue(ViewState.Error(throwable.localizedMessage, false))
            }, { results ->
                _viewState.postValue(ViewState.CharacterList(results.map {
                    it.toViewModel { detailUrl ->
                        tracker.track(it.id)
                        showDetail(detailUrl)
                    }
                }, false))
            }
        )
    }

    fun onImageClosed() {
        state = ViewState.CloseDetail(false)
    }

    private fun showDetail(detailUrl: String) {
        state = ViewState.CharacterDetail(detailUrl, false)
    }

    private fun CharacterDomain.toViewModel(action: ((String) -> Unit)? = null): CharacterViewModel {
        return CharacterViewModel(
            id = id,
            name = name,
            thumbnail = thumbnail,
            action = action,
            detailImageUrl = detailImageUrl,
            viewCount = viewCount
        )
    }

    sealed class ViewState {
        data class Loading(val categoriesEnabled: Boolean) : ViewState()
        data class Error(val message: String?, val categoriesEnabled: Boolean) : ViewState()
        data class CharacterDetail(val url: String?, val categoriesEnabled: Boolean) : ViewState()
        data class CloseDetail(val categoriesEnabled: Boolean) : ViewState()
        data class CharacterList(
            val characters: List<CharacterViewModel>,
            val categoriesEnabled: Boolean
        ) : ViewState()
    }
}
