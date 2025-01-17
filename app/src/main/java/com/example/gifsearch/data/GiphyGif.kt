package com.example.gifsearch.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/*
    Data class to store the parsed results
 */

@Serializable
data class Gifs(
    val data: List<Gif>
)

@Serializable
data class Gif(
    val images: GifImages,
    val title: String? = null,
    @Transient val backgroundColor: String = "#000000"
)

@Serializable
data class GifImages(
    val downsized: GifInfo,
    val original: GifInfo
)

@Serializable
data class GifInfo(
    val url: String = "",
    val height: String = "",
    val width: String = "",
    val size: String = ""
)