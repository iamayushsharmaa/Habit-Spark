@file:Suppress("UNREACHABLE_CODE")

package com.ayush.habitspark.view.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayush.habitspark.common.Res
import com.ayush.habitspark.data.models.HabitResponse
import com.ayush.habitspark.ui.theme.poppinsFontFamily

@Composable
fun HabitStyle(
    modifier: Modifier = Modifier,
    habit: HabitResponse,
    isCompleted: Boolean,
    isLocked: Boolean,
    streak: Int,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 5.dp)
            .height(70.dp)
            .background(
                shape = RoundedCornerShape(14.dp),
                color = colors.surfaceVariant
            )
            .clickable(enabled = !isLocked) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .size(46.dp)
                .background(
                    shape = RoundedCornerShape(12.dp),
                    color = Res.toColor(habit.iconBackground)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(id = Res.toResId(habit.icon)),
                contentDescription = habit.name,
                tint = colors.surface,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = habit.name,
                fontFamily = poppinsFontFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.onSurfaceVariant,
                maxLines = 1
            )

            Spacer(Modifier.height(3.dp))

            val statusBackground = when {
                isLocked -> colors.surface.copy(alpha = 0.3f)
                isCompleted -> colors.primary.copy(alpha = 0.2f)
                else -> colors.secondary.copy(alpha = 0.2f)
            }

            val statusTextColor = when {
                isLocked -> colors.onSurfaceVariant
                isCompleted -> colors.primary
                else -> colors.secondary
            }

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        shape = RoundedCornerShape(4.dp),
                        color = statusBackground
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when {
                        isLocked -> "Locked"
                        isCompleted -> "Done"
                        else -> "Active"
                    },
                    fontFamily = poppinsFontFamily,
                    fontSize = 11.sp,
                    color = statusTextColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        VerticalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = colors.onSurfaceVariant.copy(alpha = 0.2f)
        )

        Column(
            modifier = Modifier
                .width(64.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (streak > 0) {
                Icon(
                    painter = painterResource(id = Res.fireIcon),
                    contentDescription = "streak",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${streak}d",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = colors.secondary
                )
            } else {
                Text(
                    text = habit.goal.value,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = colors.onBackground
                )
                Text(
                    text = habit.goal.unit,
                    color = colors.onSurfaceVariant,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.width(4.dp))
    }
}