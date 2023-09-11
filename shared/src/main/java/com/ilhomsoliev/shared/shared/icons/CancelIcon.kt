package com.ilhomsoliev.shared.shared.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
private fun ImagePreview() {
    Image(imageVector = CancelIcon, contentDescription = null)
}

private var _vector: ImageVector? = null

public val CancelIcon: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = ImageVector.Builder(
            name = "vector",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
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
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(3.20921f, 3.20921f)
                    curveTo(3.4882f, 2.9303f, 3.9404f, 2.9303f, 4.2194f, 3.2092f)
                    lineTo(8f, 6.98985f)
                    lineTo(11.7806f, 3.20921f)
                    curveTo(12.0596f, 2.9303f, 12.5118f, 2.9303f, 12.7908f, 3.2092f)
                    curveTo(13.0697f, 3.4882f, 13.0697f, 3.9404f, 12.7908f, 4.2194f)
                    lineTo(9.01015f, 8f)
                    lineTo(12.7908f, 11.7806f)
                    curveTo(13.0697f, 12.0596f, 13.0697f, 12.5118f, 12.7908f, 12.7908f)
                    curveTo(12.5118f, 13.0697f, 12.0596f, 13.0697f, 11.7806f, 12.7908f)
                    lineTo(8f, 9.01015f)
                    lineTo(4.21936f, 12.7908f)
                    curveTo(3.9404f, 13.0697f, 3.4882f, 13.0697f, 3.2092f, 12.7908f)
                    curveTo(2.9303f, 12.5118f, 2.9303f, 12.0596f, 3.2092f, 11.7806f)
                    lineTo(6.98985f, 8f)
                    lineTo(3.20921f, 4.21936f)
                    curveTo(2.9303f, 3.9404f, 2.9303f, 3.4882f, 3.2092f, 3.2092f)
                    close()
                }
            }
        }.build()
        return _vector!!
    }

