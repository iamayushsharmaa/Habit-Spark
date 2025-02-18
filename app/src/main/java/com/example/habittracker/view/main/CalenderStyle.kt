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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habittracker.data.calander.CalanderViewModel
import com.example.habittracker.ui.theme.AppColor
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@Composable
fun WeekCalendarScreen(
    viewModel: CalanderViewModel = hiltViewModel()
) {
    val weekDates by viewModel.weekDates.collectAsState()

    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState.currentPage) {
        val newOffset = pagerState.currentPage - Int.MAX_VALUE / 2
        viewModel.updateWeekOffset(newOffset)
    }

    HorizontalPager(
        count = Int.MAX_VALUE,
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weekDates.forEach { calendarDay ->
                CalanderStyle(
                    modifier = Modifier.padding(5.dp),
                    day = calendarDay.day,
                    date = calendarDay.date,
                    isCurrentDate = calendarDay.isCurrentDate
                )
            }
        }
    }
}

@Composable
fun CalanderStyle(
    modifier: Modifier = Modifier,
    day: String,
    date: String,
    isCurrentDate: Boolean = false
) {
    Card(
        modifier = Modifier
            .height(65.dp)
            .width(46.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentDate) AppColor.Blue else AppColor.WhiteFade
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
                text = day,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = if (isCurrentDate) AppColor.White else AppColor.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(2.dp)
            )
            Text(
                text = date,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = if (isCurrentDate) AppColor.White else AppColor.Black
            )
        }
    }
}