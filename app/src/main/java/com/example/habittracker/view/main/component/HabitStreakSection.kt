package com.example.habittracker.view.main.component

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.common.Res
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
            isCurrent = true
        )
        // longest streak card
        StreakCard(
            modifier = Modifier.weight(1f),
            label = "Longest Streak",
            value = longestStreak,
            isCurrent = false
        )
    }
}

@Composable
fun StreakCard(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    isCurrent: Boolean
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isCurrent){
            Icon(
                painter = painterResource(id = Res.fireIcon),
                contentDescription = "streak",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        } else{
            Icon(
                painter = painterResource(id = Res.fireIcon),
                contentDescription = "streak",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        }
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