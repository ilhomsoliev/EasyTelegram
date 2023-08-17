package com.ilhomsoliev.shared.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    isSolid: Boolean = true,
    isActive: Boolean = true,
    onClick: () -> Unit,
) {
    val background = if (isSolid) Modifier.background(Color(0xFF007EEC)) else Modifier.border(
        2.dp,
        Color(0xFF007EEC),
        RoundedCornerShape(12.dp),
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(background)
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 14.dp),
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
                color = if (!isSolid) Color(0xFF007EEC) else Color.White
            )
        )
        if (!isActive)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xAAFFFFFF))
            )
    }
}