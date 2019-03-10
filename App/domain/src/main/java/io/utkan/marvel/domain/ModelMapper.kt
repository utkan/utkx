package io.utkan.marvel.domain

interface ModelMapper<in R, out D> {
    fun map(entity: R): D
}