package com.mido.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.mido.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.util.*

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

        ///// HistoryDao instance  ( Abstract fun of HistoryDao class)
        val dao = (application as WorkOutApp).db.historyDao()

        addDateToDatabase(dao)

    }

    ///// Add history to Room Database function
    private fun addDateToDatabase(historyDao: HistoryDao){

        //region Prepare the date

        ///// Instance of Calender class
        val c = Calendar.getInstance()

        ///// Get the time as Date object (type)
        val dateTime = c.time
        Log.e("Date", "" +dateTime)

        ///// Make a String out the Date type
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date", "" +dateTime)
        //endregion

        lifecycleScope.launch {

            historyDao.insert(HistoryEntity(date))
            Log.e("Date : ","Added")
        }
    }
}