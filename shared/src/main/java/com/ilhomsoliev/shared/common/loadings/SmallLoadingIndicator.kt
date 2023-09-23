package com.ilhomsoliev.shared.common.loadings

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SmallLoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(16.dp),
        color = Color(0x00000099),
        strokeWidth = 3.dp
    )
}