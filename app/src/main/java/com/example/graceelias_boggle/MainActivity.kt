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

        if(totalScore < 0)
            totalScore = 0

        val scoreFragment = supportFragmentManager.findFragmentById(R.id.score_fragment_container) as? ScoreFragment
        scoreFragment?.updateScore(totalScore)
    }

    override fun onResetGame() {
        totalScore = 0

        val boggleFragment = supportFragmentManager.findFragmentById(R.id.boggle_fragment_container) as? BoggleFragment
        boggleFragment?.resetGame()
    }




}