package io.utkan.marvel.data

interface ModelMapper<in R, out D> {
    fun map(entity: R): D
}