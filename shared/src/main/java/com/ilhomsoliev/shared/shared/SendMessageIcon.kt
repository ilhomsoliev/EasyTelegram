package com.ilhomsoliev.shared.shared

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


private var _vector: ImageVector? = null

public val SendMessageIcon: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = ImageVector.Builder(
            name = "vector",
            defaultWidth = 32.dp,
            defaultHeight = 32.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
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
                moveTo(32f, 16f)
                arcTo(16f, 16f, 0f, isMoreThanHalf = false, isPositiveArc = true, 16f, 32f)
                arcTo(16f, 16f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 16f)
                arcTo(16f, 16f, 0f, isMoreThanHalf = false, isPositiveArc = true, 32f, 16f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(16.9523f, 27f)
                curveTo(17.6396f, 27f, 18.1265f, 26.4081f, 18.4797f, 25.4916f)
                lineTo(24.7327f, 9.15752f)
                curveTo(24.9045f, 8.7184f, 25f, 8.327f, 25f, 8.0024f)
                curveTo(25f, 7.3819f, 24.6181f, 7f, 23.9976f, 7f)
                curveTo(23.673f, 7f, 23.2816f, 7.0955f, 22.8425f, 7.2673f)
                lineTo(6.42243f, 13.5585f)
                curveTo(5.6205f, 13.864f, 5f, 14.3508f, 5f, 15.0477f)
                curveTo(5f, 15.926f, 5.6683f, 16.222f, 6.5847f, 16.4988f)
                lineTo(11.7399f, 18.0644f)
                curveTo(12.3508f, 18.2554f, 12.6945f, 18.2363f, 13.105f, 17.8544f)
                lineTo(23.5776f, 8.06921f)
                curveTo(23.7017f, 7.9546f, 23.8449f, 7.9737f, 23.9403f, 8.0597f)
                curveTo(24.0358f, 8.1551f, 24.0453f, 8.2983f, 23.9308f, 8.4224f)
                lineTo(14.1838f, 18.9332f)
                curveTo(13.8115f, 19.3246f, 13.7828f, 19.6492f, 13.9642f, 20.2888f)
                lineTo(15.4821f, 25.3294f)
                curveTo(15.7685f, 26.2936f, 16.0644f, 27f, 16.9523f, 27f)
                close()
            }
        }.build()
        return _vector!!
    }

