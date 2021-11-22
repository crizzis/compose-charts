package me.bytebeats.views.charts.bar.render.label

import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Created by bytebeats on 2021/11/22 : 21:00
 * E-mail: happychinapc@gmail.com
 * Quote: Peasant. Educated. Worker
 */
interface IHorizontalLabelDrawer : ILabelDrawer {
    fun requiredYAxisWidth(drawScope: DrawScope): Float = 0F
    fun requiredAboveBarWidth(drawScope: DrawScope): Float = 0F
}