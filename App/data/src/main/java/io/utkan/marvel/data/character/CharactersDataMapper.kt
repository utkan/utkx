package io.utkan.marvel.data.character

import io.utkan.marvel.data.ModelMapper
import io.utkan.marvel.data.model.CharacterData
import io.utkan.marvel.data.model.ThumbnailData
import io.utkan.marvel.remote.model.Result
import javax.inject.Inject

class CharactersDataMapper @Inject constructor() : ModelMapper<Result, CharacterData> {
    override fun map(entity: Result): CharacterData {
        return CharacterData(
            id = entity.id,
            name = entity.name,
            thumbnail = entity.toThumbnailData()
        )
    }

    private fun Result.toThumbnailData(): ThumbnailData {
        val thumbnail = this.thumbnail
        return ThumbnailData(
            extension = thumbnail.extension,
            path = thumbnail.path
        )
    }
}