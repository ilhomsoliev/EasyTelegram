package com.ilhomsoliev.login.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitForNumberScreen(
    isLoading:Boolean,
    onNumberEnter: (String) -> Unit,
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
                    .padding(it)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Phone Number Screen")

                TextField(
                    value = phoneNumber.value,
                    onValueChange = { phoneNumber.value = it },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    )
}