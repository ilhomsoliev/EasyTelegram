package com.ilhomsoliev.login.presentation.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.shared.common.CustomButton

interface WelcomeCallback {
    fun onNextClick()
    fun onGoToOnBoardingClick()
}

@Composable
fun WelcomeContent(
    callback: WelcomeCallback,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*Image(
                painter = painterResource(id = com.ilhomsoliev.shared.R.drawable.app_icon),
                contentDescription = null
            )*/
            Text(text = "BlaBlaChat")
            Text(
                text = "Мобильное клиент использующий\n" +
                        "API Telegram"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Продолжить"
            ) {
                callback.onNextClick()
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Экскурсия по приложению",
                isSolid = false,
            ) {
                callback.onGoToOnBoardingClick()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}