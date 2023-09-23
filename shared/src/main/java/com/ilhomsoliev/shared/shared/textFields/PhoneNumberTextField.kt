package com.ilhomsoliev.shared.shared.textFields

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    code: String,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
) {
    // TODO Apply spaces in textField

    val separator = "|"
    val textFieldValue = "$code $separator $phoneNumber"

    OutlinedTextField(
        modifier = modifier,
        value = textFieldValue,
        onValueChange = {
            if (it.contains(separator)) {
                onPhoneNumberChange(it.substringAfter("$separator "))
            }
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
}