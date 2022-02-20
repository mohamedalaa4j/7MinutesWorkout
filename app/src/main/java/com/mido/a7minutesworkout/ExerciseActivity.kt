package com.mido.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.mido.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null

    ///// Resting & relaxing timer
    private var restTimer: CountDownTimer? = null

    ///// Rest time for resting timer
    private var  restProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ///// Support actionbar and pass the toolbar id
        setSupportActionBar(binding?.toolbarExercise)

        ///// Show back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ///// The listener of back button in the toolbar
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed() // A go back function
        }

        setupRestView()

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress ).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise.", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }

    ///// Check & cancel RestTimer if it's not null in case back button pressed -> and call setRestProgressBar()
    private fun setupRestView(){

        ///// Reset timer on destroying the activity
        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()

        ///// A clean way to un-assign binding to avoid memory leak
        binding = null

        ///// Reset timer on destroying the activity
        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
    }
}