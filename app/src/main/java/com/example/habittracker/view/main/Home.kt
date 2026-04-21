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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    habitViewModel: HabitsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val userName = FirebaseAuth.getInstance().currentUser?.displayName ?: "Hey there"

    val habits by habitViewModel.habitsForSelectedDates.collectAsState()
    val uiState by habitViewModel.uiState.collectAsState()
    val selectedDate by habitViewModel.selectedDate.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var selectedHabit by remember { mutableStateOf<HabitResponse?>(null) }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val today = getTodayTimestamp()

    if (showSheet && selectedHabit != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                selectedHabit = null
            },
            sheetState = sheetState,
            containerColor = AppColor.White,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .width(50.dp)
                        .height(4.dp)
                        .background(
                            color = Color.Gray.copy(alpha = 0.4f),
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

    Column(
        modifier = Modifier
            .background(color = AppColor.White)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userName,
                fontSize = 24.sp,
                color = AppColor.Black,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(start = 15.dp, top = 15.dp)
            )
        }

        Spacer(Modifier.height(8.dp))

        WeekCalendarScreen(
            modifier = Modifier.padding(start = 4.dp),
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
                    ProgressBar(modifier = Modifier.align(CenterHorizontally))
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
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(habits) { habit ->
                            HabitStyle(
                                habit = habit,
                                isCompleted = habit.isCompletedOn(selectedDate),
                                isLocked = selectedDate < today,
                                onClick = {
                                    selectedHabit = habit
                                    showSheet = true
                                }
                            )
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
