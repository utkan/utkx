package io.utkan.marvelui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.utkan.marvelui.R
import kotlinx.android.synthetic.main.activity_characters.*

class CharactersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)
        setSupportActionBar(toolbar)
    }
}
