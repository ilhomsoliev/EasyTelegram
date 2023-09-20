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
    Image(imageVector = CleanUpIcon, contentDescription = null)
}

private var _vector: ImageVector? = null

public val CleanUpIcon: ImageVector
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
                    fill = SolidColor(Color(0xFF232323)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(10.9998f, 11f)
                    horizontalLineTo(12.9998f)
                    verticalLineTo(4f)
                    curveTo(12.9998f, 3.7167f, 12.904f, 3.4792f, 12.7123f, 3.2875f)
                    curveTo(12.5206f, 3.0958f, 12.2831f, 3f, 11.9998f, 3f)
                    curveTo(11.7165f, 3f, 11.479f, 3.0958f, 11.2873f, 3.2875f)
                    curveTo(11.0956f, 3.4792f, 10.9998f, 3.7167f, 10.9998f, 4f)
                    verticalLineTo(11f)
                    close()
                    moveTo(4.9998f, 15f)
                    horizontalLineTo(18.9998f)
                    verticalLineTo(13f)
                    horizontalLineTo(4.9998f)
                    verticalLineTo(15f)
                    close()
                    moveTo(3.5498f, 21f)
                    horizontalLineTo(5.9998f)
                    verticalLineTo(19f)
                    curveTo(5.9998f, 18.7167f, 6.0956f, 18.4792f, 6.2873f, 18.2875f)
                    curveTo(6.479f, 18.0958f, 6.7165f, 18f, 6.9998f, 18f)
                    curveTo(7.2831f, 18f, 7.5206f, 18.0958f, 7.7123f, 18.2875f)
                    curveTo(7.904f, 18.4792f, 7.9998f, 18.7167f, 7.9998f, 19f)
                    verticalLineTo(21f)
                    horizontalLineTo(10.9998f)
                    verticalLineTo(19f)
                    curveTo(10.9998f, 18.7167f, 11.0956f, 18.4792f, 11.2873f, 18.2875f)
                    curveTo(11.479f, 18.0958f, 11.7165f, 18f, 11.9998f, 18f)
                    curveTo(12.2831f, 18f, 12.5206f, 18.0958f, 12.7123f, 18.2875f)
                    curveTo(12.904f, 18.4792f, 12.9998f, 18.7167f, 12.9998f, 19f)
                    verticalLineTo(21f)
                    horizontalLineTo(15.9998f)
                    verticalLineTo(19f)
                    curveTo(15.9998f, 18.7167f, 16.0956f, 18.4792f, 16.2873f, 18.2875f)
                    curveTo(16.479f, 18.0958f, 16.7165f, 18f, 16.9998f, 18f)
                    curveTo(17.2831f, 18f, 17.5206f, 18.0958f, 17.7123f, 18.2875f)
                    curveTo(17.904f, 18.4792f, 17.9998f, 18.7167f, 17.9998f, 19f)
                    verticalLineTo(21f)
                    horizontalLineTo(20.4498f)
                    lineTo(19.4498f, 17f)
                    horizontalLineTo(4.5498f)
                    lineTo(3.5498f, 21f)
                    close()
                    moveTo(20.4498f, 23f)
                    horizontalLineTo(3.5498f)
                    curveTo(2.8998f, 23f, 2.3748f, 22.7417f, 1.9748f, 22.225f)
                    curveTo(1.5748f, 21.7083f, 1.4581f, 21.1333f, 1.6248f, 20.5f)
                    lineTo(2.9998f, 15f)
                    verticalLineTo(13f)
                    curveTo(2.9998f, 12.45f, 3.1956f, 11.9792f, 3.5873f, 11.5875f)
                    curveTo(3.979f, 11.1958f, 4.4498f, 11f, 4.9998f, 11f)
                    horizontalLineTo(8.9998f)
                    verticalLineTo(4f)
                    curveTo(8.9998f, 3.1667f, 9.2915f, 2.4583f, 9.8748f, 1.875f)
                    curveTo(10.4581f, 1.2917f, 11.1665f, 1f, 11.9998f, 1f)
                    curveTo(12.8331f, 1f, 13.5415f, 1.2917f, 14.1248f, 1.875f)
                    curveTo(14.7081f, 2.4583f, 14.9998f, 3.1667f, 14.9998f, 4f)
                    verticalLineTo(11f)
                    horizontalLineTo(18.9998f)
                    curveTo(19.5498f, 11f, 20.0206f, 11.1958f, 20.4123f, 11.5875f)
                    curveTo(20.804f, 11.9792f, 20.9998f, 12.45f, 20.9998f, 13f)
                    verticalLineTo(15f)
                    lineTo(22.3748f, 20.5f)
                    curveTo(22.5915f, 21.1333f, 22.4956f, 21.7083f, 22.0873f, 22.225f)
                    curveTo(21.679f, 22.7417f, 21.1331f, 23f, 20.4498f, 23f)
                    close()
                }
            }
        }.build()
        return _vector!!
    }