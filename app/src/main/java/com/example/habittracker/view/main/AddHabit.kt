package com.example.habittracker.view.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.Res
import com.example.habittracker.Res.icons
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily

@Composable
fun AddHabit() {

    val context = LocalContext.current
    var habitName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    val intervals = listOf("Everyday", "Alternative")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(intervals[0]) }

    val colors = listOf(
        AppColor.Yellow,
        AppColor.SkyBlue,
        AppColor.Blue,
        AppColor.Cyan,
        AppColor.Green,
        AppColor.Orange,
        AppColor.Purple,
        AppColor.Black
    )
    var selectedColor by remember { mutableStateOf(colors[0]) }
    var selectedIcon by remember { mutableStateOf<Int?>(icons[0]) }
    var isIconSelected by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColor.White)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Let's Start a new Habit",
                color = AppColor.Black,
                fontSize = 26.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
            )
        }

        TextForm(text = "Name")

        OutlinedTextField(
            value = habitName,
            onValueChange = { habitName = it },
            label = {
                Text(
                    "Type habit name",
                    fontSize = 18.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal
            ) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(18.dp),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = AppColor.Black,
                unfocusedTextColor = AppColor.Black,
                focusedContainerColor = AppColor.WhiteFade,
                unfocusedContainerColor = AppColor.WhiteFade,
                focusedIndicatorColor = AppColor.Blue,
                unfocusedIndicatorColor = AppColor.WhiteFade,
                focusedLabelColor = AppColor.BlackFade,
                unfocusedLabelColor = AppColor.BlackFade,
            )
        )
        TextForm(
            text = "Description",
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = {
                Text(
                    "Describe a habit",
                    fontSize = 18.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal
                ) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(18.dp),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = AppColor.Black,
                unfocusedTextColor = AppColor.Black,
                focusedContainerColor = AppColor.WhiteFade,
                unfocusedContainerColor = AppColor.WhiteFade,
                focusedIndicatorColor = AppColor.Blue,
                unfocusedIndicatorColor = AppColor.WhiteFade,
                focusedLabelColor = AppColor.BlackFade,
                unfocusedLabelColor = AppColor.BlackFade,
            )
        )

        TextForm(text = "Value")
       
        OutlinedTextField(
            value = value,
            onValueChange = { value = it},
            label = {
                Text(
                    text = "Enter a Value",
                    fontSize = 18.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal
            ) },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(18.dp),

            colors = TextFieldDefaults.colors(
                focusedTextColor = AppColor.Black,
                unfocusedTextColor = AppColor.Black,
                focusedContainerColor = AppColor.WhiteFade,
                unfocusedContainerColor = AppColor.WhiteFade,
                focusedIndicatorColor = AppColor.Blue,
                unfocusedIndicatorColor = AppColor.WhiteFade,
                focusedLabelColor = AppColor.BlackFade,
                unfocusedLabelColor = AppColor.BlackFade,
            )
        )
        TextForm(text = "Unit")

        OutlinedTextField(
            value = unit,
            onValueChange = { unit = it},
            label = {
                Text(
                    text = "Enter a unit",
                    fontSize = 18.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal
                ) },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(18.dp),

            colors = TextFieldDefaults.colors(
                focusedTextColor = AppColor.Black,
                unfocusedTextColor = AppColor.Black,
                focusedContainerColor = AppColor.WhiteFade,
                unfocusedContainerColor = AppColor.WhiteFade,
                focusedIndicatorColor = AppColor.Blue,
                unfocusedIndicatorColor = AppColor.WhiteFade,
                focusedLabelColor = AppColor.BlackFade,
                unfocusedLabelColor = AppColor.BlackFade,
            )
        )
        TextForm(text = "Interval")

        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(start = 15.dp, end = 15.dp, top = 6.dp),
            shape = RoundedCornerShape(18.dp),

            colors = TextFieldDefaults.colors(
                focusedTextColor = AppColor.Black,
                unfocusedTextColor = AppColor.Black,
                focusedContainerColor = AppColor.WhiteFade,
                unfocusedContainerColor = AppColor.WhiteFade,
                focusedIndicatorColor = AppColor.Blue,
                unfocusedIndicatorColor = AppColor.WhiteFade,
                focusedLabelColor = AppColor.BlackFade,
                unfocusedLabelColor = AppColor.BlackFade,
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            intervals.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = option
                        expanded = false
                    },
                    text = {
                        Text(
                            text = option,
                            fontSize = 18.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal
                        ) }
                )
            }
        }

        TextForm("Icons")

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(icons) { iconResId ->
                IconPicker(
                    iconResId = iconResId,
                    isIconSelected = iconResId == selectedIcon,
                    onIconSelected = { selectedIcon = iconResId }
                )
            }
        }


        TextForm("Background color")

        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            items(colors){ color->
                PickColor(
                    color = color,
                    isSelected = color == selectedColor,
                    onClick = { selectedColor = color}
                )
            }
        }

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                Toast.makeText(context, "Your habit is ready", Toast.LENGTH_SHORT).show()
            } ,
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(horizontal = 15.dp, vertical = 5.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = AppColor.Black,
                containerColor = AppColor.Blue
            )
        ) {
            Text(
                text = "Add Habit",
                fontFamily = poppinsFontFamily,
                fontSize = 20.sp,
                color = AppColor.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun TextForm(text: String) {
    Text(
        text = text,
        color = AppColor.BlackFade,
        fontSize = 15.sp,
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Normal,
        modifier = Modifier
            .padding(start = 15.dp, top = 8.dp)
    )
}

@Composable
fun PickColor(
    modifier: Modifier = Modifier,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ),
    )
}


@Composable
fun IconPicker(
    iconResId: Int,
    isIconSelected: Boolean,
    onIconSelected: (Int) -> Unit,
) {
    Box (
        modifier = Modifier
            .size(50.dp)
            .background(AppColor.WhiteFade, RoundedCornerShape(10.dp))
            .clickable { onIconSelected(iconResId) }
            .border(
                width = 2.dp,
                color = if (isIconSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "Icon",
            modifier = Modifier.size(26.dp)
        )
    }
}