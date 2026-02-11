package com.example.caloriecountingapp.screenElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.abs

data class DayNutrition(
    val dayLabel: String,
    val calories: Float,
    val fat: Float,
    val carbs: Float,
    val protein: Float,
    val water: Float = 0f
)

@Composable
fun WeeklyCalorieChart(
    modifier: Modifier = Modifier,
    data: List<DayNutrition>,
    selectedDayIndex: Int = -1,
    onDaySelected: (Int) -> Unit = {}
) {
    val accentColor = Color(0xFFE07A5F)
    val lineColor = Color(0xFFE88B78)
    val gradientTop = Color(0x40E88B78)
    val gradientBottom = Color(0x05E88B78)
    val gridColor = Color(0xFFCCCCCC)

    // Water curve colors
    val waterLineColor = Color(0xFF5B9BD5)
    val waterGradientTop = Color(0x355B9BD5)
    val waterGradientBottom = Color(0x055B9BD5)

    val yLabels = listOf(0f, 1000f, 1500f, 2000f, 2500f)
    val maxY = 2500f

    var internalSelectedIndex by remember { mutableIntStateOf(selectedDayIndex) }

    LaunchedEffect(selectedDayIndex) {
        internalSelectedIndex = selectedDayIndex
    }

    // Якщо даних немає — показуємо порожній графік з мітками днів
    val displayData = data.ifEmpty {
        listOf(
            DayNutrition("S", 0f, 0f, 0f, 0f, 0f),
            DayNutrition("M", 0f, 0f, 0f, 0f, 0f),
            DayNutrition("T", 0f, 0f, 0f, 0f, 0f),
            DayNutrition("W", 0f, 0f, 0f, 0f, 0f),
            DayNutrition("T", 0f, 0f, 0f, 0f, 0f),
            DayNutrition("F", 0f, 0f, 0f, 0f, 0f),
            DayNutrition("S", 0f, 0f, 0f, 0f, 0f)
        )
    }

    val hasData = data.isNotEmpty() && data.any { it.calories > 0f || it.water > 0f }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(340.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(top = 16.dp, bottom = 8.dp)
            .pointerInput(displayData) {
                detectTapGestures { offset ->
                    val paddingLeft = size.width * 0.12f
                    val paddingRight = size.width * 0.05f
                    val chartWidth = size.width - paddingLeft - paddingRight
                    val step = chartWidth / (displayData.size - 1).coerceAtLeast(1)

                    var closest = 0
                    var minDist = Float.MAX_VALUE
                    for (i in displayData.indices) {
                        val x = paddingLeft + i * step
                        val dist = abs(offset.x - x)
                        if (dist < minDist) {
                            minDist = dist
                            closest = i
                        }
                    }
                    internalSelectedIndex = closest
                    onDaySelected(closest)
                }
            }
    ) {
        val paddingLeft = size.width * 0.12f
        val paddingRight = size.width * 0.05f
        val paddingTop = size.height * 0.22f
        val paddingBottom = size.height * 0.12f

        val chartWidth = size.width - paddingLeft - paddingRight
        val chartHeight = size.height - paddingTop - paddingBottom

        // Draw Y-axis labels and horizontal grid lines
        val textPaint = android.graphics.Paint().apply {
            color = 0xFF9E9E9E.toInt()
            textSize = 32f
            isAntiAlias = true
            textAlign = android.graphics.Paint.Align.RIGHT
        }

        yLabels.forEach { value ->
            val y = paddingTop + chartHeight * (1f - value / maxY)

            // Grid line
            drawLine(
                color = gridColor,
                start = Offset(paddingLeft, y),
                end = Offset(size.width - paddingRight, y),
                strokeWidth = 1f,
                pathEffect = if (value == 0f) null else PathEffect.dashPathEffect(
                    floatArrayOf(8f, 8f)
                )
            )

            // Y label
            drawContext.canvas.nativeCanvas.drawText(
                value.toInt().toString(),
                paddingLeft - 16f,
                y + 10f,
                textPaint
            )
        }

        // Calculate point positions
        val step = chartWidth / (displayData.size - 1).coerceAtLeast(1)
        val points = displayData.mapIndexed { index, day ->
            val x = paddingLeft + index * step
            val y = paddingTop + chartHeight * (1f - day.calories / maxY)
            Offset(x, y)
        }

        // Build smooth curve path using cubic bezier
        val curvePath = Path()
        val fillPath = Path()
        val hasCalories = displayData.any { it.calories > 0f }

        if (points.size >= 2 && hasCalories) {
            curvePath.moveTo(points[0].x, points[0].y)
            fillPath.moveTo(points[0].x, points[0].y)

            for (i in 0 until points.size - 1) {
                val p0 = if (i > 0) points[i - 1] else points[i]
                val p1 = points[i]
                val p2 = points[i + 1]
                val p3 = if (i < points.size - 2) points[i + 2] else points[i + 1]

                val tension = 0.3f
                val cp1x = p1.x + (p2.x - p0.x) * tension
                val cp1y = p1.y + (p2.y - p0.y) * tension
                val cp2x = p2.x - (p3.x - p1.x) * tension
                val cp2y = p2.y - (p3.y - p1.y) * tension

                curvePath.cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
                fillPath.cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
            }

            // Close the fill path
            val bottomY = paddingTop + chartHeight
            fillPath.lineTo(points.last().x, bottomY)
            fillPath.lineTo(points.first().x, bottomY)
            fillPath.close()

            // Draw gradient fill
            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(gradientTop, gradientBottom),
                    startY = paddingTop,
                    endY = paddingTop + chartHeight
                )
            )

            // Draw the calorie curve line
            drawPath(
                path = curvePath,
                color = lineColor,
                style = Stroke(width = 5f, cap = StrokeCap.Round)
            )
        }

        // Calculate water point positions
        val waterPoints = displayData.mapIndexed { index, day ->
            val x = paddingLeft + index * step
            val y = paddingTop + chartHeight * (1f - day.water / maxY)
            Offset(x, y)
        }

        // Build smooth water curve
        val waterCurvePath = Path()
        val waterFillPath = Path()
        val hasWater = displayData.any { it.water > 0f }

        if (waterPoints.size >= 2 && hasWater) {
            waterCurvePath.moveTo(waterPoints[0].x, waterPoints[0].y)
            waterFillPath.moveTo(waterPoints[0].x, waterPoints[0].y)

            for (i in 0 until waterPoints.size - 1) {
                val p0 = if (i > 0) waterPoints[i - 1] else waterPoints[i]
                val p1 = waterPoints[i]
                val p2 = waterPoints[i + 1]
                val p3 = if (i < waterPoints.size - 2) waterPoints[i + 2] else waterPoints[i + 1]

                val tension = 0.3f
                val cp1x = p1.x + (p2.x - p0.x) * tension
                val cp1y = p1.y + (p2.y - p0.y) * tension
                val cp2x = p2.x - (p3.x - p1.x) * tension
                val cp2y = p2.y - (p3.y - p1.y) * tension

                waterCurvePath.cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
                waterFillPath.cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
            }

            val bottomY = paddingTop + chartHeight
            waterFillPath.lineTo(waterPoints.last().x, bottomY)
            waterFillPath.lineTo(waterPoints.first().x, bottomY)
            waterFillPath.close()

            // Draw water gradient fill
            drawPath(
                path = waterFillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(waterGradientTop, waterGradientBottom),
                    startY = paddingTop,
                    endY = paddingTop + chartHeight
                )
            )

            // Draw water curve line
            drawPath(
                path = waterCurvePath,
                color = waterLineColor,
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )
        }

        // Draw X-axis day labels
        val dayTextPaint = android.graphics.Paint().apply {
            color = 0xFF9E9E9E.toInt()
            textSize = 36f
            isAntiAlias = true
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.BOLD
            )
        }

        val xAxisY = paddingTop + chartHeight + paddingBottom * 0.7f

        displayData.forEachIndexed { index, day ->
            val x = paddingLeft + index * step

            if (index == internalSelectedIndex) {
                // Draw highlighted circle for selected day
                drawCircle(
                    color = accentColor,
                    radius = 22f,
                    center = Offset(x, xAxisY - 4f)
                )
                // White text for selected day
                val selectedDayPaint = android.graphics.Paint().apply {
                    color = 0xFFFFFFFF.toInt()
                    textSize = 36f
                    isAntiAlias = true
                    textAlign = android.graphics.Paint.Align.CENTER
                    typeface = android.graphics.Typeface.create(
                        android.graphics.Typeface.DEFAULT,
                        android.graphics.Typeface.BOLD
                    )
                }
                drawContext.canvas.nativeCanvas.drawText(
                    day.dayLabel,
                    x,
                    xAxisY + 10f,
                    selectedDayPaint
                )
            } else {
                drawContext.canvas.nativeCanvas.drawText(
                    day.dayLabel,
                    x,
                    xAxisY + 10f,
                    dayTextPaint
                )
            }
        }

        // Draw selected point indicator and tooltip
        if (hasData && internalSelectedIndex in displayData.indices) {
            val selectedPoint = points[internalSelectedIndex]
            val selectedWaterPoint = waterPoints[internalSelectedIndex]
            val selectedData = displayData[internalSelectedIndex]

            // Vertical dashed line from calorie point to x-axis
            drawLine(
                color = gridColor,
                start = Offset(selectedPoint.x, selectedPoint.y),
                end = Offset(selectedPoint.x, paddingTop + chartHeight),
                strokeWidth = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f))
            )

            // Water point indicator
            drawCircle(
                color = Color.White,
                radius = 11f,
                center = selectedWaterPoint
            )
            drawCircle(
                color = waterLineColor,
                radius = 11f,
                center = selectedWaterPoint,
                style = Stroke(width = 3f)
            )
            drawCircle(
                color = Color.White,
                radius = 4f,
                center = selectedWaterPoint
            )

            // Calorie point indicator
            drawCircle(
                color = Color.White,
                radius = 14f,
                center = selectedPoint
            )
            drawCircle(
                color = lineColor,
                radius = 14f,
                center = selectedPoint,
                style = Stroke(width = 3f)
            )
            drawCircle(
                color = Color.White,
                radius = 6f,
                center = selectedPoint
            )

            // Draw tooltip
            drawTooltip(
                center = selectedPoint,
                fat = selectedData.fat,
                carbs = selectedData.carbs,
                protein = selectedData.protein,
                water = selectedData.water,
                accentColor = accentColor,
                waterColor = waterLineColor,
                chartLeft = paddingLeft,
                chartRight = size.width - paddingRight
            )
        }
    }
}

private fun DrawScope.drawTooltip(
    center: Offset,
    fat: Float,
    carbs: Float,
    protein: Float,
    water: Float,
    accentColor: Color,
    waterColor: Color,
    chartLeft: Float,
    chartRight: Float
) {
    val tooltipWidth = 280f
    val tooltipHeight = 200f
    val cornerRadius = 20f
    val arrowSize = 14f
    val tooltipGap = 24f

    // Position tooltip above the point
    var tooltipX = center.x - tooltipWidth / 2
    val tooltipY = center.y - tooltipHeight - tooltipGap - arrowSize

    // Clamp tooltip to chart bounds
    if (tooltipX < chartLeft) tooltipX = chartLeft
    if (tooltipX + tooltipWidth > chartRight) tooltipX = chartRight - tooltipWidth

    // Draw tooltip background with rounded corners
    val tooltipPath = Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(
                    offset = Offset(tooltipX, tooltipY),
                    size = Size(tooltipWidth, tooltipHeight)
                ),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        )
    }

    // Shadow
    drawPath(
        path = tooltipPath,
        color = Color(0x20000000),
        style = Fill
    )

    // Background
    drawPath(
        path = tooltipPath,
        color = accentColor,
        style = Fill
    )

    // Arrow pointing down
    val arrowPath = Path().apply {
        moveTo(center.x - arrowSize, tooltipY + tooltipHeight)
        lineTo(center.x, tooltipY + tooltipHeight + arrowSize)
        lineTo(center.x + arrowSize, tooltipY + tooltipHeight)
        close()
    }
    drawPath(
        path = arrowPath,
        color = accentColor,
        style = Fill
    )

    // Draw text inside tooltip
    val labelPaint = android.graphics.Paint().apply {
        color = 0xFFFFFFFF.toInt()
        textSize = 30f
        isAntiAlias = true
        textAlign = android.graphics.Paint.Align.LEFT
        typeface = android.graphics.Typeface.create(
            android.graphics.Typeface.DEFAULT,
            android.graphics.Typeface.NORMAL
        )
    }

    val valuePaint = android.graphics.Paint().apply {
        color = 0xFFFFFFFF.toInt()
        textSize = 30f
        isAntiAlias = true
        textAlign = android.graphics.Paint.Align.RIGHT
        typeface = android.graphics.Typeface.create(
            android.graphics.Typeface.DEFAULT,
            android.graphics.Typeface.BOLD
        )
    }

    val textX = tooltipX + 28f
    val valueX = tooltipX + tooltipWidth - 28f
    val lineHeight = 40f
    val firstLineY = tooltipY + 48f

    val items = listOf(
        Triple("Fat", "${fat.toInt()}g", accentColor),
        Triple("Carbs", "${carbs.toInt()}g", accentColor),
        Triple("Protein", "${protein.toInt()}g", accentColor),
        Triple("Water", "${water.toInt()}ml", waterColor)
    )

    items.forEachIndexed { index, (label, value, dotColor) ->
        val y = firstLineY + index * lineHeight

        // Color dot indicator
        drawCircle(
            color = Color.White,
            radius = 8f,
            center = Offset(textX - 4f, y - 8f)
        )
        drawCircle(
            color = dotColor,
            radius = 5f,
            center = Offset(textX - 4f, y - 8f)
        )

        drawContext.canvas.nativeCanvas.drawText(label, textX + 16f, y, labelPaint)
        drawContext.canvas.nativeCanvas.drawText(value, valueX, y, valuePaint)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5FF5F)
@Composable
fun WeeklyCalorieChartPreview() {
    val sampleData = listOf(
        DayNutrition("S", 1000f, 35f, 18f, 3f, 600f),
        DayNutrition("M", 1020f, 38f, 22f, 5f, 750f),
        DayNutrition("T", 1100f, 42f, 25f, 6f, 820f),
        DayNutrition("W", 1450f, 40f, 20f, 4f, 900f),
        DayNutrition("T", 1350f, 45f, 28f, 7f, 700f),
        DayNutrition("F", 1750f, 50f, 30f, 8f, 1100f),
        DayNutrition("S", 1600f, 48f, 26f, 6f, 950f)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF))
            .padding(start = 5.dp, end = 10.dp)
    ) {
        WeeklyCalorieChart(
            data = sampleData,
            selectedDayIndex = 3
        )
    }
}