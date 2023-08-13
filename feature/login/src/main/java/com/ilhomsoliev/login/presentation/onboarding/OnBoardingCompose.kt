package com.ilhomsoliev.login.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.login.R
import com.ilhomsoliev.shared.common.CustomButton

interface OnBoardingCallback {
    fun onNextClick()
    fun onBackClick()
    fun onPreviousClick()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingContent(
    screenIndex: Int,
    callback: OnBoardingCallback,
) {
    val pagerState = rememberPagerState(
        initialPage = 0
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            pageCount = onBoardingScreens.size,
            state = pagerState
        ) {
            val onBoardingModel = onBoardingScreens[it]
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = onBoardingModel.image),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                )

            }
        }

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp),
            text = "Готово"
        ) {

        }
    }


}

val onBoardingScreens = listOf(
    OnBoardingScreenModel(
        com.ilhomsoliev.shared.R.drawable.on_board_1,
        "Совместимость с Telegram",
        "Все ваши чаты и группы появятся автомачически при первом запуске приложения “BlaBlaChat",
    ),
    OnBoardingScreenModel(
        com.ilhomsoliev.shared.R.drawable.on_board_2,
        "Безопасность",
        "Вы можете создавать запароленные чаты, кеш которых, будет храниться на вашем устройстве\n" +
                "Узнать об их существовании не смогут даже лица, получившие доступ к вашему телефону",
    ),
    OnBoardingScreenModel(
        com.ilhomsoliev.shared.R.drawable.on_board_3,
        "Архив с архивами",
        "Вы можете создавать запароленные чаты, кеш которых, будет храниться на вашем устройстве\n" +
                "Узнать об их существовании не смогут даже лица, получившие доступ к вашему телефону",
    ),
    OnBoardingScreenModel(
        com.ilhomsoliev.shared.R.drawable.on_board_4,
        "Подборки",
        "Каналы на которые вы подписаны, раз в сутки смогут добавлять в подборку одну самую важную статью/информацию\n" +
                "Вам больше не придется “скролить” все подряд каналы, в поиске чего то важного!",
    ),
)

data class OnBoardingScreenModel(
    val image: Int,
    val title: String,
    val description: String,

    )