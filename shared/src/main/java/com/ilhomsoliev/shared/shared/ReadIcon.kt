package com.ilhomsoliev.shared.shared

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


private var _vector: ImageVector? = null

val ReadIcon: ImageVector
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
                    moveTo(2f, 9.5f)
                    lineTo(4.5f, 11.5f)
                    lineTo(9.5f, 5.5f)
                }
                path(
                    fill = SolidColor(Color(0xFF007EEC)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(6.95f, 9.4f)
                    lineTo(6.35f, 8.95f)
                    lineTo(5.45f, 10.15f)
                    lineTo(6.05f, 10.6f)
                    lineTo(6.95f, 9.4f)
                    close()
                    moveTo(8.5f, 11.5f)
                    lineTo(8.05f, 12.1f)
                    curveTo(8.3694f, 12.3396f, 8.8206f, 12.2869f, 9.0762f, 11.9801f)
                    lineTo(8.5f, 11.5f)
                    close()
                    moveTo(14.0762f, 5.98014f)
                    curveTo(14.3413f, 5.6619f, 14.2983f, 5.189f, 13.9801f, 4.9238f)
                    curveTo(13.6619f, 4.6587f, 13.189f, 4.7016f, 12.9238f, 5.0199f)
                    lineTo(14.0762f, 5.98014f)
                    close()
                    moveTo(6.05f, 10.6f)
                    lineTo(8.05f, 12.1f)
                    lineTo(8.95f, 10.9f)
                    lineTo(6.95f, 9.4f)
                    lineTo(6.05f, 10.6f)
                    close()
                    moveTo(9.07617f, 11.9801f)
                    lineTo(14.0762f, 5.98014f)
                    lineTo(12.9238f, 5.01986f)
                    lineTo(7.92383f, 11.0199f)
                    lineTo(9.07617f, 11.9801f)
                    close()
                }
            }
        }.build()
        return _vector!!
    }

