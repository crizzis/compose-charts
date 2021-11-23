package me.bytebeats.views.charts.bar.render.axis

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Created by bytebeats on 2021/9/25 : 14:16
 * E-mail: happychinapc@gmail.com
 * Quote: Peasant. Educated. Worker
 */
interface IXAxisDrawer {
    fun requiredSize(drawScope: DrawScope): Float
    fun drawAxisLine(drawScope: DrawScope, canvas: Canvas, drawableArea: Rect)
}