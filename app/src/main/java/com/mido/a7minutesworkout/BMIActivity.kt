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

    //region Which units is selected
    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }
    ///// A variable to hold a value to determine which units is selected
    private var currentVisibleView: String = METRIC_UNITS_VIEW
    //endregion

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

        //region RadioGroup

        ///// Set metric units as default
        makeVisibleMetricUnitsView()

        ///// Listener for the checked one
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else {
                makeVisibleUsUnitsView()
            }
        }
        //endregion

        ///// Calculate button listener
        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }


    //region BMI calculator functions

    ///// Calculator for METRIC/US unites
    private fun calculateUnits(){

        ///// Check which units selected
        if (currentVisibleView == METRIC_UNITS_VIEW){

            ///// METRIC_UNITS_VIEW
                ///// Check if editTexts empty first
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


        }else

            ///// US_UNITS_VIEW
                ///// Check if editTexts empty first
            if (validateUsUnits()){
            val usUnitHeightValueFeet: String = binding?.etUsMetricUnitHeightFeet?.text.toString()
            val usUnitHeightValueInch: String = binding?.etUsMetricUnitHeightInch?.text.toString()
            val usUnitWeightValue: Float = binding?.etUsMetricUnitWeight?.text.toString().toFloat()

            val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

            val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

            ///// Function to format the bmi value & display info about the calculated BMI
            displayBMIResult(bmi)

        }else {
            Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_SHORT).show()
        }
    }

    ///// Check if EditTexts is empty (METRIC)
    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false

        }else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    ///// Check if EditTexts is empty (US)
    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> { isValid = false }

            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() -> { isValid = false }

            binding?.etUsMetricUnitWeight?.text.toString().isEmpty() -> { isValid = false }
        }

        return isValid
    }

    ///// Function to format the bmi value & display info about the calculated BMI
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
    //endregion

    //region RadioGroup set Views functions

    ///// Metric units choice
    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW

        ///// Show metric units editTexts
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE

        ///// Hide US units editTexts
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE

        ///// Clear editTexts
        binding?.etMetricUnitHeight?.text?.clear()
        binding?.etMetricUnitWeight?.text?.clear()

        ///// Hide BMI results
        binding?.llDisplayMBIResult?.visibility = View.INVISIBLE
    }

    ///// US units choice
    private fun makeVisibleUsUnitsView(){
        currentVisibleView = US_UNITS_VIEW

        ///// Hide metric units editTexts
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE

        ///// Show US units editTexts
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE

        ///// Clear editTexts
        binding?.etUsMetricUnitHeightFeet?.text?.clear()
        binding?.etUsMetricUnitHeightInch?.text?.clear()
        binding?.etUsMetricUnitWeight?.text?.clear()

        ///// Hide BMI results
        binding?.llDisplayMBIResult?.visibility = View.INVISIBLE
    }
    //endregion

    override fun onDestroy() {
        super.onDestroy()

        ///// A clean way to un-assign binding to avoid memory leak
        binding = null

    }
}