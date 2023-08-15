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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.login.R

data class WaitForCodeState(
    val code: String,
    val isLoading: Boolean,
    val focuses: List<FocusRequester>,
    val sec: Int,
)

interface WaitForCodeCallback {
    fun onCodeChange(index: Int, number: String)
    fun onBack()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitForCodeScreen(
    state: WaitForCodeState,
    callback: WaitForCodeCallback,
) {

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
                CodeInfoLabel()
                DigitCode(
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally),
                    code = state.code, focuses = state.focuses
                ) { index, it ->
                    callback.onCodeChange(index, it)
                }
            }
        }
    )
}

@Composable
private fun CodeInfoLabel() {
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
        text = "Мы выслали код на номер\n" +
                "+7 911 999 9999",
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
    Row {
        focuses.forEachIndexed { index, focus ->
            BasicTextField(
                value = if (code.length > index)
                    code[index].toString() else "",
                onValueChange = { onChange(index, it) }, modifier = modifier
                    .clip(MaterialTheme.shapes.large)
                    .size(60.dp)
                    .focusRequester(focus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                //textStyle = ThemeExtra.typography.CodeText,
                //colors = textFieldColors(),
                singleLine = true,
                decorationBox = {
                    Box(
                        modifier = Modifier
                            .size(42.dp, 48.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center
                    ) {
                        it()
                    }
                }
            )
        }
    }
}