package com.mido.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mido.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener {
            Toast.makeText(this, "hi", Toast.LENGTH_LONG).show()
        }
    }

    // clean way to un-assign binding to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}