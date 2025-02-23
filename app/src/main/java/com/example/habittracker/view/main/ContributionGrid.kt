package com.example.habittracker.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habittracker.data.remote.response.HabitResponse
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.example.habittracker.viewModel.HabitsViewModel
import com.example.habittracker.viewModel.UiState
import java.time.LocalDate

@Composable
fun TrackerLayout(
    startDate: LocalDate,
    currentDate: LocalDate,
    habitViewModel: HabitsViewModel = hiltViewModel()
) {
    val habitState by habitViewModel.habitsUiState.collectAsState()
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(horizontal = 16.dp, vertical = 5.dp)

    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Center
        ){
            when (habitState){
                is UiState.Loading -> ProgressBar(
                    modifier = Modifier
                )
                is UiState.Error -> {
                    val message = (habitState as UiState.Error<List<HabitResponse>>).message
                    Text(
                        text = "Something went wrong",
                        fontSize = 13.sp,
                        color = AppColor.Black,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                    )
                }
                is UiState.Success -> {
                    val habits = (habitState as UiState.Success<List<HabitResponse>>).data
                    ContributionGrid(
                        startDate = startDate,
                        currentDate = currentDate,
                        habits = habits
                    )
                }
            }
        }
    }
}


@Composable
fun ContributionGrid(
    startDate: LocalDate,
    currentDate: LocalDate,
    habits: List<HabitResponse>
) {
    val days = mutableListOf<LocalDate>()
    var tempDate = startDate
    while (!tempDate.isAfter(currentDate)) {
        days.add(tempDate)
        tempDate = tempDate.plusDays(1)
    }

    val activeHabits = habits.filter { it.isActive }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7), // 7 days in a week
        modifier = Modifier.height(400.dp)
    ) {
        items(days.size) { index ->
            val date = days[index]
            // Flatten all completion histories into a single list for the date
            val completionsForDate = activeHabits.flatMap { it.completionHistory }
                .filter { it.date == date }

            // Check if all active habits have a completion marked as true for this date
            val allCompleted = activeHabits.isNotEmpty() && activeHabits.all { habit ->
                completionsForDate.any { it.habitId == habit.habitId && it.isCompleted }
            }

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(4.dp)
                    .background(
                        color = if (allCompleted) Color.Green else Color.Gray.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}