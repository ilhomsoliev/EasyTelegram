package com.ilhomsoliev.login.presentation.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.shared.country.Country

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitForNumberScreen(
    pickedCountry: Country? = null,
    isLoading: Boolean,
    onNumberEnter: (String) -> Unit,
    onChooseCountryClick: () -> Unit,
) {
    val phoneNumber = remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNumberEnter(phoneNumber.value)
            }) {
                if (isLoading)
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null) // TODO
                else
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
                    .padding(top = 100.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Your phone number")

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Please confirm your country code\nand enter your phone number.",
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = pickedCountry?.name ?: "",
                    onValueChange = {},
                    enabled = false,
                    readOnly = true,
                    placeholder = {
                        Text(text = "Country")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onChooseCountryClick()
                        }, trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null)
                    }, label = {
                        Text(text = "Country")
                    })

                Spacer(modifier = Modifier.height(16.dp))

                PhoneNumberTextField(
                    modifier = Modifier.fillMaxWidth(),
                    code = pickedCountry?.code ?: "",
                    phoneNumber = phoneNumber.value,
                    onCodeChange = { code ->

                    },
                    onPhoneNumberChange = {
                        phoneNumber.value = it
                    }
                )
            }
        }
    )
}

@Composable
private fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    code: String,
    phoneNumber: String,
    onCodeChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
) {

    Row(modifier = modifier.border(2.dp, Color.Blue, RoundedCornerShape(12.dp))) {
        BasicTextField(
            modifier = Modifier.width(20.dp),
            value = code,
            onValueChange = { onCodeChange(it) },
            decorationBox = { innerTextField ->
                innerTextField
            })
        Box(
            modifier = Modifier
                .height(12.dp)
                .width(1.dp)
                .background(Color.Gray)
        )
        BasicTextField(
            value = phoneNumber,
            onValueChange = {
                onPhoneNumberChange(it)
            },
            singleLine = true,
            decorationBox = {
                it
            },
        )
    }

}