package io.utkan.marvelui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.utkan.marvel.presentation.CharacterViewModel
import io.utkan.marvelui.R


class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    var characterList: MutableList<CharacterViewModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.character_list_item, parent, false)
        return CharactersViewHolder(view)
    }

    override fun getItemCount() = characterList?.size ?: 0

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = characterList?.get(position)
        character?.let {model->
            holder.characterName.text = model.name
            Glide.with(holder.view).load(model.thumbnail).into(holder.characterImage)
            holder.characterImage.setOnClickListener{
                if (model.action != null) {
                    model.action?.invoke(model.detailImageUrl)
                }
            }
        }
    }

    fun updateList(characters: List<CharacterViewModel>) {
        val diffCallback = CharactersDiffCallback(characters, this.characterList?.toList()!!)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.characterList?.clear()
        this.characterList?.addAll(characters)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CharactersViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var characterImage: ImageView = view.findViewById(R.id.character_image)
        var characterName: TextView = view.findViewById(R.id.character_name)
    }
}