package com.ayush.habitspark.view.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayush.habitspark.ui.theme.poppinsFontFamily


@Composable
fun StreakBadge(streak: Int) {

    val colors = MaterialTheme.colorScheme

    val backgroundColor = if (streak > 0) {
        colors.secondary   // active → orange (your secondary)
    } else {
        colors.surfaceVariant
    }

    val textColor = if (streak > 0) {
        colors.onSecondary
    } else {
        colors.onSurfaceVariant
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = "🔥",
            fontSize = 16.sp
        )

        Text(
            text = "$streak day${if (streak != 1) "s" else ""}",
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily
        )
    }
}