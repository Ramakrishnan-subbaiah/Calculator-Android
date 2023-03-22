package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var result: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)
    }
    fun onDigit(view: View){
        result?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onClear() {
        result?.text=""
    }
    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot){
            result?.append((view as Button).text)
            lastNumeric = false
            lastDot = true
        }
    }
    private fun isOperatorAdded(value : String): Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
    fun onOperator(view: View){
        result?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                result?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }

        }
    }
    fun onEqual() {
        if (lastNumeric) {
            var tvValue = result?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    result?.text = removeDotZero((one.toDouble() - two.toDouble()).toString())
                }else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    result?.text = removeDotZero((one.toDouble() + two.toDouble()).toString())
                }else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    result?.text = removeDotZero((one.toDouble() * two.toDouble()).toString())
                }else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    result?.text = removeDotZero((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeDotZero(resultF : String): String{
        var value = resultF
        if(resultF.contains(".0")){
            value = resultF.substring(0 , resultF.length-2)
        }
        return value
    }


}