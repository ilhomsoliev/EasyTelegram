package com.ilhomsoliev.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.Dispatchers
import org.drinkless.td.libcore.telegram.TdApi


@SuppressLint("UnrememberedMutableState")
@Composable
fun TelegramImage(
    client: TelegramClient,
    file: TdApi.File?,
    modifier: Modifier = Modifier,
) {
    val photo = file?.let {
        client.downloadableFile(file).collectAsState(file.local.path, Dispatchers.IO)
    } ?: mutableStateOf(null)

    photo.value?.let {
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
