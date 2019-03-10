package io.utkan.marvel.domain.image.variants

enum class StandardImageVariants(val variantName: String, private val dimen: String) {
    Small("standard_small", "65x45px"),
    Medium("standard_medium", "100x100px"),
    Large("standard_large", "140x140px"),
    Xlarge("standard_xlarge", "200x200px"),
    Fantastic("standard_fantastic", "250x250px"),
    Amazing("standard_amazing", "180x180px"),
}
