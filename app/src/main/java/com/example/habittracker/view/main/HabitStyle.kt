@file:Suppress("UNREACHABLE_CODE")

package com.example.habittracker.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.R
import com.example.habittracker.data.HabitData
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.google.android.play.integrity.internal.f

@Composable
fun HabitStyle(allHabits: HabitData) {

    var checkedState by remember { mutableStateOf(false) }

    val checkBoxColor = if (isSystemInDarkTheme()) {
        Color.White // For Dark Mode, use white
    } else {
        Color.Black // For Light Mode, use black
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = { checkedState = it },
            colors = CheckboxDefaults.colors(
                checkedColor = checkBoxColor,
                uncheckedColor = Color.Gray,
                checkmarkColor  = Color.White
            )
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF191919)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 7.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = Color(0xFF191919)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row (
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = allHabits.habitName,
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(10.dp)
                    )

                }
                Row (
                    modifier = Modifier
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.alarm ),
                        contentDescription = "",
                    )
                    Text(
                        text = "10:12",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun MainPreview(){

}