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
    Image(imageVector = AddContactIcon, contentDescription = null)
}

private var _vector: ImageVector? = null

public val AddContactIcon: ImageVector
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
                    moveTo(14f, 10.5752f)
                    curveTo(15.9248f, 10.5752f, 17.542f, 8.8701f, 17.542f, 6.6641f)
                    curveTo(17.542f, 4.5107f, 15.9072f, 2.8584f, 14f, 2.8584f)
                    curveTo(12.084f, 2.8584f, 10.4492f, 4.5371f, 10.4492f, 6.6816f)
                    curveTo(10.458f, 8.8701f, 12.0752f, 10.5752f, 14f, 10.5752f)
                    close()
                    moveTo(13.9912f, 12.1309f)
                    curveTo(12.5586f, 12.1309f, 11.293f, 12.4561f, 10.2471f, 12.957f)
                    curveTo(11.5039f, 14.0205f, 12.3125f, 15.6025f, 12.3125f, 17.3604f)
                    curveTo(12.3125f, 17.835f, 12.251f, 18.3096f, 12.1279f, 18.749f)
                    horizontalLineTo(19.4756f)
                    curveTo(20.917f, 18.749f, 21.418f, 18.3096f, 21.418f, 17.501f)
                    curveTo(21.418f, 15.2422f, 18.5527f, 12.1309f, 13.9912f, 12.1309f)
                    close()
                    moveTo(6.5293f, 21.8955f)
                    curveTo(8.9902f, 21.8955f, 11.0645f, 19.8389f, 11.0645f, 17.3604f)
                    curveTo(11.0645f, 14.8818f, 9.0166f, 12.834f, 6.5293f, 12.834f)
                    curveTo(4.0508f, 12.834f, 2.0029f, 14.8818f, 2.0029f, 17.3604f)
                    curveTo(2.0029f, 19.8477f, 4.0508f, 21.8955f, 6.5293f, 21.8955f)
                    close()
                    moveTo(3.66406f, 17.3604f)
                    curveTo(3.6553f, 17f, 3.9014f, 16.7627f, 4.2617f, 16.7627f)
                    horizontalLineTo(5.93164f)
                    verticalLineTo(15.1016f)
                    curveTo(5.9316f, 14.7412f, 6.1689f, 14.4951f, 6.5293f, 14.4951f)
                    curveTo(6.8984f, 14.4951f, 7.1357f, 14.7412f, 7.1357f, 15.1016f)
                    verticalLineTo(16.7627f)
                    horizontalLineTo(8.79688f)
                    curveTo(9.1572f, 16.7627f, 9.4033f, 17f, 9.4033f, 17.3604f)
                    curveTo(9.4033f, 17.7295f, 9.1572f, 17.9668f, 8.7969f, 17.9668f)
                    horizontalLineTo(7.13574f)
                    verticalLineTo(19.6367f)
                    curveTo(7.1357f, 19.9971f, 6.8984f, 20.2344f, 6.5293f, 20.2344f)
                    curveTo(6.1689f, 20.2344f, 5.9316f, 19.9971f, 5.9316f, 19.6367f)
                    verticalLineTo(17.9668f)
                    horizontalLineTo(4.26172f)
                    curveTo(3.9102f, 17.9668f, 3.6641f, 17.7295f, 3.6641f, 17.3604f)
                    close()
                }
            }
        }.build()
        return _vector!!
    }

