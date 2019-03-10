package io.utkan.marvel.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private var cancel: () -> Unit by Delegates.notNull()


    init {
        viewState.postValue(ViewState.Loading(false))
        cancel = getCharacters.execute(
            { throwable ->
                viewState.postValue(ViewState.Error(throwable.localizedMessage, false))
            }, { results ->
                viewState.postValue(
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
                viewState.postValue(ViewState.Error(throwable.localizedMessage, false))
            }, { results ->
                viewState.postValue(ViewState.CharacterList(results.map {
                    it.toViewModel { detailUrl ->
                        tracker.track(it.id)
                        showDetail(detailUrl)
                    }
                }, false))
            }
        )
    }

    fun onImageClosed() {
        viewState.postValue(ViewState.CloseDetail(false))
    }

    fun onBackPressed(): Boolean {
        return when (viewState.value) {
            is ViewState.CharacterDetail -> {
                onImageClosed()
                true
            }
            else -> false
        }
    }

    private fun showDetail(detailUrl: String) {
        viewState.value = ViewState.CharacterDetail(detailUrl, false)
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
