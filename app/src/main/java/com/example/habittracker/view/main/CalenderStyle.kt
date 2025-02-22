package com.example.habittracker.view.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habittracker.data.calander.CalendarViewModel
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun WeekCalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
    onDateClicked: (LocalDate) -> Unit
) {
    val currentDate = viewModel.currentDate.collectAsState().value
    val selectedDate = viewModel.selectedDate.collectAsState().value
    val pagerState = rememberPagerState(initialPage = Int.MAX_VALUE / 2)

    Column(modifier = modifier) {
        Text(
            text = viewModel.getWeekDates(pagerState.currentPage - Int.MAX_VALUE / 2).monthName,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )

        HorizontalPager(
            count = Int.MAX_VALUE,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val weekData = viewModel.getWeekDates(page - Int.MAX_VALUE / 2)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                weekData.weekDates.forEach { date ->
                    CalendarStyle(
                        modifier = Modifier.padding(5.dp),
                        day = date.format(DateTimeFormatter.ofPattern("EEE")),
                        date = date.dayOfMonth.toString(),
                        isCurrentDate = date == currentDate,
                        isSelectedDate = date == selectedDate,
                        onDateClicked = {
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
fun CalendarStyle(
    modifier: Modifier = Modifier,
    day: String,
    date: String,
    isSelectedDate: Boolean,
    isCurrentDate: Boolean,
    onDateClicked : () -> Unit
) {
    Card(
        modifier = Modifier
            .height(62.dp)
            .width(48.dp),
        onClick = { onDateClicked() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isSelectedDate -> AppColor.Black
                isCurrentDate -> AppColor.Black
                else -> AppColor.WhiteFade
            },
            contentColor =  when {
                isSelectedDate -> AppColor.White
                isCurrentDate -> AppColor.White
                else -> AppColor.Black
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
        }
    }
}
