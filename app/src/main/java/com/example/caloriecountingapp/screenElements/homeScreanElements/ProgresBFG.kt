package com.example.caloriecountingapp.screenElements.homeScreanElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressBFG(
    proteinCurrent: Float = 78f,
    proteinMax: Float = 90f,
    fatsCurrent: Float = 45f,
    fatsMax: Float = 70f,
    carbsCurrent: Float = 95f,
    carbsMax: Float = 110f
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        NutrientProgressItem(
            label = "Protein",
            current = proteinCurrent,
            max = proteinMax,
            color = Color(0xFF4CAF50)
        )
        NutrientProgressItem(
            label = "Fats",
            current = fatsCurrent,
            max = fatsMax,
            color = Color(0xFFE07A5F)
        )
        NutrientProgressItem(
            label = "Carbs",
            current = carbsCurrent,
            max = carbsMax,
            color = Color(0xFFFFC107)
        )
    }
}

@Composable
private fun NutrientProgressItem(
    label: String,
    current: Float,
    max: Float,
    color: Color
) {
    val progress = if (max > 0f) (current / max).coerceIn(0f, 1f) else 0f

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(
            modifier = Modifier
                .width(80.dp)
                .height(6.dp)
        ) {
            // Background track
            drawLine(
                color = Color(0xFFE9E9E9),
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = size.height,
                cap = StrokeCap.Round
            )
            // Progress fill
            if (progress > 0f) {
                drawLine(
                    color = color,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width * progress, size.height / 2),
                    strokeWidth = size.height,
                    cap = StrokeCap.Round
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "${current.toInt()}/${max.toInt()}g",
            fontSize = 13.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBFGPreview() {
    ProgressBFG()
}