package com.ilhomsoliev.shared.shared

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
private fun ImagePreview() {
    Image(imageVector = UnreadIcon, contentDescription = null)
}

private var _vector: ImageVector? = null

val UnreadIcon: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = ImageVector.Builder(
            name = "vector",
            defaultWidth = 16.dp,
            defaultHeight = 17.dp,
            viewportWidth = 16f,
            viewportHeight = 17f
        ).apply {
            group {
                path(
                    fill = null,
                    fillAlpha = 1.0f,
                    stroke = SolidColor(Color(0xFF007EEC)),
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(4.49991f, 9.5f)
                    lineTo(6.99991f, 11.5f)
                    lineTo(11.9999f, 5.5f)
                }
            }
        }.build()
        return _vector!!
    }
