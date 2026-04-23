package com.example.habittracker.view.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.example.habittracker.utils.getTodayTimestamp
import com.example.habittracker.utils.toTimestamp
import com.example.habittracker.view.main.component.GlobalStreakBanner
import com.example.habittracker.view.main.component.HabitStyle
import com.example.habittracker.view.main.component.TrackerLayout
import com.example.habittracker.viewModel.HabitsViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    habitViewModel: HabitsViewModel = hiltViewModel(),
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    val userName = FirebaseAuth.getInstance().currentUser?.displayName ?: "Hey there"
    val globalStreak by habitViewModel.globalStreak.collectAsState()

    val habits by habitViewModel.habitsForSelectedDates.collectAsState()
    val uiState by habitViewModel.uiState.collectAsState()
    val selectedDate by habitViewModel.selectedDate.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var selectedHabit by remember { mutableStateOf<HabitResponse?>(null) }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val today = getTodayTimestamp()
    val allHabits by habitViewModel.allHabits.collectAsState()

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    if (showSheet && selectedHabit != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                selectedHabit = null
            },
            sheetState = sheetState,
            containerColor = colors.surface,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .width(50.dp)
                        .height(4.dp)
                        .background(
                            color = colors.onSurfaceVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        ) {
            HabitDetailSheet(
                habit = selectedHabit!!,
                isCompleted = selectedHabit!!.isCompletedOn(selectedDate),
                isLocked = selectedDate < today,
                onDismissRequest = {
                    showSheet = false
                    selectedHabit = null
                },
                onDeleteClick = {
                    coroutineScope.launch {
                        habitViewModel.deleteHabit(selectedHabit!!.habitId)
                        showSheet = false
                        selectedHabit = null
                    }
                },
                onCompleteClick = {
                    coroutineScope.launch {
                        habitViewModel.toggleHabit(selectedHabit!!.habitId)
                        showSheet = false
                        selectedHabit = null
                    }
                }
            )
        }
    }


    LazyColumn(
        modifier = Modifier
            .background(colors.background)
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hey! $userName",
                    fontSize = 22.sp,
                    color = colors.onBackground,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp)
                )
            }
        }

        item { Spacer(Modifier.height(10.dp)) }

        item { TrackerLayout(habits = allHabits) }

        item { Spacer(Modifier.height(2.dp)) }

        item {
            WeekCalendarScreen(
                modifier = Modifier.padding(start = 4.dp),
                onDateClicked = { localDate ->
                    habitViewModel.onDateSelected(localDate.toTimestamp())
                }
            )
        }

        item { Spacer(Modifier.height(10.dp)) }

        item { GlobalStreakBanner(streak = globalStreak) }

        item { Spacer(Modifier.height(8.dp)) }

        when {
            uiState.isLoading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        ProgressBar(modifier = Modifier)
                    }
                }
            }

            uiState.error != null -> {
                item {
                    Text(
                        text = "You have no habits at the moment",
                        fontSize = 16.sp,
                        color = colors.onSurfaceVariant,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(start = 15.dp, top = 15.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            habits.isEmpty() -> {
                item {
                    Text(
                        text = "Add new Habits",
                        color = colors.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp)
                    )
                }
            }

            else -> {
                items(habits) { habit ->
                    val streak by habitViewModel
                        .habitStreakFlow(habitId = habit.habitId)
                        .collectAsState()

                    HabitStyle(
                        habit = habit,
                        isCompleted = habit.isCompletedOn(selectedDate),
                        isLocked = selectedDate < today,
                        streak = streak,
                        onClick = {
                            selectedHabit = habit
                            showSheet = true
                        }
                    )
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
fun ProgressBar(
    modifier: Modifier
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .size(55.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            color = colors.primary,
            strokeWidth = 5.dp
        )
    }
}

