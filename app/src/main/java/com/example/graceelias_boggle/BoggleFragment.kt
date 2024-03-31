package com.example.graceelias_boggle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.graceelias_boggle.databinding.FragmentBoggleBinding
import kotlin.random.Random

class BoggleFragment : Fragment()
{
    private var _binding: FragmentBoggleBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var scoreUpdater: OnDataPass

    private var dictionary =context?.assets?.open("words.txt")?.
        bufferedReader()?.useLines {lines -> lines.map { it.lowercase() }.toSet()
    }

    private val alphabet = "abcdefghijklmnopqrstuvwxyz"

    private var word = ""

    private var wordButtons = mutableListOf<Pair<Int, Int>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentBoggleBinding.inflate(layoutInflater, container, false)

        var buttons =
            listOf(
                listOf(binding.button00, binding.button01, binding.button02, binding.button03),
                listOf(binding.button10, binding.button11, binding.button12, binding.button13),
                listOf(binding.button20, binding.button21, binding.button22, binding.button23),
                listOf(binding.button30, binding.button31, binding.button32, binding.button33))

        for(i in 0..3)
        {
            for(j in 0..3) {
                buttons[i][j].setText(alphabet.get(Random.nextInt(0, 26)) + "")
                buttons[i][j].setOnClickListener {
                    word += buttons[i][j].text
                    binding.word.setText(word)
                    wordButtons.add(Pair(i, j))
                    for(i2 in 0..3) {
                        for (j2 in 0..3) {
                            if(i2 == i || i2 == i-1 || i2 == i+1) {
                                if(j2 == j || j2 == j-1 || j2 == j+1) {
                                    if(i2 == i && j2 == j) {
                                        buttons[i2][j2].isEnabled = false }
                                    else {
                                        buttons[i2][j2].isEnabled = true } }
                                else {
                                    buttons[i2][j2].isEnabled = false } }
                            else {
                                buttons[i2][j2].isEnabled = false } } }
                    for(button in wordButtons) {
                        buttons[button.first][button.second].isEnabled = false } } } }

        binding.submitButton.setOnClickListener{
            var score = 0
            var isSpecialLetterUsed = false
            var vowelCount = 0
            if(dictionary!!.contains(word) )
            {
                if(word.length > 3) {

                    for (letter in word) {
                        if (letter in "aeiou") {
                            score += 5
                            vowelCount++
                        } else {
                            score += 1
                            if (letter in "szpxq") {
                                isSpecialLetterUsed = true
                            }
                        }
                    }
                    if (isSpecialLetterUsed) {
                        score *= 2
                    }
                    if(vowelCount > 1)
                    {
                        updateScore(score)
                    }
                    else
                    {
                        updateScore(-10)
                        Toast.makeText(
                            requireContext(),
                            R.string.not_enough_vowels,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                else
                {
                    updateScore(-10)
                    Toast.makeText(
                        requireContext(),
                        R.string.too_short,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else
            {
                updateScore(-10)
                Toast.makeText(
                    requireContext(),
                    R.string.invalid_word,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.clearButton.setOnClickListener{
            word = ""
            wordButtons.clear()
            for(i in 0..3) {
                for (j in 0..3) {
                    buttons[i][j].isEnabled = true } } }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scoreUpdater = context as OnDataPass
    }

    private fun updateScore(score: Int)
    {
        scoreUpdater.onUpdateScore(score)
    }
}