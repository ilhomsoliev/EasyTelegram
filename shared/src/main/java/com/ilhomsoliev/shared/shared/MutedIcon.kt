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

public val MutedIcon: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = ImageVector.Builder(
            name = "vector",
            defaultWidth = 14.dp,
            defaultHeight = 15.dp,
            viewportWidth = 14f,
            viewportHeight = 15f
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
                    moveTo(2.01037f, 1.77563f)
                    curveTo(2.2054f, 1.6053f, 2.5015f, 1.6253f, 2.6718f, 1.8203f)
                    lineTo(5.3705f, 4.91005f)
                    lineTo(8.4132f, 2.21712f)
                    curveTo(8.5169f, 2.1253f, 8.6648f, 2.1031f, 8.7909f, 2.1593f)
                    curveTo(8.9176f, 2.2161f, 8.9986f, 2.3412f, 8.9986f, 2.4792f)
                    verticalLineTo(9.06378f)
                    lineTo(12.0468f, 12.5536f)
                    curveTo(12.2171f, 12.7486f, 12.1971f, 13.0447f, 12.0021f, 13.215f)
                    curveTo(11.8141f, 13.3792f, 11.5322f, 13.3665f, 11.3596f, 13.1908f)
                    lineTo(2.02016f, 2.49816f)
                    lineTo(2.01954f, 2.49869f)
                    lineTo(1.96569f, 2.43703f)
                    curveTo(1.7954f, 2.242f, 1.8154f, 1.9459f, 2.0104f, 1.7756f)
                    close()
                    moveTo(2.96891f, 5.00949f)
                    curveTo(2.5877f, 5.1679f, 2.319f, 5.5429f, 2.319f, 5.9791f)
                    lineTo(2.31873f, 9.47907f)
                    curveTo(2.3187f, 10.0579f, 2.792f, 10.5291f, 3.3735f, 10.5291f)
                    horizontalLineTo(5.34905f)
                    lineTo(8.41321f, 13.2408f)
                    curveTo(8.5123f, 13.3287f, 8.6582f, 13.3565f, 8.7911f, 13.2982f)
                    curveTo(8.9176f, 13.242f, 8.9986f, 13.1172f, 8.9986f, 12.9791f)
                    verticalLineTo(11.9128f)
                    lineTo(2.96891f, 5.00949f)
                    close()
                    moveTo(11.1325f, 5.25425f)
                    curveTo(10.995f, 5.1173f, 10.7733f, 5.1173f, 10.6357f, 5.2542f)
                    curveTo(10.4979f, 5.3912f, 10.4979f, 5.6127f, 10.6357f, 5.7491f)
                    curveTo(11.1669f, 6.2779f, 11.4594f, 6.981f, 11.4594f, 7.7291f)
                    curveTo(11.4594f, 8.4772f, 11.1669f, 9.1803f, 10.6357f, 9.7091f)
                    curveTo(10.4979f, 9.8456f, 10.4979f, 10.0668f, 10.6357f, 10.2037f)
                    curveTo(10.7733f, 10.3412f, 10.995f, 10.3412f, 11.1325f, 10.2037f)
                    curveTo(11.7969f, 9.5428f, 12.1625f, 8.6639f, 12.1625f, 7.7291f)
                    curveTo(12.1625f, 6.7943f, 11.7968f, 5.9154f, 11.1325f, 5.2542f)
                    close()
                }
            }
        }.build()
        return _vector!!
    }

