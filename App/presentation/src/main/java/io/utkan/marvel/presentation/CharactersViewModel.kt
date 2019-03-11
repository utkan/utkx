package io.utkan.marvel.presentation

import android.app.Application
import android.content.Context
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
    val app: Application
) : AndroidViewModel(app) {
    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private var cancel: () -> Unit by Delegates.notNull()
    private var isFirstTimeUser: Boolean = true

    init {
        viewState.postValue(ViewState.Loading(false))

        isFirstTime {
            isFirstTimeUser = false
        }

        cancel = getCharacters.execute(
            isFirstTimeUser,
            { throwable ->
                viewState.postValue(ViewState.Error(throwable.localizedMessage, false))
            }, { results ->
                viewState.postValue(
                    ViewState.CharacterList(
                        results.map {
                            it.toViewModel {
                                noMoreFirstTimeUser {
                                    onGotoCategories()
                                }
                            }
                        },
                        isFirstTimeUser.not()
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
            isFirstTimeUser,
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
        return isStateDetail {
            onImageClosed()
        }
    }

    private inline fun isStateDetail(func: () -> Unit): Boolean {
        if (viewState.value is ViewState.CharacterDetail) {
            func()
            return true
        }
        return false
    }

    private fun showDetail(detailUrl: String) {
        viewState.value = ViewState.CharacterDetail(detailUrl, false)
    }

    private inline fun isFirstTime(func: () -> Unit) {
        val sharedPref =
            app.applicationContext.getSharedPreferences(
                SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        if (sharedPref.getBoolean(FIRST_TIME_KEY, true).not()) {
            func()
        }
    }

    private inline fun noMoreFirstTimeUser(func: () -> Unit) {
        val sharedPref =
            app.applicationContext.getSharedPreferences(
                SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        sharedPref.edit().putBoolean(FIRST_TIME_KEY, false).apply()
        func()
    }

    companion object {
        const val SHARED_PREFERENCES_NAME = "MARVEL"
        const val FIRST_TIME_KEY = "FIRST_TIME"
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
