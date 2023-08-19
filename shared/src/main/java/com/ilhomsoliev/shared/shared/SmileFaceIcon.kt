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

val SmileFaceIcon: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = ImageVector.Builder(
            name = "vector",
            defaultWidth = 28.dp,
            defaultHeight = 28.dp,
            viewportWidth = 28f,
            viewportHeight = 28f
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
                    moveTo(13.9912f, 22.7422f)
                    curveTo(18.9746f, 22.7422f, 23.0879f, 18.6289f, 23.0879f, 13.6543f)
                    curveTo(23.0879f, 8.6797f, 18.9658f, 4.5664f, 13.9824f, 4.5664f)
                    curveTo(9.0078f, 4.5664f, 4.9033f, 8.6797f, 4.9033f, 13.6543f)
                    curveTo(4.9033f, 18.6289f, 9.0166f, 22.7422f, 13.9912f, 22.7422f)
                    close()
                    moveTo(13.9912f, 20.9316f)
                    curveTo(9.957f, 20.9316f, 6.7314f, 17.6885f, 6.7314f, 13.6543f)
                    curveTo(6.7314f, 9.6201f, 9.957f, 6.3857f, 13.9824f, 6.3857f)
                    curveTo(18.0166f, 6.3857f, 21.2598f, 9.6201f, 21.2686f, 13.6543f)
                    curveTo(21.2773f, 17.6885f, 18.0254f, 20.9316f, 13.9912f, 20.9316f)
                    close()
                    moveTo(11.4424f, 12.8457f)
                    curveTo(11.9609f, 12.8457f, 12.4004f, 12.3887f, 12.4004f, 11.7471f)
                    curveTo(12.4004f, 11.1055f, 11.9609f, 10.6484f, 11.4424f, 10.6484f)
                    curveTo(10.9326f, 10.6484f, 10.502f, 11.1055f, 10.502f, 11.7471f)
                    curveTo(10.502f, 12.3887f, 10.9326f, 12.8457f, 11.4424f, 12.8457f)
                    close()
                    moveTo(16.5664f, 12.8457f)
                    curveTo(17.085f, 12.8457f, 17.5244f, 12.3887f, 17.5244f, 11.7471f)
                    curveTo(17.5244f, 11.1055f, 17.085f, 10.6484f, 16.5664f, 10.6484f)
                    curveTo(16.0479f, 10.6484f, 15.626f, 11.1055f, 15.626f, 11.7471f)
                    curveTo(15.626f, 12.3887f, 16.0479f, 12.8457f, 16.5664f, 12.8457f)
                    close()
                    moveTo(10.9062f, 16.1592f)
                    curveTo(10.9062f, 16.748f, 12.1455f, 17.9697f, 13.9824f, 17.9697f)
                    curveTo(15.8281f, 17.9697f, 17.0674f, 16.748f, 17.0674f, 16.1592f)
                    curveTo(17.0674f, 15.9482f, 16.8564f, 15.8428f, 16.6631f, 15.9395f)
                    curveTo(16.0215f, 16.2734f, 15.2656f, 16.6689f, 13.9824f, 16.6689f)
                    curveTo(12.708f, 16.6689f, 11.9521f, 16.2734f, 11.3105f, 15.9395f)
                    curveTo(11.1172f, 15.8428f, 10.9062f, 15.9482f, 10.9062f, 16.1592f)
                    close()
                }
            }
        }.build()
        return _vector!!
    }

