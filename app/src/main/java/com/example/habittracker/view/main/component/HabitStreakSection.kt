package com.example.habittracker.view.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.poppinsFontFamily

@Composable
fun HabitStreakSection(
    currentStreak: Int,
    longestStreak: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // current streak card
        StreakCard(
            modifier = Modifier.weight(1f),
            label = "Current Streak",
            value = currentStreak,
            emoji = "🔥"
        )
        // longest streak card
        StreakCard(
            modifier = Modifier.weight(1f),
            label = "Longest Streak",
            value = longestStreak,
            emoji = "🏆"
        )
    }
}

@Composable
fun StreakCard(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    emoji: String
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = emoji, fontSize = 28.sp)
        Text(
            text = "$value",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFontFamily
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color.Gray,
            fontFamily = poppinsFontFamily,
            textAlign = TextAlign.Center
        )
    }
}