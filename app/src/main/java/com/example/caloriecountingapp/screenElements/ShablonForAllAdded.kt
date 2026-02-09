package com.example.caloriecountingapp.screenElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ShablonForAllAdded() {
        LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        items(100) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 4.dp, horizontal = 6.dp)
                    .background(Color.White),
                elevation = CardDefaults.cardElevation(5.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = "Borsh",
                            fontSize = 16.sp,
                            fontFamily = Kurale_reg,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                        Text(
                            text = "11:49",
                            fontSize = 12.sp,
                            fontFamily = Kurale_reg,
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                        Text(
                            text = "27.11.2004",
                            fontSize = 12.sp,
                            fontFamily = Kurale_reg,
                            color = Color.Gray,
                            modifier = Modifier
                                    .padding(start = 16.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Калорії = 250",
                            fontSize = 12.sp,
                            fontFamily = Kurale_reg,
                            modifier = Modifier
                                .padding(end = 16.dp)
                        )
                        Text(
                            text = "Білки= 50",
                            fontSize = 12.sp,
                            fontFamily = Kurale_reg,
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(end = 16.dp)
                        )
                        Text(
                            text = "Жири = 40",
                            fontSize = 12.sp,
                            fontFamily = Kurale_reg,
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(end = 16.dp)
                        )
                        Text(
                            text = "Вуглеводи = 20",
                            fontSize = 12.sp,
                            fontFamily = Kurale_reg,
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(end = 16.dp)
                        )
                    }
                }
            }
        }
    }
}