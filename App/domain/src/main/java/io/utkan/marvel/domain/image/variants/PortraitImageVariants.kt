package io.utkan.marvel.domain.image.variants

enum class PortraitImageVariants(val variantName: String, private val dimen: String) {
    Small("portrait_small", "50x75px"),
    Medium("portrait_medium", "100x150px"),
    Xlarge("portrait_xlarge", "150x225px"),
    Fantastic("portrait_fantastic", "168x252px"),
    Uncanny("portrait_uncanny", "300x450px"),
    Incredible("portrait_incredible", "216x324px"),
}
