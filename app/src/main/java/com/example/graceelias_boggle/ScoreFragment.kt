package com.example.graceelias_boggle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.graceelias_boggle.databinding.FragmentScoreBinding

class ScoreFragment : Fragment()
{
    private var _binding: FragmentScoreBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var gameResetter: OnDataPass


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentScoreBinding.inflate(layoutInflater, container, false)

        binding.newGameButton.setOnClickListener{
            resetGame()
            binding.score.setText("Score: 0")
        }

        return binding.root
    }

    fun updateScore(totalScore: Int)
    {
        binding.score.setText("Score: " + totalScore)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        gameResetter = context as OnDataPass
    }

    private fun resetGame()
    {
        gameResetter.onResetGame()
    }

}