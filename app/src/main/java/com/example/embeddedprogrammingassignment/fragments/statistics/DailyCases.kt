package com.example.embeddedprogrammingassignment.fragments.statistics

import android.util.Log
import java.util.Collections.max
import java.util.Collections.min
import kotlin.math.round
import kotlin.math.roundToInt

class DailyCases {

    lateinit var dailyCasesList: List<Int>

    fun setDailyValues(dailyCasesList: MutableList<Int>) {
        // Add database / API (????)listOf<Int>(50, 40, 60, 23, 56, 100, 80);
        this.dailyCasesList = dailyCasesList
        Log.d("Kotlin set daily values function @ DailyCases.Kt", dailyCasesList.toString())
    }

    fun setProgressValues(): MutableList<Int> {
        val total = dailyCasesList.sum()

        var progress = mutableListOf<Int>()

        dailyCasesList.forEach {

            val roundOff = ((it.toFloat() / total.toFloat()) *100.0).roundToInt()

            progress.add(roundOff)

        }

        Log.d("Kotlin set progress function dailycaseslist @ Statistics Fragment", dailyCasesList.toString())
        Log.d("Kotlin set progress function progress @ Statistics Fragment", progress.toString())
        Log.d("Kotlin set progress function progress sum @ Statistics Fragment", progress.sum().toString())
        return progress
    }

}