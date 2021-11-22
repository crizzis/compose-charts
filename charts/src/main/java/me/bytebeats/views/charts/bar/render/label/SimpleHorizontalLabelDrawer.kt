package me.bytebeats.views.charts.bar.render.label

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import me.bytebeats.views.charts.toLegacyInt

/**
 * Created by bytebeats on 2021/11/22 : 21:02
 * E-mail: happychinapc@gmail.com
 * Quote: Peasant. Educated. Worker
 */
data class SimpleHorizontalLabelDrawer(
    val drawLocation: ILabelDrawer.DrawLocation = ILabelDrawer.DrawLocation.Inside,
    val labelTextSize: TextUnit = 12.sp,
    val labelTextColor: Color = Color.Black
) : IHorizontalLabelDrawer {
    private val mLabelTextArea: Float? = null
    private val mPaint by lazy {
        android.graphics.Paint().apply {
            textAlign = android.graphics.Paint.Align.CENTER
            color = labelTextColor.toLegacyInt()
        }
    }

    override fun requiredAboveBarWidth(drawScope: DrawScope): Float = when (drawLocation) {
        ILabelDrawer.DrawLocation.Outside -> 3F / 2F * labelTextWidth(drawScope)
        else -> 0F
    }

    override fun requiredYAxisWidth(drawScope: DrawScope): Float = when (drawLocation) {
        ILabelDrawer.DrawLocation.YAxis -> labelTextWidth(drawScope)
        else -> 0F
    }

    override fun drawLabel(
        drawScope: DrawScope,
        canvas: Canvas,
        label: String,
        barArea: Rect,
        axisArea: Rect
    ) {
        with(drawScope) {
            val yCenter = barArea.top + barArea.height / 2
            val xCenter = when (drawLocation) {
                ILabelDrawer.DrawLocation.Inside -> (barArea.left + barArea.right) / 2
                ILabelDrawer.DrawLocation.Outside -> barArea.right - labelTextSize.toPx() / 2
                ILabelDrawer.DrawLocation.YAxis -> barArea.left - labelTextWidth(drawScope)
                else -> 0F
            }
            canvas.nativeCanvas.drawText(label, xCenter, yCenter, paint(drawScope))
        }
    }

    private fun labelTextWidth(drawScope: DrawScope): Float = with(drawScope) {
        mLabelTextArea ?: (1.5F * labelTextSize.toPx())
    }

    private fun paint(drawScope: DrawScope): android.graphics.Paint = with(drawScope) {
        mPaint.apply { textSize = labelTextSize.toPx() }
    }
}