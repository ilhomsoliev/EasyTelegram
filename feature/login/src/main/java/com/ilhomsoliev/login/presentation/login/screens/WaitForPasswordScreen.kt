package com.ilhomsoliev.login.presentation.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.login.R
import com.ilhomsoliev.shared.common.CustomButton
import com.ilhomsoliev.shared.shared.textFields.PasswordTextField


data class WaitForPasswordState(
    val password: String,
    val isLoading: Boolean,
)

interface WaitForPasswordCallback {
    fun onPasswordChange(value: String)
    fun onPasswordCheck()
    fun onBack()
    fun onNext()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitForPasswordScreen(
    state: WaitForPasswordState,
    callback: WaitForPasswordCallback,
) {
    Scaffold(
        topBar = {
            IconButton(modifier = Modifier.padding(start = 4.dp), onClick = {
                callback.onBack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Продолжить",
                isActive = (state.password.isNotEmpty())
            ) {
                callback.onPasswordCheck()
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PasswordInfoLabel()

                Spacer(modifier = Modifier.height(70.dp))

                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    onValueChange = { callback.onPasswordChange(it) },
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    )
}

@Composable
private fun PasswordInfoLabel() {
    Image(
        modifier = Modifier.size(83.dp, 95.dp),
        painter = painterResource(id = R.drawable.password_label),
        contentDescription = null
    )

    Text(
        text = "Код пароль", style = MaterialTheme.typography.labelMedium.copy(
            fontSize = 25.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
        )
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Ваш аккаунт защищен персональным\n" +
                "кодом-паролем\n" +
                "Введите код-пароль, который вы используете\n" +
                "при входе в Telegram",
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight(400)
        )
    )
}