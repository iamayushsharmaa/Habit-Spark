package com.example.habittracker.view.main

import android.widget.Toast
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
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.habittracker.data.models.HabitResponse
import com.example.habittracker.data.models.isCompletedOn
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.example.habittracker.utils.getTodayTimestamp
import com.example.habittracker.viewModel.HabitsViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun Home(
    navController: NavController,
    habitViewModel: HabitsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val currentDate = LocalDate.now()
    val startDate = currentDate.minusMonths(11)

    var date by remember { mutableStateOf<LocalDate>(LocalDate.now()) }

    val habits by habitViewModel.habitsForSelectedDates.collectAsState()

    val uiState by habitViewModel.uiState.collectAsState()
    val selectedDate by habitViewModel.selectedDate.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    var selectedHabit by remember { mutableStateOf<HabitResponse?>(null) }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            selectedHabit?.let {
                HabitDetailSheet(
                    habit = it,
                    isCompleted = it.isCompletedOn(selectedDate),
                    isLocked = selectedDate < getTodayTimestamp(),
                    onDismissRequest = {
                        coroutineScope.launch {
                            modalBottomSheetState.hide()
                        }
                    },
                    onDeleteClick = {
                        coroutineScope.launch {
                            habitViewModel.deleteHabit(it.habitId)
                            modalBottomSheetState.hide()
                        }
                    },
                    onCompleteClick = {
                        coroutineScope.launch {
                            habitViewModel.toggleHabit(it.habitId)
                            modalBottomSheetState.hide()
                        }
                    }
                )
            }
        }
    ) {
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
            ) {
                Text(
                    text = "Ayush",
                    fontSize = 30.sp,
                    color = AppColor.Black,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            TrackerLayout(
                startDate = startDate,
                currentDate = currentDate,
            )

            WeekCalendarScreen(
                modifier = Modifier
                    .padding(start = 4.dp),
                onDateClicked = { }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                when {
                    uiState.isLoading -> {
                        ProgressBar(
                            modifier = Modifier
                                .align(CenterHorizontally)
                        )
                    }

                    uiState.error != null -> {
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
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }

                    habits.isEmpty() -> {
                        Text(
                            text = "Add new Habits",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    else -> {
                        val today = getTodayTimestamp()

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(habits) { habit ->

                                val isCompleted = habit.isCompletedOn(selectedDate)

                                val isLocked = selectedDate != today

                                HabitStyle(
                                    habit = habit,
                                    isCompleted = isCompleted,
                                    isLocked = isLocked,
                                    onClick = {
                                        selectedHabit = habit
                                        coroutineScope.launch {
                                            modalBottomSheetState.show()
                                        }
                                    }
                                )
                            }
                        }
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
            .size(55.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            color = AppColor.BlackFade,
            strokeWidth = 5.dp
        )
    }
}
