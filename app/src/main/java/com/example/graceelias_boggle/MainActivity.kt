package com.example.graceelias_boggle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), OnDataPass
{
    private lateinit var totalScoreUpdater: OnDataPass
    private var totalScore = 0
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onUpdateScore(score: Int)
    {
        totalScore += score
        updateTotalScore(totalScore)
    }

    override fun onUpdateTotalScore(totalScore: Int) {

    }

    private fun updateTotalScore(totalScore: Int)
    {
        totalScoreUpdater.onUpdateTotalScore(totalScore)
    }

}