package com.example.graceelias_boggle

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.graceelias_boggle.databinding.FragmentScoreBinding
import java.util.Objects
import kotlin.math.sqrt

class ScoreFragment : Fragment()
{
    private var _binding: FragmentScoreBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var gameResetter: OnDataPass

    private var sensorManager: SensorManager? = null

    private var currentAcceleration = 0f

    private var lastMovementTime = 0L
    private var cooldownDuration = 3000L // 3 second cooldown

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

        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        Objects.requireNonNull(sensorManager)!!
            .registerListener(sensorListener, sensorManager!!
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)

        currentAcceleration = SensorManager.GRAVITY_EARTH

        return binding.root
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()

            if (currentAcceleration > 15)
            {
                if(System.currentTimeMillis() - lastMovementTime >= cooldownDuration)
                {
                    resetGame()
                    binding.score.setText("Score: 0")
                    lastMovementTime = System.currentTimeMillis()
                }
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
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
        Toast.makeText(
            requireContext(),
            R.string.reset_game,
            Toast.LENGTH_LONG
        ).show()
        gameResetter.onResetGame()
    }

}