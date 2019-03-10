package io.utkan.marvel.data.character

import io.utkan.marvel.data.character.db.AppDatabase
import io.utkan.marvel.data.character.db.CharacterViewEntity
import javax.inject.Inject

class CharacterViewDataImpl @Inject constructor(private val appDatabase: AppDatabase) :
    CharacterViewData {
    override fun viewed(characterId: Int) {
        Thread(Runnable {
            val characterViewDao = appDatabase.characterViewEntity()
            val loadCharacterViewById = characterViewDao.loadCharacterViewById(characterId)
            val characterViewEntity = CharacterViewEntity()
            characterViewEntity.id = characterId
            if (loadCharacterViewById == null) {
                characterViewEntity.viewCount = 1
                characterViewDao.insertCharacterViewEntity(characterViewEntity)
            } else {
                characterViewEntity.viewCount = loadCharacterViewById.viewCount + 1
                characterViewDao.updateCharacterViewEntity(characterViewEntity)
            }
        }).start()
    }
}