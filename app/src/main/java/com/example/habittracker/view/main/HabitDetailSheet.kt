package com.example.habittracker.view.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.R
import com.example.habittracker.common.Res
import com.example.habittracker.data.models.HabitResponse
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily

@Composable
fun HabitDetailSheet(
    habit: HabitResponse,
    isCompleted: Boolean,
    isLocked: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .background(color = AppColor.White),
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onDeleteClick() },
                modifier = Modifier
                    .size(44.dp)
                    .background(shape = RoundedCornerShape(14.dp), color = AppColor.WhiteFade),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = AppColor.RedBrown,
                    containerColor = AppColor.WhiteFade
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete_icon),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentDescription = "delete icon"
                )
            }
            Spacer(Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            color = Res.toColor(habit.iconBackground),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = Res.toResId(habit.icon)),
                        contentDescription = habit.name,
                        tint = AppColor.Black,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = habit.name,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = AppColor.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )

                Text(
                    text = when {
                        isLocked -> "Locked"
                        isCompleted -> "Completed"
                        else -> "Pending"
                    },
                    color = when {
                        isLocked -> AppColor.Gray
                        isCompleted -> AppColor.Green
                        else -> AppColor.Orange
                    },
                    fontSize = 14.sp
                )
            }
            Spacer(Modifier.weight(1f))

            IconButton(
                modifier = Modifier
                    .size(44.dp)
                    .background(shape = RoundedCornerShape(14.dp), color = AppColor.WhiteFade),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = AppColor.Black,
                    containerColor = AppColor.WhiteFade
                ),
                onClick = { onDismissRequest() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cross_icon),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    contentDescription = "Cross icon"
                )
            }
        }

        // graph track

        Spacer(Modifier.height(20.dp))
        BoxDesign(text = habit.description)

        Text(
            text = "Target",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            color = AppColor.Black,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 5.dp)
        )

        OutlinedTextField(
            value = "${habit.goal.value} ${habit.goal.unit}",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = false,
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = AppColor.Black
            ),
            shape = RoundedCornerShape(14.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = Res.toResId(habit.icon)),
                    contentDescription = "habit icon",
                    tint = AppColor.Black
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = AppColor.Black,
                unfocusedTextColor = AppColor.Black,
                focusedContainerColor = AppColor.WhiteFade,
                unfocusedContainerColor = AppColor.WhiteFade,
                focusedIndicatorColor = AppColor.Black,
                unfocusedIndicatorColor = AppColor.BlackFade,
                disabledContainerColor = AppColor.WhiteFade,
                disabledTextColor = AppColor.Black,
                disabledIndicatorColor = AppColor.BlackFade,
                focusedLabelColor = AppColor.BlackFade,
                unfocusedLabelColor = AppColor.BlackFade,
            )
        )
        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                if (!isLocked) {
                    onCompleteClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = if (isCompleted) AppColor.Black else AppColor.White,
                containerColor = if (isCompleted) AppColor.WhiteFade else AppColor.Black
            ),
            border = if (isCompleted) BorderStroke(1.dp, AppColor.BlackFade) else null,

            ) {
            Text(
                text = when {
                    isLocked -> "Locked"
                    isCompleted -> "Mark Incomplete"
                    else -> "Finish"
                },
                fontFamily = poppinsFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }

    }
}

@Composable
fun BoxDesign(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 14.dp, vertical = 3.dp)
            .background(shape = RoundedCornerShape(16.dp), color = AppColor.WhiteFade)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 10.dp),
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = AppColor.Black,
            textAlign = TextAlign.Center,
            softWrap = true
        )
    }
}