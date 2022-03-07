package com.mido.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.mido.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null

    //region Variables

    ///// Resting & relaxing timer
    private var restTimer: CountDownTimer? = null

    ///// Rest time for resting timer
    private var  restProgress = 0



    ///// Exercise timer
    private var exerciseTimer: CountDownTimer? = null

    ///// Rest time for exercise timer
    private var  exerciseProgress = 0


    ///// Exercises list
    private var exerciseList : ArrayList<ExerciseModel>? = null

    ///// Variable for current exercise position ID (by incrementing value ++1 it will be 0 which the first position in the Array )
    private var currentExercisePosition = -1

    //endregion

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

        ///// Call ProgressBar Timer fun
        setupRestView()

        ///// Fill exercises list using fun that returns the exercises
        exerciseList = Constants.defaultExerciseList()

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //region #1 RestTimer functions

    ///// Setup CountDownTimer object
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress ).toString()
            }

            override fun onFinish() {
                // Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise.", Toast.LENGTH_SHORT).show()

                ///// Call Exercise views & it's timer start function
                setupExerciseView()

                ///// Change position to the nest exercise
                currentExercisePosition++
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

    //endregion

    //region #2 ExerciseTimer functions

    ///// Setup CountDownTimer object
    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress

       exerciseTimer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress ).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "30 Seconds are over, let's go to the rest view.", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }

    private fun setupExerciseView(){

        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.text = "Exercise Name"
        binding?.flExerciseView?.visibility = View.VISIBLE

        ///// Reset the timer
        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        setExerciseProgressBar()
    }

    //endregion


    override fun onDestroy() {
        super.onDestroy()

        ///// A clean way to un-assign binding to avoid memory leak
        binding = null

        ///// Reset timer on destroying the activity
        if (restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        ///// Exercise timer on destroying the activity
        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

    }

}