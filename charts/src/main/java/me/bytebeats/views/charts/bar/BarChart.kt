package me.bytebeats.views.charts.bar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import me.bytebeats.views.charts.bar.render.axis.IXAxisDrawer
import me.bytebeats.views.charts.bar.render.axis.IYAxisDrawer
import me.bytebeats.views.charts.bar.render.axis.SimpleVerticalXAxisDrawer
import me.bytebeats.views.charts.bar.render.axis.SimpleVerticalYAxisDrawer
import me.bytebeats.views.charts.bar.render.bar.IBarDrawer
import me.bytebeats.views.charts.bar.render.bar.SimpleBarDrawer
import me.bytebeats.views.charts.bar.render.label.IVerticalLabelDrawer
import me.bytebeats.views.charts.bar.render.label.SimpleVerticalLabelDrawer
import me.bytebeats.views.charts.simpleChartAnimation

/**
 * Created by bytebeats on 2021/9/25 : 15:56
 * E-mail: happychinapc@gmail.com
 * Quote: Peasant. Educated. Worker
 */

@Composable
fun BarChart(
    barChartData: BarChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    barDrawer: IBarDrawer = SimpleBarDrawer(),
    xAxisDrawer: IXAxisDrawer = SimpleVerticalXAxisDrawer(),
    yAxisDrawer: IYAxisDrawer = SimpleVerticalYAxisDrawer(),
    labelDrawer: IVerticalLabelDrawer = SimpleVerticalLabelDrawer()
) {
    val transitionAnimation = remember(barChartData.bars) { Animatable(initialValue = 0F) }

    LaunchedEffect(barChartData.bars) {
        transitionAnimation.animateTo(1F, animationSpec = animation)
    }

    val progress = transitionAnimation.value
    Canvas(modifier = modifier
        .fillMaxSize()
        .drawBehind {
            drawIntoCanvas { canvas ->
                val (xAxisArea, yAxisArea) = axisAreas(
                    drawScope = this,
                    totalSize = size,
                    xAxisDrawer = xAxisDrawer,
                    labelDrawer = labelDrawer
                )

                val barDrawableArea = barDrawableArea(xAxisArea)

                yAxisDrawer.drawAxisLine(
                    drawScope = this,
                    canvas = canvas,
                    drawableArea = yAxisArea
                )

                xAxisDrawer.drawAxisLine(
                    drawScope = this,
                    canvas = canvas,
                    drawableArea = xAxisArea
                )

                barChartData.forEachWithArea(
                    this,
                    barDrawableArea,
                    progress,
                    labelDrawer
                ) { barArea, bar ->
                    barDrawer.drawBar(drawScope = this, canvas, barArea, bar)
                }
            }
        }) {

        drawIntoCanvas { canvas ->
            val (xAxisArea, yAxisArea) = axisAreas(
                drawScope = this,
                totalSize = size,
                xAxisDrawer = xAxisDrawer,
                labelDrawer = labelDrawer
            )
            val barDrawableArea = barDrawableArea(xAxisArea)

            barChartData.forEachWithArea(
                this,
                barDrawableArea,
                progress,
                labelDrawer
            ) { barArea, bar ->
                labelDrawer.drawLabel(
                    drawScope = this,
                    canvas = canvas,
                    label = bar.label,
                    barArea = barArea,
                    axisArea = xAxisArea
                )
            }

            yAxisDrawer.drawAxisLabels(
                drawScope = this,
                canvas = canvas,
                minValue = barChartData.min,
                maxValue = barChartData.max,
                drawableArea = yAxisArea
            )
        }
    }
}