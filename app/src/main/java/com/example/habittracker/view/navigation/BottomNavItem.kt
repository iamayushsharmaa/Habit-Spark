package com.example.habittracker.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.habittracker.common.Res

sealed class BottomNavItem(val iconBlank: Int, val iconFilled: Int, val route: String) {

    object Home : BottomNavItem( Res.houseIconBlank, Res.houseIconFilled , "home")
    object AddHabit : BottomNavItem( Res.addIconBlank, Res.addIconFilled, "add_habit")
    object Profile : BottomNavItem( Res.profileIconBlank, Res.profileIconFilled, "profile")
}