@file:Suppress("UNREACHABLE_CODE")

package com.example.habittracker.view.main

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
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.common.Res
import com.example.habittracker.data.models.HabitResponse
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily

@Composable
fun HabitStyle(
    habit: HabitResponse,
    isCompleted: Boolean,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(70.dp)
            .background(shape = RoundedCornerShape(14.dp), color = AppColor.WhiteFade)
            .clickable(enabled = !isLocked) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(10.dp))

        // ✅ Dynamic icon + background
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
                tint = AppColor.White,
                modifier = Modifier
                    .size(26.dp)
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
                color = AppColor.Black,
                maxLines = 1
            )
            Spacer(Modifier.height(3.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        shape = RoundedCornerShape(4.dp),
                        color = when {
                            isLocked -> AppColor.Gray.copy(alpha = 0.3f)
                            isCompleted -> AppColor.Green.copy(alpha = 0.2f)
                            else -> AppColor.Orange.copy(alpha = 0.2f)
                        }
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
                    color = when {
                        isLocked -> AppColor.Gray
                        isCompleted -> AppColor.Green
                        else -> AppColor.Orange
                    },
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        VerticalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = AppColor.BlackFade.copy(alpha = 0.2f)
        )

        Column(
            modifier = Modifier
                .width(64.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = habit.goal.value,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = AppColor.Black
            )
            Text(
                text = habit.goal.unit,
                color = AppColor.BlackFade,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.width(8.dp))
    }
}