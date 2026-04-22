package com.example.habittracker.view.main.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.common.Res
import com.example.habittracker.ui.theme.poppinsFontFamily

@Composable
fun GlobalStreakBanner(streak: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (streak > 0) Color(0xFFFF6B35) else Color(0xFFF5F5F5)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Current Streak",
                fontSize = 12.sp,
                color = if (streak > 0) Color.White.copy(alpha = 0.8f) else Color.Gray,
                fontFamily = poppinsFontFamily
            )
            Text(
                text = "$streak day${if (streak != 1) "s" else ""}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (streak > 0) Color.White else Color.DarkGray,
                fontFamily = poppinsFontFamily
            )
        }
        if (streak > 0) {
            Icon(
                painter = painterResource(id = Res.fireIcon),
                contentDescription = "streak",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        } else {
            Text(text = "💤", fontSize = 40.sp)
        }
    }
}