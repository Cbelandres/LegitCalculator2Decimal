package com.example.bottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.DecimalFormat

class CalculatorFragment : Fragment() {

    private lateinit var displayInput: TextView
    private lateinit var displayOutput: TextView
    private var input1: String = ""
    private var input2: String = ""
    private var operator: String? = null
    private val decimalFormat = DecimalFormat("0.00") // To format the result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        // Initialize views
        displayInput = view.findViewById(R.id.displayInput)
        displayOutput = view.findViewById(R.id.displayOutput)

        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonEquals, R.id.buttonClear
        )

        for (id in buttons) {
            view.findViewById<Button>(id).setOnClickListener { onButtonClick(it as Button) }
        }

        return view
    }

    private fun onButtonClick(button: Button) {
        when (button.id) {
            R.id.buttonClear -> clear()
            R.id.buttonEquals -> calculate()
            R.id.buttonAdd -> setOperator("+")
            R.id.buttonSubtract -> setOperator("-")
            R.id.buttonMultiply -> setOperator("*")
            R.id.buttonDivide -> setOperator("/")
            else -> appendNumber(button.text.toString())
        }
    }

    private fun appendNumber(number: String) {
        if (operator == null) {
            // First input
            input1 += number
            displayInput.text = input1
        } else {
            // Second input
            input2 += number
            displayInput.text = "$input1 $operator $input2"
        }
    }

    private fun setOperator(op: String) {
        if (input1.isNotEmpty() && operator == null) {
            operator = op
            displayInput.text = "$input1 $operator"
        }
    }

    private fun calculate() {
        if (input1.isNotEmpty() && input2.isNotEmpty() && operator != null) {
            val num1 = input1.toDoubleOrNull() ?: 0.0
            val num2 = input2.toDoubleOrNull() ?: 0.0
            val result = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN // Handle division by zero
                else -> 0.0
            }

            displayOutput.text = decimalFormat.format(result)
            clearAfterCalculation()
        }
    }

    private fun clearAfterCalculation() {
        input1 = ""
        input2 = ""
        operator = null
        displayInput.text = ""
    }

    private fun clear() {
        input1 = ""
        input2 = ""
        operator = null
        displayInput.text = ""
        displayOutput.text = ""
    }
}
