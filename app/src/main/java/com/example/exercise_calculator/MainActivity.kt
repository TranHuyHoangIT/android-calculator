package com.example.exercise_calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentNumber: String = ""
    private var firstOperand: Int? = null
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setButtonListeners()
    }

    private fun setButtonListeners() {
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEquals, R.id.btnC, R.id.btnCE, R.id.btnBS
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(it) }
        }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button
        when (button.text.toString()) {
            in "0".."9" -> numberPressed(button.text.toString())
            "+", "-", "x", "/" -> operatorPressed(button.text.toString())
            "=" -> calculateResult()
            "C" -> clearAll()
            "CE" -> clearEntry()
            "BS" -> backspace()
        }
    }

    private fun numberPressed(number: String) {
        currentNumber += number
        tvResult.text = currentNumber
    }

    private fun operatorPressed(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstOperand = currentNumber.toInt()
            currentNumber = ""
            operator = op
        }
    }

    private fun calculateResult() {
        if (operator != null && currentNumber.isNotEmpty()) {
            val secondOperand = currentNumber.toInt()
            val result: String = when (operator) {
                "+" -> (firstOperand!! + secondOperand).toString()
                "-" -> (firstOperand!! - secondOperand).toString()
                "x" -> (firstOperand!! * secondOperand).toString()
                "/" -> {
                    when {
                        firstOperand == 0 && secondOperand == 0 -> "Result is undefined"
                        secondOperand == 0 -> "Cannot divide by zero"
                        else -> (firstOperand!! / secondOperand).toString()
                    }
                }
                else -> "0"
            }
            tvResult.text = result
            firstOperand = null
            operator = null
            currentNumber = if (result == "Cannot divide by zero" || result == "Result is undefined") "" else result
        }
    }

    private fun clearAll() {
        currentNumber = ""
        firstOperand = null
        operator = null
        tvResult.text = "0"
    }

    private fun clearEntry() {
        currentNumber = ""
        tvResult.text = "0"
    }

    private fun backspace() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            tvResult.text = if (currentNumber.isEmpty()) "0" else currentNumber
        }
    }
}
