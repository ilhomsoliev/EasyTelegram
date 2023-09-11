package com.ilhomsoliev.shared.shared.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
@Preview
private fun ImagePreview() {
    Image(imageVector = PaperclipIcon, contentDescription = null)
}

private var _vector: ImageVector? = null

val PaperclipIcon: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = ImageVector.Builder(
            name = "vector",
            defaultWidth = 18.dp,
            defaultHeight = 20.dp,
            viewportWidth = 18f,
            viewportHeight = 20f
        ).apply {
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
                moveTo(14.9065f, 10.357f)
                lineTo(8.41138f, 16.8521f)
                curveTo(6.7678f, 18.5044f, 4.553f, 18.3462f, 3.1379f, 16.9224f)
                curveTo(1.7053f, 15.4986f, 1.5471f, 13.3013f, 3.2083f, 11.6402f)
                lineTo(12.0852f, 2.76322f)
                curveTo(13.052f, 1.7964f, 14.4934f, 1.6294f, 15.4338f, 2.5699f)
                curveTo(16.3743f, 3.5191f, 16.1985f, 4.9517f, 15.2317f, 5.9097f)
                lineTo(6.52173f, 14.6372f)
                curveTo(6.135f, 15.0328f, 5.678f, 14.9185f, 5.3967f, 14.6548f)
                curveTo(5.1418f, 14.3824f, 5.0364f, 13.9253f, 5.4231f, 13.5298f)
                lineTo(11.4963f, 7.45658f)
                curveTo(11.804f, 7.1402f, 11.8215f, 6.6831f, 11.5227f, 6.3843f)
                curveTo(11.2239f, 6.1031f, 10.7668f, 6.1118f, 10.4592f, 6.4195f)
                lineTo(4.35962f, 12.5191f)
                curveTo(3.4104f, 13.4683f, 3.4456f, 14.9185f, 4.2893f, 15.7622f)
                curveTo(5.2034f, 16.6763f, 6.5832f, 16.6499f, 7.5325f, 15.7007f)
                lineTo(16.2864f, 6.93802f)
                curveTo(17.9915f, 5.2417f, 17.9299f, 3.0005f, 16.4358f, 1.5064f)
                curveTo(14.968f, 0.0474f, 12.6917f, -0.0581f, 10.9866f, 1.647f)
                lineTo(2.05689f, 10.5855f)
                curveTo(-0.1667f, 12.8179f, -0.0173f, 16.0171f, 2.0129f, 18.0474f)
                curveTo(4.0344f, 20.0601f, 7.2424f, 20.2183f, 9.4661f, 17.9947f)
                lineTo(16.0051f, 11.4644f)
                curveTo(16.304f, 11.1656f, 16.304f, 10.6206f, 15.9963f, 10.3394f)
                curveTo(15.7063f, 10.023f, 15.2141f, 10.0669f, 14.9065f, 10.357f)
                close()
            }
        }.build()
        return _vector!!
    }

