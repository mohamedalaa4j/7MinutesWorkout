package com.mido.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mido.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null

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
    }
}