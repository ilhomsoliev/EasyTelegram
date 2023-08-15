package com.ilhomsoliev.login.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.login.R
import com.ilhomsoliev.shared.common.CustomButton
import kotlinx.coroutines.launch

interface OnBoardingCallback {
    fun onBackClick()
    fun onDone()
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingContent(
    callback: OnBoardingCallback,
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0
    )
    val onBoardModel = onBoardingScreens[pagerState.currentPage]

    Scaffold(topBar = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = {
                callback.onBackClick()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Экскурсия по приложению")
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.3f)
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize(),
                    pageCount = onBoardingScreens.size,
                    state = pagerState
                ) {
                    val onBoardingModel = onBoardingScreens[it]
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = onBoardingModel.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,

                        )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(vertical = 14.dp, horizontal = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xB5FFFFFF))
                                .clickable {
                                    if (pagerState.currentPage - 1 >= 0)
                                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                                }
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.align(Alignment.Center),
                                imageVector = Icons.Default.ArrowBackIos,
                                contentDescription = null
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xB5FFFFFF))
                                .clickable {
                                    if (pagerState.currentPage + 1 < onBoardingScreens.size)
                                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                                }
                                .padding(4.dp), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = null
                            )
                        }
                    }

                    Dashes(modifier = Modifier.fillMaxWidth(), pagerState.currentPage)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = onBoardModel.title,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = onBoardModel.description,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                        )
                    )
                }
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    text = "Готово"
                ) {
                    callback.onDone()
                }
            }
        }
    }
}

@Composable
private fun Dashes(modifier: Modifier = Modifier, currentScreenIndex: Int) {
    fun getColor(index: Int) = if (index <= currentScreenIndex) Color.White else Color(0xFF979797)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(getColor(0))
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(getColor(1))
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(getColor(2))
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(getColor(3))
        )

    }
}

val onBoardingScreens = listOf(
    OnBoardingScreenModel(
        R.drawable.on_board_1,
        "Совместимость с Telegram",
        "Все ваши чаты и группы появятся автомачически при первом запуске приложения “BlaBlaChat",
    ),
    OnBoardingScreenModel(
        R.drawable.on_board_2,
        "Безопасность",
        "Вы можете создавать запароленные чаты, кеш которых, будет храниться на вашем устройстве\n" +
                "Узнать об их существовании не смогут даже лица, получившие доступ к вашему телефону",
    ),
    OnBoardingScreenModel(
        R.drawable.on_board_3,
        "Архив с архивами",
        "Сортируйте ненужные чаты и группы по тематическим архивам",
    ),
    OnBoardingScreenModel(
        R.drawable.on_board_4,
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