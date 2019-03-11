package io.utkan.marvelui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import dagger.android.support.AndroidSupportInjection
import io.utkan.marvel.presentation.CharactersViewModel
import io.utkan.marvelui.R
import io.utkan.marvelui.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject
import kotlin.properties.Delegates


class CharactersActivityFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var charactersViewModel: CharactersViewModel

    private var charactersAdapter by Delegates.notNull<CharactersAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersAdapter = CharactersAdapter()
        charactersAdapter.characterList = mutableListOf()
        list.adapter = charactersAdapter
        val lookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position and 0x1) {
                    1 -> 3
                    else -> 1
                }
            }
        }
//        (list.layoutManager as GridLayoutManager).spanSizeLookup = lookup
        go_to_categories.setOnClickListener {
            charactersViewModel.onGotoCategories()
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        charactersViewModel = ViewModelProviders.of(activity!!, viewModelFactory)
            .get(CharactersViewModel::class.java)

        charactersViewModel.viewState.observe(
            this, Observer {
                when (it) {
                    is CharactersViewModel.ViewState.Loading -> {
                        changeGoToCategoriesButtonVisibility(it.categoriesEnabled)
                    }
                    is CharactersViewModel.ViewState.Error -> {
                        changeGoToCategoriesButtonVisibility(it.categoriesEnabled)
                    }
                    is CharactersViewModel.ViewState.CharacterList -> {
                        changeGoToCategoriesButtonVisibility(it.categoriesEnabled)
                        charactersAdapter.updateList(it.characters)
                    }
                    is CharactersViewModel.ViewState.CharacterDetail -> {
                        it.url?.let { url ->
                            changeGoToCategoriesButtonVisibility(it.categoriesEnabled)
                            showImageDetail(url)
                        }
                    }
                    is CharactersViewModel.ViewState.CloseDetail -> {
                        changeGoToCategoriesButtonVisibility(it.categoriesEnabled)
                        character_detail.visibility = View.GONE
                    }
                }
            }
        )
    }

    private fun showImageDetail(url: String) {
        character_detail.setOnClickListener {
            charactersViewModel.onImageClosed()
        }
        character_detail.visibility = View.VISIBLE
        Glide.with(this).load(url).into(character_detail)
    }

    private fun changeGoToCategoriesButtonVisibility(isEnabled: Boolean) {
        go_to_categories.visibility = if (isEnabled) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
