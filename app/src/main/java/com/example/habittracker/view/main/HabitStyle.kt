@file:Suppress("UNREACHABLE_CODE")

package com.example.habittracker.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.example.habittracker.R
import com.example.habittracker.data.remote.response.HabitResponse
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.google.android.play.integrity.internal.f
import io.ktor.util.debug.initContextInDebugMode

@Composable
fun HabitStyle(
    //habit: HabitResponse,
    onClick: () -> Unit
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(shape = RoundedCornerShape(12.dp), color = AppColor.WhiteFade)
            .clickable { onClick() },
    ){
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(46.dp)
                .align(Alignment.CenterVertically)
                .background(shape = RoundedCornerShape(12.dp), color = AppColor.Blue),
            contentAlignment = Alignment.Center,
        ){
            Icon(
                painter = painterResource(id = R.drawable.fitness),
                contentDescription = "Habit Icon",
                tint = AppColor.Black,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(9.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column (
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .padding(vertical = 9.dp)
        ){
            Text(
                text = "Fitness",
                fontFamily = poppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(top = 0.dp)
            )
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 3.dp)
                    .background(shape = RoundedCornerShape(4.dp), color = AppColor.Orange),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Active", // habit status -> Done, Active
                    fontFamily = poppinsFontFamily,
                    fontSize = 12.sp,
                    color = AppColor.Black,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 1.dp)
                )
            }
        }

        Spacer(Modifier.weight(1f))

        VerticalDivider(
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "25", // value
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 21.sp,
                textAlign = TextAlign.Center,
                color = AppColor.Black
            )
            Text(
                text = "min", // unit
                color = AppColor.BlackFade,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
//
//@Preview
//@Composable
//private fun HabitPrev() {
//    HabitStyle(onClick = {})
//}
