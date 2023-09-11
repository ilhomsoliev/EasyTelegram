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
    Image(imageVector = SearchIcon, contentDescription = null)
}

private var _vector: ImageVector? = null

public val SearchIcon: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = ImageVector.Builder(
            name = "vector",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF979797)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.EvenOdd
                ) {
                    moveTo(16.0166f, 14f)
                    horizontalLineTo(15.2512f)
                    lineTo(14.98f, 13.73f)
                    curveTo(15.9294f, 12.59f, 16.501f, 11.11f, 16.501f, 9.5f)
                    curveTo(16.501f, 5.91f, 13.6818f, 3f, 10.2038f, 3f)
                    curveTo(6.7257f, 3f, 3.9065f, 5.91f, 3.9065f, 9.5f)
                    curveTo(3.9065f, 13.09f, 6.7257f, 16f, 10.2038f, 16f)
                    curveTo(11.7635f, 16f, 13.1974f, 15.41f, 14.3018f, 14.43f)
                    lineTo(14.5634f, 14.71f)
                    verticalLineTo(15.5f)
                    lineTo(19.4074f, 20.49f)
                    lineTo(20.851f, 19f)
                    lineTo(16.0166f, 14f)
                    close()
                    moveTo(10.2038f, 14f)
                    curveTo(7.7914f, 14f, 5.8441f, 11.99f, 5.8441f, 9.5f)
                    curveTo(5.8441f, 7.01f, 7.7914f, 5f, 10.2038f, 5f)
                    curveTo(12.6161f, 5f, 14.5634f, 7.01f, 14.5634f, 9.5f)
                    curveTo(14.5634f, 11.99f, 12.6161f, 14f, 10.2038f, 14f)
                    close()
                }
            }
        }.build()
        return _vector!!
    }

