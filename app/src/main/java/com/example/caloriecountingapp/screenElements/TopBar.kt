package com.example.caloriecountingapp.screenElements

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caloriecountingapp.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val Kurale_reg = FontFamily(
    Font(R.font.kurale_regular, FontWeight.Normal)
)

@Preview
@Composable
fun TopBar(name: String = "User Name") {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
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
                        .clickable { Log.d("TopBar", "Profile image clicked") }
                )
                Spacer(Modifier.width(12.dp))
                Column{
                    Text(
                        text = name,
                        fontSize = 20.sp,
                        fontFamily = Kurale_reg,
                    )

                    Text(
                        text = "-day Plan Streak",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontFamily = Kurale_reg,
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_finder),
                    contentDescription = "finder",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { Log.d("TopBar", "Finder image clicked") }
                )
                Spacer(Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_notification_false),
                    contentDescription = "notification",
                    modifier = Modifier
                        .size(21.dp)
                        .clickable { Log.d("TopBar", "Notification image clicked") }
                )
            }
        }
    }
}
