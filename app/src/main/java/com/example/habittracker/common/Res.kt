package com.example.habittracker.common

import com.example.habittracker.R
import com.example.habittracker.ui.theme.AppColor

object Res{

    val houseIconBlank = R.drawable.house_blank
    val houseIconFilled = R.drawable.house_filled
    val addIconBlank = R.drawable.add_blank
    val addIconFilled = R.drawable.add_filled
    val profileIconBlank = R.drawable.user_blank
    val profileIconFilled = R.drawable.user_filled

    val icons = listOf(
        R.drawable.fitness,
        R.drawable.health,
        R.drawable.hobbies,
        R.drawable.meditation,
        R.drawable.work,
        R.drawable.self_improvement,
        R.drawable.social,
        R.drawable.arrt,
        R.drawable.book,
        R.drawable.finance,
        R.drawable.water,
        R.drawable.cycling,
        R.drawable.alarm,
        R.drawable.headphone,
        R.drawable.heart,
        R.drawable.sleep,
        R.drawable.sun,
        R.drawable.running,
    )

    val colorList = listOf(
        AppColor.Yellow,
        AppColor.SkyBlue,
        AppColor.Blue,
        AppColor.Cyan,
        AppColor.Green,
        AppColor.Orange,
        AppColor.Purple,
        AppColor.Black
    )

    val iconMap = mapOf(
        "fitness" to R.drawable.fitness,
        "health" to R.drawable.health,
        "hobbies" to R.drawable.hobbies,
        "meditation" to R.drawable.meditation,
        "work" to R.drawable.work,
        "self_improvement" to R.drawable.self_improvement,
        "social" to R.drawable.social,
        "art" to R.drawable.arrt,
        "book" to R.drawable.book,
        "finance" to R.drawable.finance,
        "water" to R.drawable.water,
        "cycling" to R.drawable.cycling,
        "alarm" to R.drawable.alarm,
        "headphone" to R.drawable.headphone,
        "heart" to R.drawable.heart,
        "sleep" to R.drawable.sleep,
        "sun" to R.drawable.sun,
        "running" to R.drawable.running,
    )

    val colorMap = mapOf(
        "#FFC107" to AppColor.Yellow,
        "#03A9F4" to AppColor.SkyBlue,
        "#2196F3" to AppColor.Blue,
        "#00BCD4" to AppColor.Cyan,
        "#4CAF50" to AppColor.Green,
        "#FF5722" to AppColor.Orange,
        "#9C27B0" to AppColor.Purple,
        "#000000" to AppColor.Black
    )

    fun toResId(iconName: String) = iconMap[iconName] ?: R.drawable.fitness
    fun toColor(hex: String) = colorMap[hex] ?: AppColor.Black
}