package com.ilhomsoliev.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ilhomsoliev.tgcore.AppDataState
import com.ilhomsoliev.tgcore.newUpdateFromTdApi
import kotlinx.coroutines.flow.first
import org.drinkless.tdlib.TdApi


@SuppressLint("UnrememberedMutableState")
@Composable
fun TelegramImage(
    downloadManager: TgDownloadManager,
    file: TdApi.File?,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    var photo by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(key1 = Unit, block = {
        file?.let {
            photo = downloadManager.downloadableFile(it).first()
        }
    })
    LaunchedEffect(key1 = newUpdateFromTdApi.value, block = {
        file?.id?.let {
            AppDataState.getFile(it)?.local?.path?.let {
                photo = it
            }
        }
    })

    photo?.let {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(it)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    } ?: Box(modifier.background(Color.LightGray))
}
