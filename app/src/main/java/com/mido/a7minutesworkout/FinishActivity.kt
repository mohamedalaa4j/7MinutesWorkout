package com.mido.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mido.a7minutesworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //region ToolBar

        ///// Support actionbar and pass the toolbar id
        setSupportActionBar(binding?.toolbarFinishActivity)

        ///// Show back button
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        ///// The listener of back button in the toolbar
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed() // A go back function
        }
        //endregion

        binding?.btnFinish?.setOnClickListener {
            finish()
        }
    }
}