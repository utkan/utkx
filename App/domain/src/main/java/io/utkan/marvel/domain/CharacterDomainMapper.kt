package io.utkan.marvel.domain

import io.utkan.marvel.data.model.CharacterData
import io.utkan.marvel.data.model.ThumbnailData
import io.utkan.marvel.domain.image.variants.StandardImageVariants
import io.utkan.marvel.domain.model.CharacterDomain
import javax.inject.Inject

class CharacterDomainMapper @Inject constructor() : ModelMapper<CharacterData, CharacterDomain> {
    override fun map(entity: CharacterData): CharacterDomain {
        return CharacterDomain(
            id = entity.id,
            name = entity.name,
            thumbnail = entity.thumbnail.toUrl()
        )
    }

    private fun ThumbnailData.toUrl(): String {
        return path + "/" + StandardImageVariants.Medium.dimen + "." + extension
    }
}