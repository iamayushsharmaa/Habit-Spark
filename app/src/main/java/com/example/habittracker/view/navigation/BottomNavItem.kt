package com.example.habittracker.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val label: String, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem("Home", Icons.Filled.Home, "home")
    object AddHabit : BottomNavItem("Add", Icons.Filled.Add, "add_habit")
    object Profile : BottomNavItem("Profile", Icons.Filled.Settings, "profile")
}