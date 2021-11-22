package me.bytebeats.views.charts.bar.render.label

import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Created by bytebeats on 2021/9/25 : 13:59
 * E-mail: happychinapc@gmail.com
 * Quote: Peasant. Educated. Worker
 */
interface IVerticalLabelDrawer: ILabelDrawer {
    fun requiredXAxisHeight(drawScope: DrawScope): Float = 0F
    fun requiredAboveBarHeight(drawScope: DrawScope): Float = 0F
}