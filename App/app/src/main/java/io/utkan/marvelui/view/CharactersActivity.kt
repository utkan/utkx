package io.utkan.marvelui.view

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import io.utkan.marvel.presentation.CharactersViewModel
import io.utkan.marvelui.R
import io.utkan.marvelui.ViewModelFactory
import kotlinx.android.synthetic.main.activity_characters.*
import javax.inject.Inject

class CharactersActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var charactersViewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)
        setSupportActionBar(toolbar)
        AndroidInjection.inject(this)

        charactersViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CharactersViewModel::class.java)

        val charactersFragment = supportFragmentManager.findFragmentById(R.id.fragment)
        addOnBackPressedCallback(charactersFragment as LifecycleOwner, object :
            OnBackPressedCallback {
            override fun handleOnBackPressed(): Boolean {
                return charactersViewModel.onBackPressed()
            }
        })
    }
}
