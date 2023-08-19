package com.ilhomsoliev.chat.presentation.message_types

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.chat.model.message.MessageModel
import com.ilhomsoliev.chat.presentation.message_item.MessageStatus
import org.drinkless.td.libcore.telegram.TdApi

@Composable
fun CallMessage(message: MessageModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        CallMessage(message.content as TdApi.MessageCall)
        MessageStatus(message)
    }
}

@Composable
private fun CallMessage(content: TdApi.MessageCall, modifier: Modifier = Modifier) {
    val msg = when (content.discardReason) {
        is TdApi.CallDiscardReasonHungUp -> {
            "Incoming call"
        }

        is TdApi.CallDiscardReasonDeclined -> {
            "Declined call"
        }

        is TdApi.CallDiscardReasonDisconnected -> {
            "Call disconnected"
        }

        is TdApi.CallDiscardReasonMissed -> {
            "Missed call"
        }

        is TdApi.CallDiscardReasonEmpty -> {
            "Call: Unknown state"
        }

        else -> "Call: Unknown state"
    }
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = msg, modifier = modifier)
        Icon(
            imageVector = Icons.Outlined.Call,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(18.dp)
        )
    }
}