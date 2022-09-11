package com.dabo.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.dabo.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        makeVisibleMetricUnitsViews()

        binding?.rgUnits?.setOnCheckedChangeListener{ _, checkedId: Int ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsViews()
            }else{
                makeVisibleUsUnitsViews()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener{
            calculateUnits()
        }

    }

    private fun makeVisibleMetricUnitsViews(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitInch?.visibility = View.GONE
        binding?.tilUsMetricUnitFeet?.visibility = View.GONE
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE

        binding?.etMetricHeight?.text!!.clear()
        binding?.etMetricWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsViews(){
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilUsMetricUnitInch?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitFeet?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE

        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()
        binding?.etUsMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }


    private fun displayBMIResult(bmi: Float){

        val bmiLabel : String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "매우 심각한 저체중이에요!"
            bmiDescription = "자기 자신을 조금 더 챙겨 줄 필요가 있을 것 같아요. 좀 더 먹으세요!!"
        }else if(bmi.compareTo(15f)>0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "심각한 저체중입니다."
            bmiDescription = "자기 자신을 조금 더 챙겨 줄 필요가 있을 것 같아요. 좀 더 먹으세요!!"
        }else if(bmi.compareTo(16f)>0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "저체중입니다."
            bmiDescription = "자기 자신을 조금 더 챙겨 줄 필요가 있을 것 같아요. 좀 더 먹으세요!!"
        }else if(bmi.compareTo(18.5f)>0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "정상입니다."
            bmiDescription = "축하합니다. 훌륭한 몸매의 소유자이시군요!"
        }else if(bmi.compareTo(25f)>0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "과체중입니다."
            bmiDescription = "자기 자신을 조금 더 챙겨 줄 필요가 있을 것 같아요. 운동을 하시는 것을 권장드려요"
        }else if(bmi.compareTo(30f)>0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "비만입니다. 1급이에요."
            bmiDescription = "자기 자신을 조금 더 챙겨 줄 필요가 있을 것 같아요. 운동을 하시는 것을 권장드려요"
        }else if(bmi.compareTo(35f)>0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "비만입니다. 2급이에요."
            bmiDescription = "매우 심각한 수준입니다. 당장 움직이세요!!"
        }else{
            bmiLabel = "비만입니다. 3급이에요"
            bmiDescription = "매우 심각한 수준입니다. 당장 움직이세요!!"
        }

        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription


    }

    private fun validateMetricUnits():Boolean{
        var isValid = true

        if(binding?.etMetricWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etMetricWeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val heightValue : Float = binding?.etMetricHeight?.text.toString().toFloat() / 100
                val weightValue : Float = binding?.etMetricWeight?.text.toString().toFloat()
                val bmi = weightValue / (heightValue*heightValue)

                displayBMIResult(bmi)
            }else{
                Toast.makeText(
                    this@BMIActivity, "Please enter valid values.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            if(validateUsUnits()){
                val usUnitsHeightValueFeet: String =
                    binding?.etUsMetricUnitHeightFeet?.text.toString()
                val usUnitsHeightValueInch: String =
                    binding?.etUsMetricUnitHeightInch?.text.toString()
                val usUnitsWeightValue: Float =
                    binding?.etUsMetricUnitWeight?.text.toString().toFloat()

                val heightValue =
                    usUnitsHeightValueInch.toFloat() + usUnitsHeightValueFeet.toFloat() * 12

                val bmi = 703 * (usUnitsWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi)
            }else{
                Toast.makeText(
                    this@BMIActivity, "Please enter valid values.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validateUsUnits():Boolean {
        var isValid = true

        when{
            binding?.etUsMetricUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }

}