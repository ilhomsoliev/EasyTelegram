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

val PinnedIcon: ImageVector
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
                    stroke = SolidColor(Color(0xFF979797)),
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 0.712144f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.EvenOdd
                ) {
                    moveTo(13.4072f, 5.31398f)
                    curveTo(13.469f, 5.3759f, 13.5f, 5.4535f, 13.5f, 5.531f)
                    curveTo(13.5f, 5.6086f, 13.469f, 5.686f, 13.3918f, 5.7325f)
                    lineTo(12.2938f, 6.83337f)
                    curveTo(12.232f, 6.8953f, 12.1547f, 6.9264f, 12.0773f, 6.9264f)
                    curveTo(12f, 6.9264f, 11.9227f, 6.8953f, 11.8608f, 6.8334f)
                    lineTo(11.3196f, 6.29068f)
                    lineTo(8.89168f, 8.72485f)
                    lineTo(8.79892f, 10.9108f)
                    curveTo(8.7989f, 10.9884f, 8.768f, 11.0504f, 8.7061f, 11.1124f)
                    lineTo(7.93296f, 11.8876f)
                    curveTo(7.8712f, 11.9496f, 7.7938f, 11.9806f, 7.7165f, 11.9806f)
                    curveTo(7.6391f, 11.9806f, 7.5618f, 11.9496f, 7.5f, 11.8876f)
                    lineTo(5.53605f, 9.9031f)
                    lineTo(2.02577f, 13.407f)
                    curveTo(1.964f, 13.469f, 1.8866f, 13.5f, 1.8093f, 13.5f)
                    curveTo(1.7319f, 13.5f, 1.6546f, 13.469f, 1.5928f, 13.407f)
                    curveTo(1.4691f, 13.2829f, 1.4691f, 13.0969f, 1.5928f, 12.9729f)
                    lineTo(5.08761f, 9.45349f)
                    lineTo(3.12368f, 7.48446f)
                    curveTo(3.0619f, 7.4225f, 3.0309f, 7.3449f, 3.0309f, 7.2674f)
                    curveTo(3.0309f, 7.1899f, 3.0619f, 7.1123f, 3.1237f, 7.0504f)
                    lineTo(3.89687f, 6.29064f)
                    curveTo(3.9432f, 6.2287f, 4.0206f, 6.1976f, 4.0979f, 6.1976f)
                    lineTo(6.27836f, 6.10463f)
                    lineTo(8.70623f, 3.67067f)
                    lineTo(8.16495f, 3.12798f)
                    curveTo(8.0412f, 3.0039f, 8.0412f, 2.8179f, 8.1649f, 2.6939f)
                    lineTo(9.26293f, 1.59303f)
                    curveTo(9.3711f, 1.469f, 9.5722f, 1.469f, 9.6959f, 1.593f)
                    lineTo(13.4072f, 5.31398f)
                    close()
                    moveTo(11.5515f, 5.6395f)
                    lineTo(12.0928f, 6.1822f)
                    lineTo(12.7421f, 5.54648f)
                    lineTo(12.686f, 5.49022f)
                    lineTo(12.088f, 6.08824f)
                    lineTo(11.3821f, 5.38235f)
                    lineTo(8.30023f, 8.37172f)
                    lineTo(8.29284f, 8.52277f)
                    curveTo(8.3031f, 8.4662f, 8.3327f, 8.4172f, 8.3815f, 8.3682f)
                    lineTo(11.1185f, 5.6395f)
                    curveTo(11.1803f, 5.5775f, 11.2576f, 5.5465f, 11.335f, 5.5465f)
                    curveTo(11.4123f, 5.5465f, 11.4897f, 5.5775f, 11.5515f, 5.6395f)
                    close()
                    moveTo(8.18097f, 10.7449f)
                    lineTo(7.96982f, 10.9671f)
                    lineTo(8.18042f, 10.7559f)
                    lineTo(8.18097f, 10.7449f)
                    close()
                    moveTo(7.73686f, 11.2006f)
                    lineTo(6.45015f, 9.95138f)
                    lineTo(7.71648f, 11.2211f)
                    lineTo(7.73686f, 11.2006f)
                    close()
                    moveTo(3.79926f, 7.29357f)
                    lineTo(4.07275f, 6.99825f)
                    lineTo(3.78873f, 7.28301f)
                    lineTo(3.79926f, 7.29357f)
                    close()
                    moveTo(6.39252f, 6.71054f)
                    horizontalLineTo(6.5115f)
                    lineTo(6.54064f, 6.68081f)
                    curveTo(6.5031f, 6.6998f, 6.4603f, 6.7093f, 6.4176f, 6.7093f)
                    lineTo(6.39252f, 6.71054f)
                    close()
                    moveTo(8.81434f, 2.91081f)
                    lineTo(8.81898f, 2.91547f)
                    lineTo(9.50757f, 2.30351f)
                    lineTo(9.46382f, 2.25964f)
                    lineTo(8.81434f, 2.91081f)
                    close()
                }
            }
        }.build()
        return _vector!!
    }

