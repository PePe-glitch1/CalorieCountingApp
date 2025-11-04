package com.example.caloriecountingapp.screanElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.caloriecountingapp.R


@Composable
fun TopBar(name: String = "User Name") {
    Card(
        modifier = Modifier
            .padding(top = 55.dp)
            .height(62.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.im_profile_image),
                    contentDescription = "menuIcon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(62.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(text = "Welcome")
                    Text(text = name)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_finder),
                    contentDescription = "finder",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "notification",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
