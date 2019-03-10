package io.utkan.marvelui.view

import androidx.recyclerview.widget.DiffUtil
import io.utkan.marvel.presentation.CharacterViewModel

class CharactersDiffCallback(
    private var newCharacters: List<CharacterViewModel>,
    private var oldCharacters: List<CharacterViewModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldCharacters.size
    }

    override fun getNewListSize(): Int {
        return newCharacters.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCharacters[oldItemPosition].id == newCharacters[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCharacters[oldItemPosition] == newCharacters[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}