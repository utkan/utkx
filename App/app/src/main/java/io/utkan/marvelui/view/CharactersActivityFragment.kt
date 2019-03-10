package io.utkan.marvelui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
    lateinit var charactersViewModel: CharactersViewModel

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
        charactersAdapter.characterList = emptyList()
        list.adapter = charactersAdapter
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        charactersViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CharactersViewModel::class.java)

        charactersViewModel.viewState.observe(
            this, Observer {
                when (it) {
                    is CharactersViewModel.ViewState.CharacterList -> {
                        charactersAdapter.characterList =it.characters
                        charactersAdapter.notifyDataSetChanged()

                        Log.i("aaa", it.characters.get(0).name)
                    }
                }
            }
        )
    }
}
