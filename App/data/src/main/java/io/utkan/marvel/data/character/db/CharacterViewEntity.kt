package io.utkan.marvel.data.character.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CharacterView")
class CharacterViewEntity {
    @PrimaryKey
    @NonNull
    var id: Int? = null

    var viewCount: Int = 0
}