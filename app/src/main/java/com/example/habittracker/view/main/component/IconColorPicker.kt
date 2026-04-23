package com.example.habittracker.view.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.habittracker.common.Res


@Composable
fun PickColor(
    modifier: Modifier = Modifier,
    colorHex: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val color = Color(android.graphics.Color.parseColor(colorHex))

    Box(
        modifier = modifier
            .size(40.dp)
            .background(color, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .border(
                width = 2.dp,
                color = if (isSelected) colors.onSurface else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ),
    )
}

@Composable
fun IconPicker(
    iconName: String,
    isIconSelected: Boolean,
    onIconSelected: (String) -> Unit,
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .size(50.dp)
            .background(colors.surfaceVariant, RoundedCornerShape(10.dp))
            .clickable { onIconSelected(iconName) }
            .border(
                width = 2.dp,
                color = if (isIconSelected) colors.primary else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = Res.toResId(iconName)),
            contentDescription = iconName,
            modifier = Modifier.size(26.dp),
            tint = colors.onSurface
        )
    }
}