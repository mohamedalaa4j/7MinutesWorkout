package com.mido.a7minutesworkout

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mido.a7minutesworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
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

    ///// Timers duration
    private var restTimerDuration: Long = 3
    private var exerciseTimerDuration: Long = 5


    ///// Exercises list
    private var exerciseList: ArrayList<ExerciseModel>? = null

    ///// Variable for current exercise position ID (by incrementing value ++1 it will be 0 which the first position in the Array )
    private var currentExercisePosition = -1

    ///// Text to speech object
    private var tts: TextToSpeech? = null

    ///// Media Player object
    private var player: MediaPlayer? = null

    ///// Exercise status RecyclerView Adapter
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //region ToolBar

        ///// Support actionbar and pass the toolbar id
        setSupportActionBar(binding?.toolbarExercise)

        ///// Show back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ///// The listener of back button in the toolbar
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed() // A go back function
        }
        //endregion

        ///// Initialize text to speech
        tts = TextToSpeech(this, this)

        ///// Fill exercises list using fun that returns the exercises
        exerciseList = Constants.defaultExerciseList()

        ///// Call ProgressBar Timer fun
        setupRestView()

        ///// Call Exercise status recyclerview fun
        setupExerciseStatusRecyclerView()

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //region #1 RestTimer functions

    ///// Setup CountDownTimer object
    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration*1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                // Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise.", Toast.LENGTH_SHORT).show()

                ///// Change position to the nest exercise
                currentExercisePosition++

                //region RecyclerView items appearance
                ///// Set isSelected to true for the finished exercise
                exerciseList!![currentExercisePosition].setIsSelected(true)

                ///// Tell the RV adapter that the data changed to re-call the methods & change the views
                exerciseAdapter!!.notifyDataSetChanged()
                //endregion

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
        binding?.tvUpComingExerciseName?.text =
            exerciseList!![currentExercisePosition + 1].getName()

        ///// Call Media Player function
        mediaPlayer()


        ///// Reset timer on destroying the activity
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        ///// Call TextToSpeech fun to speak "Rest 10 seconds" between exercises
        speakOut("Rest 10 seconds")

        setRestProgressBar()
    }

    //endregion

    //region #2 ExerciseTimer functions

    ///// Setup CountDownTimer object
    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration*1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                ///// When exercise finished go back to rest view & it's timer ** unless exercises have not finished yet
                if (currentExercisePosition < exerciseList?.size!! - 1) {

                    //region RecyclerView items appearance
                    ///// Set isSelected to true for the finished exercise
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)

                    ///// Tell the RV adapter that the data changed to re-call the methods & change the views
                    exerciseAdapter!!.notifyDataSetChanged()
                    //endregion

                    ///// Call RestTimer function
                    setupRestView()
                } else {
                  //  Toast.makeText( this@ExerciseActivity, "Congratulations! You have completed the 7 Minutes Workout.", Toast.LENGTH_SHORT ).show()

                      ///// Finish the activity
                    finish()

                    ///// Move to the finish activity
                    val intent = Intent (this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
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

        ///// Call TextToSpeech fun to speak exercise name when setting exercises views
        speakOut(exerciseList!![currentExercisePosition].getName())

        ///// Set exercise image (using getter fun as it's private property & can't be access directly)
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())

        ///// Set exercise name (using getter fun as it's private property & can't be access directly)
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()


        setExerciseProgressBar()
    }

    //endregion

    //region TextToSpeech functions

    ///// onInit fun for TextToSpeech & setting language
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language specified is not supported")
            }

        } else {
            Log.e("TTS", " Initialization failed !")
        }
    }

    ///// Speak fun
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_ADD, null, "")
    }
    //endregion

    //region Media Player function
    private fun mediaPlayer(){
        try {
            ///// URI (Uniform Resource Identifier) -> Identify sound file
            val soundURI = Uri.parse("android.resource://com.mido.a7minutesworkout/" + R.raw.press_start)

            ///// Use Player object & pass identified sound file
            player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping = false
            player?.start()

        }catch (e: Exception){e.printStackTrace()}
    }

    //endregion

    //region Exercise status recyclerView function
    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter

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

        ///// Shutdown TextToSpeech feature when activity destroyed
        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }

        ///// Stop MediaPlayer
        if (player != null){
            player!!.stop()
        }

    }

}