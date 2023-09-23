package com.ilhomsoliev.shared.shared.dialogs.customDialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun BaseCustomDialog(
    modifier: Modifier = Modifier,
    isDialogShown: Boolean,
    cornerShape: Dp = 4.dp,
    onDismissRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    if (isDialogShown)
        Dialog(onDismissRequest = {
            onDismissRequest()
        }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(cornerShape))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                content()
            }
        }
}