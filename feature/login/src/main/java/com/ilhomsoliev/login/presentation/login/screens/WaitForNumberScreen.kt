package com.ilhomsoliev.login.presentation.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.login.R
import com.ilhomsoliev.shared.common.CustomButton
import com.ilhomsoliev.shared.country.Country
import com.ilhomsoliev.shared.shared.dialogs.customDialogs.ConfirmPhoneNumberDialog
import com.ilhomsoliev.shared.shared.textFields.PhoneNumberTextField
import com.ilhomsoliev.shared.shared.utils.getPhoneNumberStructured

data class WaitForNumberState(
    val phoneNumber: String,
    val pickedCountry: Country?,
    val isLoading: Boolean,
    val isNextActive: Boolean,
)

interface WaitForNumberCallback {
    fun onNewNumberEnter(number: String)
    fun onChooseCountryClick()
    fun onBack()
    fun onNext()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitForNumberScreen(
    state: WaitForNumberState,
    callback: WaitForNumberCallback,
) {
    val isDialogShow = remember { mutableStateOf(false) }

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
                isActive = state.isNextActive
            ) {
                isDialogShow.value = true
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PhoneNumberInfoLabel()

                Spacer(modifier = Modifier.height(24.dp))

                PickedCountryContainer(state.pickedCountry) {
                    callback.onChooseCountryClick()
                }

                Spacer(modifier = Modifier.height(16.dp))

                PhoneNumberTextField(
                    modifier = Modifier.fillMaxWidth(),
                    code = state.pickedCountry?.phoneDial ?: "",
                    phoneNumber = state.phoneNumber,
                    onPhoneNumberChange = {
                        callback.onNewNumberEnter(it)
                    }
                )

                PolicyText()

            }
        }
    )
    ConfirmPhoneNumberDialog(
        isDialogShown = isDialogShow.value,
        onDismissRequest = {
            isDialogShow.value = false
        },
        phoneNumber = state.phoneNumber.getPhoneNumberStructured(state.pickedCountry?.phoneDial),
        onNegativeButtonClick = {
            isDialogShow.value = false
        },
        onPositiveButtonClick = {
            callback.onNext()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickedCountryContainer(
    pickedCountry: Country?,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            )
            .border(1.dp, Color(0xFF979797), RoundedCornerShape(4.dp))
            .clickable {
                onClick()
            }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = pickedCountry?.name ?: "Страна",
            color = if (pickedCountry == null) Color(0xFF979797) else Color.Unspecified
        )
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = null,
            tint = Color(0xFF979797)
        )
    }
}

@Composable
private fun PhoneNumberInfoLabel() {
    Image(
        modifier = Modifier.size(83.dp, 95.dp),
        painter = painterResource(id = R.drawable.phone),
        contentDescription = null
    )

    Text(
        text = "Ваш телефон", style = MaterialTheme.typography.labelMedium.copy(
            fontSize = 25.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
        )
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Подтвердите код вашей страны\n" +
                "и введите ваш номер телефона",
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight(400)
        )
    )
}

@Composable
private fun PolicyText() {
    val uriTag = "URI"
    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        val text = "Регистрируясь, вы соглашаетесь с политикой конфиденциальности"
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
            start = 33,
            end = text.length
        )
        addStringAnnotation(
            tag = uriTag,
            annotation = "https://developer.android.com/jetpack/compose", // TODO change url
            start = 33,
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