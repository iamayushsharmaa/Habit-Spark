package com.example.habittracker.view.main.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.poppinsFontFamily


@Composable
fun EmptyHabitsState(
    onAddClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // ✅ SVG illustration using Canvas
        EmptyIllustration()

        Spacer(Modifier.height(24.dp))

        Text(
            text = "No habits yet",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = colors.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Start building a better you.\nAdd your first habit today.",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = colors.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onAddClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.onBackground,
                contentColor = colors.background
            )
        ) {
            Text(
                text = "+ Add your first habit",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun EmptyIllustration() {
    val colors = MaterialTheme.colorScheme

    Canvas(
        modifier = Modifier
            .size(200.dp)
    ) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        // ✅ Outer circle background
        drawCircle(
            color = colors.surfaceVariant,
            radius = w / 2f
        )

        // ✅ Calendar base
        drawRoundRect(
            color = colors.onSurfaceVariant.copy(alpha = 0.15f),
            topLeft = Offset(cx - 55f, cy - 55f),
            size = Size(110f, 110f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f)
        )

        // ✅ Calendar header bar
        drawRoundRect(
            color = colors.onBackground.copy(alpha = 0.8f),
            topLeft = Offset(cx - 55f, cy - 55f),
            size = Size(110f, 30f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f)
        )

        // ✅ Grid dots — calendar days
        val dotRadius = 4f
        val startX = cx - 38f
        val startY = cy - 10f
        val spacing = 26f

        for (row in 0..2) {
            for (col in 0..3) {
                val dotX = startX + col * spacing
                val dotY = startY + row * spacing
                drawCircle(
                    color = colors.onSurfaceVariant.copy(alpha = 0.3f),
                    radius = dotRadius,
                    center = Offset(dotX, dotY)
                )
            }
        }

        // ✅ One highlighted dot — "today"
        drawCircle(
            color = colors.onBackground,
            radius = dotRadius + 1f,
            center = Offset(startX, startY)
        )

        // ✅ Plus icon circle — bottom right of illustration
        drawCircle(
            color = colors.onBackground,
            radius = 22f,
            center = Offset(cx + 52f, cy + 52f)
        )
        // Plus horizontal
        drawLine(
            color = colors.background,
            start = Offset(cx + 44f, cy + 52f),
            end = Offset(cx + 60f, cy + 52f),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
        // Plus vertical
        drawLine(
            color = colors.background,
            start = Offset(cx + 52f, cy + 44f),
            end = Offset(cx + 52f, cy + 60f),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
    }
}