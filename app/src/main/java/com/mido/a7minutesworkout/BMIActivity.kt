package com.mido.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mido.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //region ToolBar

        ///// Support actionbar and pass the toolbar id
        setSupportActionBar(binding?.toolbarBmiActivity)

        ///// Set toolbar title
        supportActionBar?.title = "BMI CALCULATOR"

        ///// Show back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ///// The listener of back button in the toolbar
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
             onBackPressed() // A go back function
        }
        //endregion

        ///// Calculate button listener
        binding?.btnCalculateUnits?.setOnClickListener {
            ///// Check if EditTexts is empty first then execute the calculation
            if (validateMetricUnits()){

                ///// BMI formula variables
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100     // Height in Meters
                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()           // Weight in Kilo grams

                ///// Formula of BMI
                val bmi = weightValue / (heightValue * heightValue)

                ///// Function to format the bmi value & display info about the calculated BMI
                displayBMIResult(bmi)

            }else{
                Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //region BMI calculator functions
    private fun displayBMIResult(bmi: Float){

        //region BMI conditions info
        val bmiLabel : String
        val bmiDescription : String

        if (bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself..Eat more!"
        }else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself..Eat more!"
        }else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = " Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself..Eat more!"
        }else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = " Normal"
            bmiDescription = "Congratulations! You're in a good shape!"
        }else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself..Workout!"
        }else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = " Obese class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take better care of yourself..Workout!"
        }else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = " Obese class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition..Workout!"
        }else{
            bmiLabel = " Obese class ||| (Very severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition..Workout!"

        }
        //endregion

        ///// Format the value
        // setScale 1st parameter -> how many numbers after the decimal point
        // setScale 2nd parameter -> decimal rounding
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        ///// Display BMI info
        binding?.llDisplayMBIResult?.visibility = View.VISIBLE
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
        binding?.tvBMIValue?.text = bmiValue

    }

    ///// Check if EditTexts is empty
    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false

        }else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    //endregion

    override fun onDestroy() {
        super.onDestroy()

        ///// A clean way to un-assign binding to avoid memory leak
        binding = null

    }
}