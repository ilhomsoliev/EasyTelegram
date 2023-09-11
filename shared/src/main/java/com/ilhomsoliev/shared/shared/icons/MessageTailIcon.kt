package com.ilhomsoliev.shared.shared.icons

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
    Image(imageVector = MessageTailIcon, contentDescription = null)
}

private var _vector: ImageVector? = null

val MessageTailIcon: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = ImageVector.Builder(
            name = "vector",
            defaultWidth = 8.dp,
            defaultHeight = 19.dp,
            viewportWidth = 8f,
            viewportHeight = 19f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFE3FFCA)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.EvenOdd
                ) {
                    moveTo(1f, 0f)
                    curveTo(1f, 4f, 4f, 18f, 8f, 18f)
                    curveTo(6.7929f, 18.1207f, 2.6134f, 17.1224f, 0.4002f, 16.1068f)
                    curveTo(0.4532f, 16.0213f, 0.5031f, 15.9336f, 0.5497f, 15.8439f)
                    curveTo(1f, 14.9769f, 1f, 13.838f, 1f, 11.56f)
                    verticalLineTo(0f)
                    close()
                }
            }
        }.build()
        return _vector!!
    }

