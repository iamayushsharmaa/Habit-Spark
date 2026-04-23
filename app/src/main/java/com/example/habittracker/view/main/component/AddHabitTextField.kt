package com.example.habittracker.view.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.poppinsFontFamily


@Composable
fun HabitTextField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column {
        SectionTitle(title)

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Enter $title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            shape = RoundedCornerShape(18.dp),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                color = colors.onSurface
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colors.onSurface,
                unfocusedTextColor = colors.onSurface,
                focusedContainerColor = colors.surfaceVariant,
                unfocusedContainerColor = colors.surfaceVariant,
                focusedIndicatorColor = colors.primary,
                unfocusedIndicatorColor = colors.outline,
                focusedLabelColor = colors.onSurfaceVariant,
                unfocusedLabelColor = colors.onSurfaceVariant,
            )
        )
    }
}


@Composable
fun SectionTitle(text: String) {
    val colors = MaterialTheme.colorScheme
    Text(
        text =
            text,
        color = colors.onSurfaceVariant,
        fontSize = 13.sp,
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(start = 15.dp, top = 8.dp)
    )
}