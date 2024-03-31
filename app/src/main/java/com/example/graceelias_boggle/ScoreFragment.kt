package com.example.graceelias_boggle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.graceelias_boggle.databinding.FragmentScoreBinding

class ScoreFragment : Fragment(), OnDataPass
{
    private var _binding: FragmentScoreBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentScoreBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onUpdateScore(score: Int) {
        TODO("Not yet implemented")
    }
    override fun onUpdateTotalScore(totalScore: Int) {
        binding.score.setText("Score: " + totalScore)
    }

    fun updateScore(totalScore: Int)
    {

    }


    interface OnScoreToMain{

    }

}