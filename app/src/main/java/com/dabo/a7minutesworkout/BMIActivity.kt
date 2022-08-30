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

    private var binding: ActivityBmiBinding? = null

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

        binding?.btnCalculateUnits?.setOnClickListener{
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
        }

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


}