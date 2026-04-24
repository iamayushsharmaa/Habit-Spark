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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

    val frequency = Frequency.entries.toTypedArray()
    var expanded by remember { mutableStateOf(false) }
    var selectedFrequency by remember { mutableStateOf(Frequency.EVERYDAY) }

    var selectedIcon by remember { mutableStateOf("fitness") }
    var selectedColor by remember { mutableStateOf("#FFC107") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        Text(
            text = "Let's Start a New Habit",
            color = colors.onBackground,
            fontSize = 28.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

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

        Spacer(Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(
                        color = Res.toColor(selectedColor),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.toResId(selectedIcon)),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = habitName.ifBlank { "Habit name" },
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = colors.onBackground
                )
                Text(
                    text = selectedFrequency.toDisplayString(),
                    fontFamily = poppinsFontFamily,
                    fontSize = 13.sp,
                    color = colors.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                when {
                    habitName.isBlank() -> Toast.makeText(
                        context,
                        "Please enter a name",
                        Toast.LENGTH_SHORT
                    ).show()

                    value.isBlank() -> Toast.makeText(
                        context,
                        "Please enter a goal value",
                        Toast.LENGTH_SHORT
                    ).show()

                    unit.isBlank() -> Toast.makeText(
                        context,
                        "Please enter a unit",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
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

                        habitsViewModel.createHabit(habitRequest)
                        navController.navigate(BottomNavItem.Home.route) {
                            popUpTo(BottomNavItem.AddHabit.route) {
                                inclusive = true
                            }
                        }
                    }
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