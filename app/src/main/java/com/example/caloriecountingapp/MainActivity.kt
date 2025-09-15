package com.example.caloriecountingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column (
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize(),
            ) {
                TopBar(name = "Top Bar")

            }

        }
    }
}

@Composable
private fun TopBar (name: String) {
    Card(
        modifier = Modifier
            .padding(top = 55.dp, start = 14.dp, end = 14.dp)
            .height(62.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Row (verticalAlignment = Alignment.CenterVertically,) {
                Image(
                    painter = painterResource(id = R.drawable.im_profil_imeg),
                    contentDescription = "menuIcon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(62.dp).clip(CircleShape)
                    )
                Column (modifier = Modifier.padding(start = 14.dp)) {
                    Text(text = "Welcome")
                    Text(text = name)
                }
                Row (
                    verticalAlignment = Alignment.End,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_fined),
                        contentDescription = "menuIcon",
                        modifier = Modifier.size(20.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "menuIcon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
