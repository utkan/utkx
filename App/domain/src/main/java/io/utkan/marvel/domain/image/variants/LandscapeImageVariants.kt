package io.utkan.marvel.domain.image.variants

enum class LandscapeImageVariants(val variantName: String, private val dimen: String) {
    Small("landscape_small", "120x90px"),
    Medium("landscape_medium", "175x130px"),
    Large("landscape_large", "190x140px"),
    Xlarge("landscape_xlarge", "270x200px"),
    Amazing("landscape_amazing", "250x156px"),
    Incredible("landscape_incredible", "464x261px"),
}
