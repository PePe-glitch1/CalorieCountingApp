package com.example.caloriecountingapp.screenElements.homeScreanElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProgressBar(
    currentCalories: Int = 1721,
    maxCalories: Int = 2213
) {
    val progress = if (maxCalories > 0) {
        (currentCalories.toFloat() / maxCalories.toFloat()).coerceIn(0f, 1f)
    } else 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        Box(
            modifier = Modifier.requiredSize(224.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val radius = size.minDimension / 2

                val endAngle = 180f + (180f * progress)
                val angleInRadians = Math.toRadians(endAngle.toDouble())
                val dotX = centerX + radius * cos(angleInRadians).toFloat()
                val dotY = centerY + radius * sin(angleInRadians).toFloat()

                drawArc(
                    color = Color(0xFFF4D7CF),
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = 80f, cap = StrokeCap.Round),
                )

                drawArc(
                    color = Color(0xFFF47551),
                    startAngle = 180f,
                    sweepAngle = 180f * progress,
                    useCenter = false,
                    style = Stroke(width = 80f, cap = StrokeCap.Round)
                )

                drawCircle(
                    color = Color.White,
                    radius = 25f,
                    center = Offset(dotX, dotY)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$currentCalories Kcal",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "of $maxCalories kcal",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    ProgressBar()
}