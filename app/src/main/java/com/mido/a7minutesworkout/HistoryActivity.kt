package com.mido.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mido.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //region ToolBar

        ///// Support actionbar and pass the toolbar id
        setSupportActionBar(binding?.toolbarHistoryActivity)

        ///// Set toolbar title
        supportActionBar?.title = "HISTORY"

        ///// Show back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ///// The listener of back button in the toolbar
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed() // A go back function
        }
        //endregion

        //region Fetch data from Room DB

        ///// HistoryDao instance
        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)
        //endregion

    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {

            ///// collect to determine what to do when we get -it/allCompletedDatesList- which is list of HistoryEntity type
            historyDao.fetchAllDates().collect { allCompletedDatesList ->
                /* Testing
                for (i in allCompletedDatesList){
                    Log.e("Date: ",""+i)
                }
                */
                if(allCompletedDatesList.isNotEmpty()){
                    //region Setup the View
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.INVISIBLE
                    //endregion

                    //region Setup the RecyclerView
                    ///// Use this@HistoryActivity as we are in lifecycleScope context (Coroutine)
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity, )

                    val dates = ArrayList<String>()
                    for (date in allCompletedDatesList){
                        dates.add(date.date)
                    }

                    val historyAdapter = HistoryAdapter(dates)

                    binding?.rvHistory?.adapter = historyAdapter

                    //endregion

                }else{
                    //region Setup the Views
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                    //endregion

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}