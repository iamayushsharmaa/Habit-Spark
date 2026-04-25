package com.ayush.habitspark.view.onboarding

data class OnboardingPage(
    val title: String,
    val description: String,
    val emoji: String
)


val onboardingPage = listOf(
    OnboardingPage(
        title = "Welcome",
        description = "Build habits that stick.\nSmall steps lead to big changes.",
        emoji = "👋"
    ),
    OnboardingPage(
        title = "Track your progress",
        description = "See your streaks grow every day.\nNever lose momentum.",
        emoji = "🔥"
    ),
    OnboardingPage(
        title = "Stay consistent",
        description = "Daily reminders keep you on track.\nYour future self will thank you.",
        emoji = "🏆"
    )
)

