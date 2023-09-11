package com.ilhomsoliev.shared.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.shared.shared.icons.CancelIcon
import com.ilhomsoliev.shared.shared.icons.SearchIcon

@Composable
fun CustomSearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValue: (String) -> Unit,
    onCancelClick: () -> Unit,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            onValue(newValue)
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0x1F767680)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .weight(1f, false),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(1.dp),
                    imageVector = SearchIcon,
                    contentDescription = ""
                )
                it()
            }
            if (value.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { onCancelClick() },
                    imageVector = CancelIcon,
                    contentDescription = "null"
                )
            }
        }
    }
}