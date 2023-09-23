package com.ilhomsoliev.shared.shared.dialogs.alertDialogs

import androidx.compose.runtime.Composable

@Composable
fun CleanChatHistoryDialog(
    isDialogShown: Boolean,
    onDismissRequest: () -> Unit,
    onNegativeButtonClick: () -> Unit = onDismissRequest,
    onPositiveButtonClick: (Boolean) -> Unit,
) {

    BaseAlertDialog(
        isDialogShown = isDialogShown,
        onDismissRequest = onDismissRequest,
        title = "Очистить историю",
        description = "Уверены что хотите очистить историю в чате?",
        negativeButtonText = "ОТМЕНА",
        positiveButtonText = "ОЧИСТИТЬ",
        isCheckBoxActive = false,
        checkBoxText = "Очистить только у себя",
        onNegativeButtonClick = {
            onNegativeButtonClick()
        },
        onPositiveButtonClick = {
            onPositiveButtonClick(it ?: false)
        },
    )
}