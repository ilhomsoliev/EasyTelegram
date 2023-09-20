package com.ilhomsoliev.shared.shared.dialogs

import androidx.compose.runtime.Composable

@Composable
fun CleanChatHistoryDialog(
    isDialogShown: Boolean,
    onDismissRequest: () -> Unit,
    onNegativeButtonClick: () -> Unit = onDismissRequest,
    onPositiveButtonClick: () -> Unit,
) {
    BaseAlertDialog(
        isDialogShown = isDialogShown,
        onDismissRequest = onDismissRequest,
        title = "Очистить историю",
        description = "Уверены что хотите очистить историю в чате?",
        negativeButtonText = "ОТМЕНА",
        positiveButtonText = "ЗАКРЕПИТЬ",
        onNegativeButtonClick = {
            onNegativeButtonClick()
        },
        onPositiveButtonClick = {
            onPositiveButtonClick()
        }
    )
}