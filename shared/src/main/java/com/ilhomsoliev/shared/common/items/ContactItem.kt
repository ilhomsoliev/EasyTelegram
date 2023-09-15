package com.ilhomsoliev.shared.common.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.profile.model.UserModel
import com.ilhomsoliev.profile.model.UserStatusState
import com.ilhomsoliev.shared.TgDownloadManager
import com.ilhomsoliev.shared.common.ChatItemImage
import java.util.concurrent.TimeUnit

@Composable
fun ContactItem(
    modifier: Modifier = Modifier,
    downloadManager: TgDownloadManager,
    user: UserModel,
    onClick: () -> Unit,
) {
    val name = user.firstName + " " + user.lastName
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable {
            onClick()
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                ChatItemImage(
                    modifier = Modifier,
                    file = user.profilePhoto?.smallFile,
                    username = name,
                    downloadManager = downloadManager,
                    imageSize = 36.dp,
                )
            }
            //
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(600),
                    )
                )
                Text(
                    text = getLastSeenIndicator(user.status),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                    )
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 52.dp, top = 8.dp), thickness = 1.dp
        )
    }
}

fun getLastSeenIndicator(status: UserStatusState) =
    when (status) {
        UserStatusState.Empty -> "Был(а) в сети недавно" // TODO
        is UserStatusState.Online -> "В сети"
        is UserStatusState.Offline -> "Был(а) ${getLastSeenTimeIndicator(status.wasOnline)} назад"
        UserStatusState.Recently -> "Был(а) в сети недавно"
        UserStatusState.LastWeek -> "Был(а) в сети неделю назад"
        UserStatusState.LastMonth -> "Был(а) в сети месяц назад"
    }

fun getLastSeenTimeIndicator(givenTimeMillis: Int): String {
    val timeDifferenceInMillis = getTimeDifferenceInMillis(givenTimeMillis)
    val days = TimeUnit.MILLISECONDS.toDays(timeDifferenceInMillis)
    val hoursDifference = TimeUnit.MILLISECONDS.toHours(timeDifferenceInMillis)
    val minutesDifference = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceInMillis)
    return if (days == 0L && hoursDifference == 0L) {
        "$minutesDifference минут"
    } else if (days == 0L) {
        "$hoursDifference часов"
    } else {
        "$days дней"
    }
}

fun getTimeDifferenceInMillis(givenTimeMillis: Int): Long {
    val currentTimeMillis = System.currentTimeMillis()
    return (givenTimeMillis * 1000L) - currentTimeMillis
}
