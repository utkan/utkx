package io.utkan.marvel.data.character.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CharacterViewDao {

    @Query("SELECT * FROM CharacterView")
    fun findAllViewedCharactersSync(): List<CharacterViewEntity>

    @Query("select * from CharacterView where id = :id")
    fun loadCharacterViewById(id: Int): CharacterViewEntity?

    @Query("SELECT * FROM CharacterView WHERE id IN (:ids)")
    fun loadCharacterViewByIds(ids: List<Int>): List<CharacterViewEntity>?

    @Insert
    fun insertCharacterViewEntity(characterViewEntity: CharacterViewEntity)

    @Update
    fun updateCharacterViewEntity(characterViewEntity: CharacterViewEntity)
}
