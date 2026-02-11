package com.example.caloriecountingapp.screenElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin


@Preview
@Composable
fun ProgressBar() {

    val progresss = 2000f/2560f

    var progress by remember { mutableStateOf(progresss) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.height(224.dp).width(224.dp).background(Color.Transparent)
        ) {

            // Центр Canvas
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Радіус кола
            val radius = (size.minDimension / 2)

            val endAngle = 180f + (180f * progress)

            // Переводимо в радіани
            val angleInRadians = Math.toRadians(endAngle.toDouble())

            // Знаходимо координати точки на колі
            val dotX = centerX + radius * cos(angleInRadians).toFloat()
            val dotY = centerY + radius * sin(angleInRadians).toFloat()

            drawArc(
                color = Color(0xFFF4D7CF),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(
                    width = 80f,
                    cap = StrokeCap.Round
                ),
            )

            drawArc(
                color = Color(0xFFF47551),
                startAngle = 180f,
                sweepAngle = 180f * progress,
                useCenter = false,
                style = Stroke(
                    width = 80f,
                    cap = StrokeCap.Round
                )
            )

            drawCircle(
                color = Color.White,
                radius = 25f,
                center = Offset(dotX, dotY)
            )
        }
        Text(
            text = "${(progress * 100).toInt()}%",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
