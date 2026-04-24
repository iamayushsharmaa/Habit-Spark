package com.example.habittracker.view.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habittracker.R
import com.example.habittracker.common.Res
import com.example.habittracker.data.models.HabitResponse
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.example.habittracker.view.main.component.HabitStreakSection
import com.example.habittracker.viewModel.HabitsViewModel

@Composable
fun HabitDetailSheet(
    habit: HabitResponse,
    isCompleted: Boolean,
    isLocked: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit,
    habitViewModel: HabitsViewModel = hiltViewModel(),
) {
    val haptic = LocalHapticFeedback.current

    val colors = MaterialTheme.colorScheme
    val currentStreak by habitViewModel.habitStreakFlow(habitId = habit.habitId).collectAsState()
    val longestStreak by habitViewModel.longestHabitStreakFlow(habitId = habit.habitId)
        .collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
            .background(colors.surface),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Res.toColor(habit.iconBackground).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            IconButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onDeleteClick()
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(36.dp)
                    .background(colors.surface.copy(alpha = 0.7f), RoundedCornerShape(10.dp)),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = colors.error
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete_icon),
                    contentDescription = "delete",
                    modifier = Modifier.padding(6.dp)
                )
            }

            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(36.dp)
                    .background(colors.surface.copy(alpha = 0.7f), RoundedCornerShape(10.dp)),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = colors.onSurface
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.cross_icon),
                    contentDescription = "close",
                    modifier = Modifier.padding(6.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = Res.toColor(habit.iconBackground),
                            shape = RoundedCornerShape(18.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.toResId(habit.icon)),
                        contentDescription = habit.name,
                        tint = colors.surface,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = habit.name,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = colors.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = when {
                                isLocked -> colors.onSurfaceVariant.copy(alpha = 0.15f)
                                isCompleted -> colors.primary.copy(alpha = 0.15f)
                                else -> colors.secondary.copy(alpha = 0.15f)
                            },
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = when {
                            isLocked -> "🔒 Locked"
                            isCompleted -> "✓ Completed"
                            else -> "● Pending"
                        },
                        color = when {
                            isLocked -> colors.onSurfaceVariant
                            isCompleted -> colors.primary
                            else -> colors.secondary
                        },
                        fontSize = 13.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(8.dp))
            }
        }

        Spacer(Modifier.height(16.dp))

        if (habit.description.isNotBlank()) {
            Text(
                text = habit.description,
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                color = colors.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(Modifier.height(12.dp))
        }

        if (habit.goal.value.isNotBlank() && habit.goal.unit.isNotBlank()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.surfaceVariant, RoundedCornerShape(14.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.toResId(habit.icon)),
                    contentDescription = null,
                    tint = Res.toColor(habit.iconBackground),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Target",
                    fontFamily = poppinsFontFamily,
                    fontSize = 13.sp,
                    color = colors.onSurfaceVariant
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${habit.goal.value} ${habit.goal.unit}",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = colors.onSurface
                )
            }
            Spacer(Modifier.height(12.dp))
        }

        HabitStreakSection(
            currentStreak = currentStreak,
            longestStreak = longestStreak
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (!isLocked) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onCompleteClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isCompleted) colors.surfaceVariant else colors.onSurface,
                contentColor = if (isCompleted) colors.onSurface else colors.surface
            ),
            border = if (isCompleted) BorderStroke(1.dp, colors.outline) else null
        ) {
            Text(
                text = when {
                    isLocked -> "Locked"
                    isCompleted -> "Mark Incomplete"
                    else -> "Finish"
                },
                fontFamily = poppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}