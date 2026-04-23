package com.example.habittracker.view.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.habittracker.common.Res
import com.example.habittracker.data.models.Frequency
import com.example.habittracker.data.models.Goal
import com.example.habittracker.data.models.HabitRequest
import com.example.habittracker.data.models.toDisplayString
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.example.habittracker.view.main.component.DropdownField
import com.example.habittracker.view.main.component.HabitTextField
import com.example.habittracker.view.main.component.IconPicker
import com.example.habittracker.view.main.component.PickColor
import com.example.habittracker.view.main.component.SectionTitle
import com.example.habittracker.view.navigation.BottomNavItem
import com.example.habittracker.viewModel.HabitsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabit(
    habitsViewModel: HabitsViewModel = hiltViewModel(),
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    var habitName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    val frequency = Frequency.values()
    var expanded by remember { mutableStateOf(false) }
    var selectedFrequency by remember { mutableStateOf(Frequency.EVERYDAY) }

    var selectedIcon by remember { mutableStateOf("fitness") }
    var selectedColor by remember { mutableStateOf("#FFC107") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Let's Start a new Habit",
                color = colors.onBackground,
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
            )
        }

        HabitTextField("Name", habitName) { habitName = it }
        HabitTextField("Description", description) { description = it }
        HabitTextField("Value", value) { value = it }
        HabitTextField("Unit", unit) { unit = it }

        DropdownField(
            title = "Interval",
            selectedValue = selectedFrequency.toDisplayString(),
            expanded = expanded,
            onExpandChange = { expanded = it },
            options = frequency.map { it.toDisplayString() },
            onOptionSelected = {
                selectedFrequency = frequency.first { f -> f.toDisplayString() == it }
                expanded = false
            }
        )

        SectionTitle("Icons")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(Res.iconMap.keys.toList()) { iconName ->
                IconPicker(
                    iconName = iconName,
                    isIconSelected = iconName == selectedIcon,
                    onIconSelected = { selectedIcon = it }
                )
            }
        }

        SectionTitle("Background color")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(Res.colorMap.keys.toList()) { hex ->
                PickColor(
                    colorHex = hex,
                    isSelected = hex == selectedColor,
                    onClick = { selectedColor = hex }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (habitName.isNotBlank()) {
                    val habitRequest = HabitRequest(
                        name = habitName,
                        icon = selectedIcon,
                        iconBackground = selectedColor,
                        description = description,
                        goal = Goal(value = value, unit = unit),
                        frequency = selectedFrequency,
                        startDate = LocalDate.now()
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli(),
                        isActive = true
                    )

                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            habitsViewModel.createHabit(habitRequest)
                            Toast.makeText(
                                context,
                                "Your habit is ready to go!",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.navigate(BottomNavItem.Home.route) {
                                popUpTo(BottomNavItem.AddHabit.route) {
                                    inclusive = true
                                }
                            }

                            habitName = ""
                            description = ""
                            selectedIcon = "fitness"
                            selectedColor = "#FFC107"
                            selectedFrequency = Frequency.EVERYDAY

                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Failed to create habit",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Please enter a habit name", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(horizontal = 15.dp, vertical = 5.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = colors.onPrimary,
                containerColor = colors.primary
            )
        ) {
            Text(
                text = "Add Habit",
                fontFamily = poppinsFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}