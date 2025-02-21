package com.example.habittracker.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.example.habittracker.viewModel.HabitsViewModel
import java.time.LocalDate

@Composable
fun Home(
    habitViewModel: HabitsViewModel = hiltViewModel(),
) {
    val date = LocalDate.now()

    LaunchedEffect (date){
        habitViewModel.getHabitsByDate(date)
    }
    val habits by habitViewModel.habitsUiState.collectAsState()

    Column(
        modifier = Modifier
            .background(color = AppColor.White)
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Ayush",
                fontSize = 30.sp,
                color = AppColor.Black,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(start = 15.dp, top = 15.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        WeekCalendarScreen()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                when {
                    habits.isLoading -> ProgressBar(
                        modifier = Modifier
                            .align(CenterHorizontally)
                    )
                    habits.errorMessage != null -> {
                        Text(
                            text = "You have no habits at the moment",
                            fontSize = 16.sp,
                            color = AppColor.Black,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(start = 15.dp, top = 15.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                    habits.data != null -> {
                        // Empty column to maintain the centered layout
                    }
                }
            }

            if (habits.data != null) {
                val habits = habits.data!!
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(habits) { habit ->
                        Text(text = habit.name, fontSize = 50.sp)
                    }
                }
            }
        }

    }
}

@Composable
fun ProgressBar(
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.BlackFade,
            strokeWidth = 6.dp
        )
    }
}

@Preview
@Composable
private fun Habit() {
    Home()
}

