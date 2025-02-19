package com.example.habittracker.view.navigation


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.view.main.AddHabit
import com.example.habittracker.view.main.BottomNavBar
import com.example.habittracker.view.main.Home
import com.example.habittracker.view.main.Profile
import kotlinx.coroutines.CoroutineExceptionHandler
import java.time.LocalDate


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("TAG", "There has been an issue: ", throwable)
    }

    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalFontFamilyResolver provides createFontFamilyResolver(LocalContext.current, handler)
    ) {
        Scaffold(
            bottomBar = {
                BottomNavBar(navController = navController) // Remove manual padding here
            }
        ) {  innerPadding->
            NavigationGraph(
                navController,
                innerPadding
            )
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier = Modifier.padding(innerPadding)) {
        composable(BottomNavItem.Home.route) {
            Home(
                userId = "",
                date = LocalDate.now()
            )
        }
        composable(BottomNavItem.AddHabit.route){
            AddHabit()
        }

        composable(BottomNavItem.Profile.route) {
            Profile(
                navController,
                onSignOut = { },

            )
        }
    }
}
