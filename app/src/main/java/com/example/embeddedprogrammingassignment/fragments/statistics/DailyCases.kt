package com.example.embeddedprogrammingassignment.fragments.statistics

import android.util.Log
import java.util.Collections.max
import java.util.Collections.min
import kotlin.math.round
import kotlin.math.roundToInt

class DailyCases {

    lateinit var dailyCasesList: List<Int>

    fun setDailyValues(dailyCasesList: MutableList<Int>) {
        this.dailyCasesList = dailyCasesList
    }

    fun setProgressValues(): MutableList<Int> {
        val total = dailyCasesList.sum()
        var progress = mutableListOf<Int>()
        dailyCasesList.forEach {
            val roundOff = ((it.toFloat() / total.toFloat()) *100.0).roundToInt()
            progress.add(roundOff)
        }

        return progress
    }

}