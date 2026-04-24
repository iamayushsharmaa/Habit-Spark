package com.example.habittracker.core.onboarding

import android.content.Context

object OnboardingPreference {

    private const val PREF_NAME = "habit_pref"
    private const val KEY_ONBOARDING_DONE = "onboarding_done"

    fun isOnboardingDone(context: Context): Boolean {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getBoolean(KEY_ONBOARDING_DONE, false);
    }


    fun setOnBoardingDone(context: Context) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putBoolean(KEY_ONBOARDING_DONE, true).apply()
    }

}