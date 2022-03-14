package com.mido.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mido.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ///// Start ExerciseActivity
        binding?.flStart?.setOnClickListener {
            startActivity(Intent(this, ExerciseActivity::class.java))
        }

        ///// Start BMIActivity
        binding?.flBMI?.setOnClickListener {
            startActivity(Intent(this, BMIActivity::class.java))
        }
    }

    ///// A clean way to un-assign binding to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}