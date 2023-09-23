package com.ilhomsoliev.login.presentation.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.login.R
import com.ilhomsoliev.shared.country.Country

data class WaitForCodeState(
    val country: Country?,
    val phoneNumber: String,
    val code: String,
    val isLoading: Boolean,
    val focuses: List<FocusRequester>,
    val sec: Int,
)

interface WaitForCodeCallback {
    fun onCodeChange(index: Int, number: String)
    fun onBack()
    fun firstRequest()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitForCodeScreen(
    state: WaitForCodeState,
    callback: WaitForCodeCallback,
) {
    LaunchedEffect(key1 = Unit, block = {
        callback.firstRequest()
    })

    Scaffold(
        topBar = {
            IconButton(modifier = Modifier.padding(start = 4.dp), onClick = {
                callback.onBack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
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
                CodeInfoLabel(
                    phoneNumber = state.phoneNumber,
                )

                Spacer(modifier = Modifier.height(70.dp))

                DigitCode(
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally),
                    code = state.code, focuses = state.focuses
                ) { index, it ->
                    callback.onCodeChange(index, it)
                }

                Spacer(modifier = Modifier.height(24.dp))

                DidNotReceivedCodeMessage()
            }
        }
    )
}

@Composable
private fun DidNotReceivedCodeMessage() {

    val uriTag = "URI"
    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        val text = "Не получили код?"
        append(
            AnnotatedString(
                text,
            )
        )
        addStyle(
            style = SpanStyle(fontSize = 12.sp),
            start = 0,
            end = text.length
        )
        addStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = Color(0xFF007EEC),
            ),
            start = 0,
            end = text.length
        )
        addStringAnnotation(
            tag = uriTag,
            annotation = "https://developer.android.com/jetpack/compose", // TODO change url
            start = 0,
            end = text.length,
        )
    }


    ClickableText(
        text = annotatedString,
        onClick = { position ->
            // find annotations by tag and current position
            val annotations = annotatedString.getStringAnnotations(
                uriTag,
                start = position,
                end = position
            )
            annotations.firstOrNull()?.let {
                uriHandler.openUri(it.item)
            }
        },
    )

}

@Composable
private fun CodeInfoLabel(
    phoneNumber: String,
) {
    Image(
        modifier = Modifier.size(83.dp, 95.dp),
        painter = painterResource(id = R.drawable.code_label),
        contentDescription = null
    )

    Text(
        text = "Введите код", style = MaterialTheme.typography.labelMedium.copy(
            fontSize = 25.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
        )
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Мы выслали код на номер\n+${phoneNumber.trimStart('+')}",
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight(400)
        )
    )
}

@Composable
private fun DigitCode(
    modifier: Modifier = Modifier,
    code: String,
    focuses: List<FocusRequester>,
    onChange: (Int, String) -> Unit,
) {
    Row(modifier = modifier) {
        focuses.forEachIndexed { index, focus ->
            val number = if (code.length > index)
                code[index].toString() else "";

            BasicTextField(
                value = number,
                onValueChange = { onChange(index, it) },
                modifier = Modifier.focusRequester(focus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF232323),
                    textAlign = TextAlign.Center,
                ),
                singleLine = true,
                decorationBox = {
                    Box(
                        modifier = Modifier
                            .size(42.dp, 48.dp)
                            .border(
                                (0.5).dp,
                                if (number.isEmpty()) Color(0xFF979797)
                                else Color(0xFF007EEC), RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        it()
                    }
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}
