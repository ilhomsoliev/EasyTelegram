package com.ilhomsoliev.shared.shared.dialogs.customDialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.shared.common.CustomButton

@Composable
fun ConfirmPhoneNumberDialog(
    isDialogShown: Boolean,
    onDismissRequest: () -> Unit,
    phoneNumber: String,
    onNegativeButtonClick: () -> Unit = onDismissRequest,
    onPositiveButtonClick: () -> Unit,
) {

    BaseCustomDialog(
        isDialogShown = isDialogShown,
        onDismissRequest = onDismissRequest,
        cornerShape = 24.dp,
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = phoneNumber,
                    style = TextStyle(
                        fontSize = 28.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF3E3E3E),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.15.sp,
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Проверьте номер")
                Spacer(modifier = Modifier.height(12.dp))
                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Изменить",
                    isSolid = false
                ) {
                    onNegativeButtonClick()
                }
                Spacer(modifier = Modifier.height(12.dp))
                CustomButton(modifier = Modifier.fillMaxWidth(), text = "Продолжить") {
                    onPositiveButtonClick()
                }
            }
        }
    )
}