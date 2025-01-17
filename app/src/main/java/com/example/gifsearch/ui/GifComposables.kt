package com.example.gifsearch.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color.parseColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.example.gifsearch.R
import com.example.gifsearch.data.Gif
import com.example.gifsearch.data.GifInfo

/*
    All GIFs related composables: GifGid, GifImage, GifImageDetailed
 */

@Composable
fun GifGrid(
    viewModel: MainViewModel,
    gifs: LazyPagingItems<Gif>,
    windowWidth: WindowWidthSizeClass,
    scrollState: LazyStaggeredGridState
){
    when(windowWidth){
        WindowWidthSizeClass.Compact ->{
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = StaggeredGridCells.Fixed(3),
                contentPadding = PaddingValues(
                    horizontal = 6.dp,
                ),
                state = scrollState
            ) {
                items(gifs.itemCount) {
                    val gif = gifs[it]
                    if (gif != null && gif.images.original.size != "0") {
                        if(gif.images.downsized.url != "" || gif.images.original.url != ""){
                            GifImage(
                                gif = gif,
                                onClick = {
                                    viewModel.showGif(gif)
                                },
                            )
                        }
                    }
                }
            }
        }
        WindowWidthSizeClass.Medium -> {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = StaggeredGridCells.Fixed(3),
                contentPadding = PaddingValues(
                    horizontal = 6.dp,
                ),
                state = scrollState
            ) {
                items(gifs.itemCount) {
                    val gif = gifs[it]
                    if (gif != null && gif.images.original.size != "0") {
                        if(gif.images.downsized.url != "" || gif.images.original.url != ""){
                            GifImage(
                                gif = gif,
                                onClick = {
                                    viewModel.showGif(gif)
                                },
                            )
                        }
                    }
                }
            }
        }
        WindowWidthSizeClass.Expanded -> {
            LazyHorizontalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize(),
                rows = StaggeredGridCells.Fixed(3),
                contentPadding = PaddingValues(
                    horizontal = 6.dp,
                ),
                state = scrollState
            ) {
                items(gifs.itemCount) {
                    val gif = gifs[it]
                    if (gif != null && gif.images.original.size != "0") {
                        if(gif.images.downsized.url != "" || gif.images.original.url != ""){
                            GifImage(
                                gif = gif,
                                onClick = {
                                    viewModel.showGif(gif)
                                },
                            )
                        }
                    }
                }
            }
        }
        else -> {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = StaggeredGridCells.Fixed(3),
                contentPadding = PaddingValues(
                    horizontal = 6.dp,
                ),
                state = scrollState
            ) {
                items(gifs.itemCount) {
                    val gif = gifs[it]
                    if (gif != null && gif.images.original.size != "0") {
                        if(gif.images.downsized.url != "" || gif.images.original.url != ""){
                            GifImage(
                                gif = gif,
                                onClick = {
                                    viewModel.showGif(gif)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GifImage(
    gif: Gif,
    onClick: () -> Unit = {},
){
    val image: GifInfo = if (gif.images.downsized.url != "") gif.images.downsized else gif.images.original
    val aspectRatio = image.width.toFloat() / image.height.toFloat()
    val surfaceColor = Color(parseColor(gif.backgroundColor))

    Surface(
        modifier = Modifier
            .padding(2.dp),
        color = surfaceColor
    ){
        AsyncImage(
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .clickable{
                    onClick()
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.url)
                .crossfade(true)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.DISABLED)
                .build(),
            contentDescription = "${gif.title}",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun GifImageDetailed(
    gif: Gif,
    onDismiss: () -> Unit
){
    val context = LocalContext.current
    val aspectRatio = gif.images.original.width.toFloat() / gif.images.original.height.toFloat()
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(gif.images.original.url)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .size(Size.ORIGINAL)
            .build()
    )
    val state = painter.state.collectAsState()

    when(state.value){
        is AsyncImagePainter.State.Loading -> {
            Box(
                modifier = Modifier
                    .wrapContentSize(
                        align = Alignment.Center
                    )
                    .background(
                        color = if (isSystemInDarkTheme()) Color.Black else Color.White,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(2.dp),
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            }

        }
        is AsyncImagePainter.State.Success -> {
            Dialog(
                onDismissRequest = { onDismiss() }
            ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(aspectRatio),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(parseColor(gif.backgroundColor))
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        Image(
                            painter = painter,
                            contentDescription = "${gif.title}",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.BottomEnd)
                        )
                        Surface(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.BottomCenter)
                                .padding(4.dp),
                            shape = RoundedCornerShape(22.dp),
                            color = Color.Black
                        ){
                            Image(
                                modifier = Modifier
                                    .width(90.dp)
                                    .clickable{
                                        shareGif(context, gif)
                                    },
                                painter = painterResource(R.drawable.share),
                                contentDescription = "Send button",
                            )
                        }
                    }
                }
            }
        }
        is AsyncImagePainter.State.Error -> {
        }
        else -> {}
    }
}

private fun shareGif(context: Context, gif: Gif){
    val intent = Intent(Intent.ACTION_SEND).apply{
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, gif.title)
        putExtra(Intent.EXTRA_TEXT, gif.images.original.url)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            "Share GIF"
        )
    )
}

