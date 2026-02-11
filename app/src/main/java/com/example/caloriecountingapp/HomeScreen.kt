package com.example.caloriecountingapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caloriecountingapp.screenElements.AddButton
import com.example.caloriecountingapp.screenElements.BottomBar
import com.example.caloriecountingapp.screenElements.DayNutrition
import com.example.caloriecountingapp.screenElements.ProgressBFG
import com.example.caloriecountingapp.screenElements.ProgressBar
import com.example.caloriecountingapp.screenElements.TopBar
import com.example.caloriecountingapp.screenElements.WeeklyCalorieChart

@Preview
@Composable
fun HomeScreen(
    // Calorie gauge
    currentCalories: Int = 1721,
    maxCalories: Int = 2213,
    // Nutrient bars
    proteinCurrent: Float = 78f,
    proteinMax: Float = 90f,
    fatsCurrent: Float = 45f,
    fatsMax: Float = 70f,
    carbsCurrent: Float = 95f,
    carbsMax: Float = 110f,
    // Weekly chart
    weekData: List<DayNutrition> = listOf(
        DayNutrition("S", 1000f, 35f, 18f, 3f, 600f),
        DayNutrition("M", 1020f, 38f, 22f, 5f, 750f),
        DayNutrition("T", 1100f, 42f, 25f, 6f, 820f),
        DayNutrition("W", 1450f, 40f, 20f, 4f, 900f),
        DayNutrition("T", 1350f, 45f, 28f, 7f, 700f),
        DayNutrition("F", 1750f, 50f, 30f, 8f, 1100f),
        DayNutrition("S", 1600f, 48f, 26f, 6f, 950f)
    ),
    selectedDayIndex: Int = 3,
    onDaySelected: (Int) -> Unit = {},
    // Navigation
    selectedTab: Int = 0,
    onHomeClick: () -> Unit = {},
    onRecipesClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = Color.White,
        topBar = { TopBar() },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                BottomBar(
                    selectedTab = selectedTab,
                    onHomeClick = onHomeClick,
                    onRecipesClick = onRecipesClick,
                    onStatsClick = onStatsClick,
                    onProfileClick = onProfileClick
                )
                AddButton(
                    onClick = onAddClick,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-15).dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(65.dp))

            ProgressBar(
                currentCalories = currentCalories,
                maxCalories = maxCalories
            )

            ProgressBFG(
                proteinCurrent = proteinCurrent,
                proteinMax = proteinMax,
                fatsCurrent = fatsCurrent,
                fatsMax = fatsMax,
                carbsCurrent = carbsCurrent,
                carbsMax = carbsMax
            )

            Spacer(modifier = Modifier.height(20.dp))

            WeeklyCalorieChart(
                modifier = Modifier.padding(horizontal = 8.dp),
                data = weekData,
                selectedDayIndex = selectedDayIndex,
                onDaySelected = onDaySelected
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val sampleWeekData = listOf(
        DayNutrition("S", 1000f, 35f, 18f, 3f, 600f),
        DayNutrition("M", 1020f, 38f, 22f, 5f, 750f),
        DayNutrition("T", 1100f, 42f, 25f, 6f, 820f),
        DayNutrition("W", 1450f, 40f, 20f, 4f, 900f),
        DayNutrition("T", 1350f, 45f, 28f, 7f, 700f),
        DayNutrition("F", 1750f, 50f, 30f, 8f, 1100f),
        DayNutrition("S", 1600f, 48f, 26f, 6f, 950f)
    )
    HomeScreen(
        weekData = sampleWeekData,
        selectedDayIndex = 3
    )
}