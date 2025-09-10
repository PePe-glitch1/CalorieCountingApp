package com.example.caloriecountingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column (
                modifier = Modifier.
                background(Color.Gray).fillMaxSize(),
            ) {
                topInfoContainer()
                progresAllContainer()
                dividedCalorieContainer()
                infoBlockContainer()
                bottomInfoContainer()

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun topInfoContainer() {
    Column (
        modifier = Modifier
            .background(Color.Cyan)
            .height(30.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello Ass")
    }
}

@Preview(showBackground = true)
@Composable
fun progresAllContainer() {
    Column (
        modifier = Modifier
            .background(Color.Green)
            .fillMaxHeight(0.2f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello Dildo")
    }
}

@Preview(showBackground = true)
@Composable
fun dividedCalorieContainer() {
    Column (
        modifier = Modifier
            .background(Color.Yellow)
            .fillMaxHeight(0.2f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello Dildo")
    }
}

@Preview(showBackground = true)
@Composable
fun infoBlockContainer() {
    Column (
        modifier = Modifier
            .background(Color.Magenta)
            .fillMaxHeight(0.2f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello Dildo")
    }
}

@Preview(showBackground = true)
@Composable
fun bottomInfoContainer() {
    Column (
        modifier = Modifier
            .background(Color.Blue)
            .fillMaxHeight(0.2f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello Dildo")
    }
}
