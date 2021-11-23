package me.bytebeats.views.charts.bar.render.label

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Created by bytebeats on 2021/11/22 : 21:07
 * E-mail: happychinapc@gmail.com
 * Quote: Peasant. Educated. Worker
 */
interface ILabelDrawer {
    fun drawLabel(
        drawScope: DrawScope,
        canvas: Canvas,
        label: String,
        barArea: Rect,
        axisArea: Rect
    )

    enum class DrawLocation {
        Inside, Outside, Axis;
    }
}