package com.example.habittracker.view.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.data.models.HabitResponse
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun TrackerLayout(
    habits: List<HabitResponse>,
    modifier: Modifier = Modifier
) {
    val today = LocalDate.now()
    val totalDays = 126

    val completionCounts = remember(habits) {
        habits.flatMap { habit ->
            habit.completionHistory
                .filter { it.isCompleted }
                .map {
                    Instant.ofEpochMilli(it.date)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                }
        }.groupingBy { it }.eachCount()
    }

    val days = (totalDays - 1 downTo 0).map { today.minusDays(it.toLong()) }
    val weeks = days.chunked(7)
    var rowWidth by remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Your Activity",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    rowWidth = coordinates.size.width.toFloat()
                },
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            if (rowWidth > 0f) {
                val totalSpacingPx = with(density) { (3.dp * (weeks.size - 1)).toPx() }
                val cellSizePx = (rowWidth - totalSpacingPx) / weeks.size
                val cellSizeDp = with(density) { cellSizePx.toDp() }

                weeks.forEach { week ->
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        week.forEach { date ->
                            val count = completionCounts[date] ?: 0
                            val maxCount = completionCounts.values.maxOrNull() ?: 1
                            val intensity = if (count == 0) 0f
                            else (count.toFloat() / maxCount).coerceIn(0.2f, 1f)
                            val isFuture = date.isAfter(today)

                            Box(
                                modifier = Modifier
                                    .size(cellSizeDp)
                                    .background(
                                        color = when {
                                            isFuture -> MaterialTheme.colorScheme.surfaceVariant.copy(
                                                alpha = 0.3f
                                            )

                                            count == 0 -> MaterialTheme.colorScheme.surfaceVariant
                                            else -> MaterialTheme.colorScheme.primary.copy(alpha = intensity)
                                        },
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Less",
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = poppinsFontFamily
            )
            Spacer(Modifier.width(4.dp))
            listOf(0.2f, 0.4f, 0.6f, 0.8f, 1f).forEach { alpha ->
                Spacer(Modifier.width(3.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = "More",
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = poppinsFontFamily
            )
        }
    }
}