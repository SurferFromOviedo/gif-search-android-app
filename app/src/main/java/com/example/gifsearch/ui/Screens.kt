package com.example.gifsearch.ui

import android.content.res.Configuration
import android.graphics.Color.parseColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gifsearch.R
import com.example.gifsearch.data.Gif

/*
    All composables related to the main screen. AppRoot, NotLoadingScreen, ErrorScreen,
    LoadingScreen, CustomSearchBar
 */

@Composable
fun AppRoot(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    windowWidth: WindowWidthSizeClass
){
    val gifs = viewModel.gifs.collectAsLazyPagingItems()
    val query = viewModel.query.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CustomSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(
                    top = 4.dp,
                    bottom = 2.dp
                ),
            query = query.value,
            viewModel = viewModel
        )

        when(gifs.loadState.refresh){
            is LoadState.Error -> {
                ErrorScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    isOnline = viewModel.isOnline,
                    onRetry = {
                        gifs.retry()
                        viewModel.setIsRetryPending(false)
                    },
                    viewModel = viewModel
                )
            }
            is LoadState.Loading -> {
                LoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            is LoadState.NotLoading -> {
                NotLoadingScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    viewModel = viewModel,
                    gifs = gifs,
                    query = query.value,
                    onRetry = {
                        gifs.retry()
                        viewModel.setIsRetryPending(false)
                    },
                    windowWidth = windowWidth
                )
            }
            else -> {}
        }
    }
}

@Composable
fun NotLoadingScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    gifs: LazyPagingItems<Gif>,
    query: String,
    onRetry: () -> Unit = {},
    windowWidth: WindowWidthSizeClass
){
    val scrollState = viewModel.scrollState
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        if(gifs.itemCount == 0){
            Text(
                text = "No GIFs found",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Column(
            modifier = modifier
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (query.isEmpty() && viewModel.isOnline) {
                    Text(
                        text = "Trending now",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                        contentDescription = "Trending"
                    )
                }
                if (!viewModel.isOnline){
                    viewModel.setIsRetryPending(true)
                    Text(
                        text = "Internet connection lost",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Icon(
                        imageVector = Icons.Outlined.WifiOff,
                        contentDescription = "Trending"
                    )
                }else{
                    if(viewModel.isRetryPending){
                        onRetry()
                    }
                }
            }
            val triggerReload = remember { mutableStateOf(false) }
            LaunchedEffect(viewModel.isOnline) {
                if (viewModel.isOnline) {
                    triggerReload.value = true
                }
            }
            key(triggerReload.value){
                GifGrid(
                    viewModel = viewModel,
                    gifs = gifs,
                    windowWidth = windowWidth,
                    scrollState = scrollState
                )
                triggerReload.value = false
            }
        }
        if (viewModel.selectedGif != null){
            GifImageDetailed(
                gif = viewModel.selectedGif!!,
                onDismiss = {
                    viewModel.showGif(gif = null)
                }
            )
        }
        Image(
            modifier = Modifier
                .width(175.dp)
                .padding(horizontal = 10.dp, vertical = 2.dp)
                .align(Alignment.BottomStart),
            painter = painterResource(R.drawable.poweredbyofficial2),
            contentDescription = "Powered by Giphy",
        )
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    isOnline: Boolean,
    onRetry: () -> Unit = {}
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        if (!isOnline){
            viewModel.setIsRetryPending(true)
            Text(
                text = "No internet connection.",
            )
        }else{
            if(viewModel.isRetryPending){
                onRetry()
            }
            Text(
                text = "Error loading gifs. Try again later.",
            )
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    viewModel: MainViewModel
){
    BasicTextField(
        modifier = modifier,
        value = query,
        onValueChange = {
            viewModel.updateQuery(it)
        },
        maxLines = 1,
        textStyle = MaterialTheme.typography.titleMedium.copy(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(parseColor("#FF6666")),
                    Color(parseColor("#FFFF99")),
                    Color(parseColor("#00FF99")),
                    Color(parseColor("#00CCFF")),
                    Color(parseColor("#9933FF")),
                )
            )
        ),
        cursorBrush = SolidColor(if(isSystemInDarkTheme()) Color.White else Color.Black),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .background( if(isSystemInDarkTheme())Color.Black else Color.White)
                    .border(1.dp, if(isSystemInDarkTheme()) Color.White else Color.Black)
            ){
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Search GIPHY ",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                    if (query.isNotEmpty()){
                        IconButton(
                            onClick = {
                                viewModel.updateQuery("")
                            },
                            modifier = Modifier
                                .size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Cancel,
                                contentDescription = "Clear query",
                                modifier = Modifier
                                    .size(24.dp),
                                tint = if(isSystemInDarkTheme()) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }
    )
}


