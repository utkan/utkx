package io.utkan.marvelui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.utkan.marvel.presentation.CharacterViewModel
import io.utkan.marvelui.R

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    var characterList: List<CharacterViewModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.character_list_item, parent, false)
        return CharactersViewHolder(view)
    }

    override fun getItemCount() = characterList?.size ?: 0

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = characterList?.get(position)
        character?.let {
            Glide.with(holder.view).load(it.thumbnail).into(holder.characterImage)
        }
    }

    inner class CharactersViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var characterImage: ImageView = view.findViewById(R.id.character_image)
    }
}