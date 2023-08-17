package com.ilhomsoliev.login.presentation.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.ilhomsoliev.shared.country.Country

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
                callback.onNext()
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

                OutlinedTextField(
                    value = state.pickedCountry?.name ?: "",
                    onValueChange = {},
                    enabled = false,
                    readOnly = true,
                    placeholder = {
                        Text(text = "Country")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            callback.onChooseCountryClick()
                        },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null)
                    }, label = {
                        Text(text = "Country")
                    })

                Spacer(modifier = Modifier.height(16.dp))

                PhoneNumberTextField(
                    modifier = Modifier.fillMaxWidth(),
                    code = state.pickedCountry?.phoneDial ?: "",
                    phoneNumber = state.phoneNumber,
                    onPhoneNumberChange = {
                        callback.onNewNumberEnter(it)
                    }
                )
                Text(text = "Регистрируясь, вы соглашаетесь с политикой конфиденциальности ")
            }
        }
    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    code: String,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
) {
    val separator = "|"
    val textFieldValue = "$code $separator $phoneNumber"

    OutlinedTextField(
        modifier = modifier,
        value = textFieldValue,
        onValueChange = {
            if (it.contains(separator))
                onPhoneNumberChange(it.substringAfter("$separator "))
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Phone, contentDescription = null)
        },
        label = {
            Text(text = "Country")
        },
        placeholder = {
            Text(text = "Country")
        },
    )
    /*
        Row(modifier = modifier.border(2.dp, Color.Blue, RoundedCornerShape(12.dp))) {
            BasicTextField(
                modifier = Modifier
                    .width(20.dp)
                    .height(280.dp),
                value = code,
                onValueChange = { onCodeChange(it) },
                decorationBox = { innerTextField ->
                    innerTextField()
                })
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(1.dp)
                    .background(Color.Gray)
            )
            BasicTextField(
                modifier = Modifier.height(280.dp),
                value = phoneNumber,
                onValueChange = {
                    onPhoneNumberChange(it)
                },
                singleLine = true,
                decorationBox = {
                    it()
                },
            )
        }*/

}