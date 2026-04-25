package com.ayush.habitspark.view.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.ayush.habitspark.data.models.HabitResponse
import com.ayush.habitspark.data.models.isCompletedOn
import com.ayush.habitspark.ui.theme.poppinsFontFamily
import com.ayush.habitspark.utils.getTodayTimestamp
import com.ayush.habitspark.utils.greetingText
import com.ayush.habitspark.utils.toTimestamp
import com.ayush.habitspark.view.main.component.EmptyHabitsState
import com.ayush.habitspark.view.main.component.GlobalStreakBanner
import com.ayush.habitspark.view.main.component.HabitStyle
import com.ayush.habitspark.view.main.component.TrackerLayout
import com.ayush.habitspark.view.navigation.BottomNavItem
import com.ayush.habitspark.viewModel.HabitsViewModel
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

    val habitsRaw by habitViewModel.habitsForSelectedDates.collectAsState()
    val selectedDate by habitViewModel.selectedDate.collectAsState()

    val habits = remember(habitsRaw, selectedDate) {
        habitsRaw.sortedWith(compareBy { it.isCompletedOn(selectedDate) })
    }

    val uiState by habitViewModel.uiState.collectAsState()

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

    val currenthabit = selectedHabit
    if (showSheet && currenthabit != null) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showSheet = false
                    selectedHabit = null
                }
            },
            sheetState = sheetState,
            tonalElevation = 4.dp,
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
                habit = currenthabit,
                isCompleted = currenthabit.isCompletedOn(selectedDate),
                isLocked = selectedDate < today,
                onDismissRequest = {
                    coroutineScope.launch {
                        sheetState.hide()
                        showSheet = false
                        selectedHabit = null
                    }
                },
                onDeleteClick = {
                    coroutineScope.launch {
                        habitViewModel.deleteHabit(currenthabit.habitId)
                        sheetState.hide()
                        showSheet = false
                        selectedHabit = null
                    }
                },
                onCompleteClick = {
                    coroutineScope.launch {
                        habitViewModel.toggleHabit(currenthabit.habitId)
                        sheetState.hide()
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
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 3.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        text = greetingText(),
                        fontSize = 13.sp,
                        color = colors.onSurfaceVariant,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Hey, $userName!",
                        fontSize = 22.sp,
                        color = colors.onBackground,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item { Spacer(Modifier.height(10.dp)) }

        item { TrackerLayout(habits = allHabits) }

        item { Spacer(Modifier.height(2.dp)) }

        item {
            WeekCalendarStyle(
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
                    EmptyHabitsState(
                        onAddClick = {
                            navController.navigate(BottomNavItem.AddHabit.route)
                        }
                    )
                }
            }

            else -> {
                items(habits, key = { it.habitId }) { habit ->
                    val streak by habitViewModel
                        .habitStreakFlow(habitId = habit.habitId)
                        .collectAsState()

                    HabitStyle(
                        modifier = Modifier.animateItem(),
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

