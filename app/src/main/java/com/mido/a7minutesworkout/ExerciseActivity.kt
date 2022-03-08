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
    private var restProgress = 0


    ///// Exercise timer
    private var exerciseTimer: CountDownTimer? = null

    ///// Rest time for exercise timer
    private var exerciseProgress = 0


    ///// Exercises list
    private var exerciseList: ArrayList<ExerciseModel>? = null

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


        ///// Fill exercises list using fun that returns the exercises
        exerciseList = Constants.defaultExerciseList()

        ///// Call ProgressBar Timer fun
        setupRestView()

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //region #1 RestTimer functions

    ///// Setup CountDownTimer object
    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                // Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise.", Toast.LENGTH_SHORT).show()

                ///// Change position to the nest exercise
                currentExercisePosition++

                ///// Call Exercise views & it's timer start function
                setupExerciseView()
            }

        }.start()
    }

    ///// Check & cancel RestTimer if it's not null in case back button pressed -> and call setRestProgressBar()
    private fun setupRestView() {

        ///// Invert views of the exercises in rest time
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE

        ///// Set exercise name label
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpComingExerciseName?.visibility = View.VISIBLE
        binding?.tvUpComingExerciseName?.text = exerciseList!![currentExercisePosition +1 ].getName()


        ///// Reset timer on destroying the activity
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }

    //endregion

    //region #2 ExerciseTimer functions

    ///// Setup CountDownTimer object
    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                ///// When exercise finished go back to rest view & it's timer ** unless exercises have not finished yet
                if (currentExercisePosition < exerciseList?.size!! -1){
                    ///// Call RestTimer function
                    setupRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity, "Congratulations! You have completed the 7 Minutes Workout.",
                        Toast.LENGTH_SHORT).show()
                }
            }

        }.start()
    }

    private fun setupExerciseView() {

        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE

        ///// Set exercise name label to INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpComingExerciseName?.visibility = View.INVISIBLE

        ///// Reset the timer
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        ///// Set exercise image (using getter fun as it's private property & can't be access directly)
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())

        ///// Set exercise name (using getter fun as it's private property & can't be access directly)
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()


        setExerciseProgressBar()
    }

    //endregion


    override fun onDestroy() {
        super.onDestroy()

        ///// A clean way to un-assign binding to avoid memory leak
        binding = null

        ///// Reset timer on destroying the activity
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        ///// Exercise timer on destroying the activity
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

    }

}