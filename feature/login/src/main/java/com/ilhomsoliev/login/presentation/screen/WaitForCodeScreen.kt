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
fun WaitForCodeScreen(
    isLoading: Boolean,
    error: String?,
    onEnter: (String) -> Unit
) {
    val codeNumber = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEnter(codeNumber.value.text)
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
                Text(text = "Code Screen")
                if (error?.isNotEmpty() == true) {
                    Text(text = error)
                }
                TextField(
                    value = codeNumber.value,
                    onValueChange = { codeNumber.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    //textStyle = MaterialTheme.typography.h5
                )
            }
        }
    )
}