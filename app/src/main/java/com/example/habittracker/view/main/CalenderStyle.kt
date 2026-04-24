package com.example.habittracker.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.example.habittracker.viewModel.CalendarViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun WeekCalendarStyle(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
    onDateClicked: (LocalDate) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val colors = MaterialTheme.colorScheme

    val currentDate = viewModel.currentDate.collectAsState().value
    val selectedDate = viewModel.selectedDate.collectAsState().value

    val pagerState = rememberPagerState(initialPage = 2, pageCount = { 5 })

    val weekOffset = pagerState.currentPage - 2

    Column(modifier = modifier) {
        Text(
            text = viewModel.getWeekDates(weekOffset).monthName,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = colors.onBackground,
            modifier = Modifier.padding(16.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val weekData = viewModel.getWeekDates(page - 2)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                weekData.weekDates.forEach { date ->
                    CalendarCard(
                        modifier = Modifier.padding(5.dp),
                        day = date.format(DateTimeFormatter.ofPattern("EEE")),
                        date = date.dayOfMonth.toString(),
                        isCurrentDate = date == currentDate,
                        isSelectedDate = date == selectedDate,
                        onDateClicked = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            viewModel.onDateSelected(date)
                            onDateClicked(date)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarCard(
    modifier: Modifier = Modifier,
    day: String,
    date: String,
    isSelectedDate: Boolean,
    isCurrentDate: Boolean,
    onDateClicked: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = modifier
            .height(62.dp)
            .width(48.dp)
            .then(
                if (isCurrentDate && !isSelectedDate)
                    Modifier.border(
                        1.5.dp,
                        colors.onSurface,
                        RoundedCornerShape(10.dp)
                    )
                else Modifier
            ),
        onClick = { onDateClicked() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelectedDate) colors.primary else colors.surfaceVariant,
            contentColor = if (isSelectedDate) colors.onPrimary else colors.onSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelectedDate) 6.dp else 0.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
            )

            Text(
                text = day,
                fontFamily = poppinsFontFamily,
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
            )

            if (isCurrentDate) {
                Spacer(Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(
                            color = if (isSelectedDate)
                                colors.onPrimary
                            else
                                colors.onSurface,
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}
