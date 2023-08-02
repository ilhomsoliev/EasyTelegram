package com.ilhomsoliev.login.presentation.login.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitForNumberScreen(
    isLoading: Boolean,
    onNumberEnter: (String) -> Unit,
    onChooseCountryClick: () -> Unit,
) {
    val phoneNumber = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNumberEnter(phoneNumber.value.text)
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
                    value = "",
                    onValueChange = {},
                    enabled = false,
                    readOnly = true,
                    placeholder = {
                        Text(text = "Country")
                    },
                    modifier = Modifier.fillMaxWidth().clickable {
                        onChooseCountryClick()
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null)
                    })

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = phoneNumber.value,
                    onValueChange = { phoneNumber.value = it },
                    modifier = Modifier.fillMaxWidth(),
                )

            }
        }
    )
}