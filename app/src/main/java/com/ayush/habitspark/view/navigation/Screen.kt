package com.ayush.habitspark.view.navigation

sealed class Screen (val route: String){
    object Onboarding : Screen("onboarding")
    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Main : Screen("main_screen")
    object UpdatePassword: Screen("update_password")
}