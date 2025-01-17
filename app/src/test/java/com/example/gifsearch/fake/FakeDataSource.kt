package com.example.gifsearch.fake

import com.example.gifsearch.data.Gif
import com.example.gifsearch.data.GifImages
import com.example.gifsearch.data.GifInfo
import com.example.gifsearch.data.Gifs

/*
    This object contains fake data
 */

object FakeDataSource {
    val gifs = Gifs(
        listOf(
            Gif(
                title = "Title_1",
                images = GifImages(
                    downsized = GifInfo(
                        url = "url1",
                        height = "height1",
                        width = "width1",
                        size = "size1"
                    ),
                    original = GifInfo(
                        url = "url1",
                        height = "height1",
                        width = "width1",
                        size = "size1"
                    )
                ),
                backgroundColor = "#000000"
            ),
            Gif(
                title = "Title_2",
                images = GifImages(
                    downsized = GifInfo(
                        url = "url2",
                        height = "height2",
                        width = "width2",
                        size = "size2"
                    ),
                    original = GifInfo(
                        url = "url2",
                        height = "height2",
                        width = "width2",
                        size = "size2"
                    )
                ),
                backgroundColor = "#000000"
            )
        )
    )
    val empty = Gifs(
        emptyList(
        )
    )
}