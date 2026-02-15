package com.example.caloriecountingapp.screenElements.homeScreanElements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caloriecountingapp.R

@Composable
fun BottomBar(
    selectedTab: Int = 0,
    onHomeClick: () -> Unit = {},
    onRecipesClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val navIconColor = Color(0xFF1B1464)

    Column {
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFD0D0D0)
        )
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp
        ) {
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = onHomeClick,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_home_bar),
                        contentDescription = "Home",
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedTab == 0) navIconColor else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = onRecipesClick,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_resipt_bar),
                        contentDescription = "Recipes",
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedTab == 1) navIconColor else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
            // Spacer for FAB
            NavigationBarItem(
                selected = false,
                onClick = {},
                enabled = false,
                icon = { Spacer(Modifier.size(24.dp)) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                selected = selectedTab == 2,
                onClick = onStatsClick,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_statistick_bar),
                        contentDescription = "Statistics",
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedTab == 2) navIconColor else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                selected = selectedTab == 3,
                onClick = onProfileClick,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_user_bar),
                        contentDescription = "Profile",
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedTab == 3) navIconColor else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BottomBar()
}