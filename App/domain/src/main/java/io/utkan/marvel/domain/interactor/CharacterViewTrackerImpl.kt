package io.utkan.marvel.domain.interactor

import io.utkan.marvel.data.character.CharacterViewData
import javax.inject.Inject

class CharacterViewTrackerImpl @Inject constructor(
    private val characterViewData: CharacterViewData
) : CharacterViewTracker {
    override fun track(characterId: Int) {
        characterViewData.viewed(characterId)
    }
}
