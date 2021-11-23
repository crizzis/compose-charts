package me.bytebeats.views.charts.bar.render.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.bytebeats.views.charts.LabelFormatter
import me.bytebeats.views.charts.toLegacyInt
import kotlin.math.roundToInt

/**
 * Created by bytebeats on 2021/9/25 : 14:27
 * E-mail: happychinapc@gmail.com
 * Quote: Peasant. Educated. Worker
 */

data class SimpleHorizontalXAxisDrawer(
    private val labelTextSize: TextUnit = 12.sp,
    private val labelTextColor: Color = Color.Black,
    private val drawLabelEvery: Int = 3,
    private val labelValueFormatter: LabelFormatter = { value ->
        "%.1f".format(value)
    },
    private val axisLineThickness: Dp = 1.dp,
    private val axisLineColor: Color = Color.Black
) : IYAxisDrawer {

    private val mAxisLinePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = axisLineColor
            style = PaintingStyle.Stroke
        }
    }

    private val mTextPaint by lazy {
        android.graphics.Paint().apply {
            isAntiAlias = true
            color = labelTextColor.toLegacyInt()
        }
    }

    private val mTextBounds = android.graphics.Rect()

    override fun drawAxisLine(drawScope: DrawScope, canvas: Canvas, drawableArea: Rect) {
        with(drawScope) {
            val lineThickness = axisLineThickness.toPx()
            val y = drawableArea.bottom - lineThickness / 2F
            canvas.drawLine(
                p1 = Offset(x = drawableArea.left, y = y),
                p2 = Offset(x = drawableArea.right, y = y),
                paint = mAxisLinePaint.apply { strokeWidth = lineThickness }
            )
        }
    }

    override fun drawAxisLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        minValue: Float,
        maxValue: Float
    ) {
        with(drawScope) {
            val labelPaint = mTextPaint.apply {
                textSize = labelTextSize.toPx()
                textAlign = android.graphics.Paint.Align.RIGHT
            }
            val minLabelWidth = labelTextSize.toPx() * drawLabelEvery.toFloat()
            val totalWidth = drawableArea.width
            val labelCount = (drawableArea.width / minLabelWidth).roundToInt().coerceAtLeast(2)
            for (i in 0..labelCount) {
                val value = minValue + i * (maxValue - minValue) / labelCount
                val label = labelValueFormatter(value)
                val y = drawableArea.bottom - axisLineThickness.toPx() - labelTextSize.toPx() / 2F
                labelPaint.getTextBounds(label, 0, label.length, mTextBounds)
                val x =
                    drawableArea.right - i * (totalWidth / labelCount) + mTextBounds.height() / 2F
                canvas.nativeCanvas.drawText(label, x, y, labelPaint)
            }
        }
    }
}
