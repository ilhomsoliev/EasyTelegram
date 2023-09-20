package com.ilhomsoliev.shared

import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.drinkless.tdlib.TdApi

class TgDownloadManager(
    private val tgClient: TelegramClient,
) {

    private fun downloadFile(fileId: Int): Flow<Unit> = callbackFlow {
        tgClient.baseClient.send(TdApi.DownloadFile(fileId, 1, 0, 0, true)) {
            when (it.constructor) {
                TdApi.Ok.CONSTRUCTOR -> {
                    trySend(Unit).isSuccess
                }

                else -> {
                    cancel("", Exception(""))

                }
            }
        }
        awaitClose()
    }

    fun downloadableFile(file: TdApi.File): Flow<String?> =
        file.takeIf {
            it.local?.isDownloadingCompleted == false
        }?.id?.let { fileId ->
            downloadFile(fileId).map { file.local?.path }
        } ?: flowOf(file.local?.path)

}