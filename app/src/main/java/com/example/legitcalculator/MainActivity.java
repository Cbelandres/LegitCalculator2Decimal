package com.example.legitcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentNumber = "";
    private String previousNumber = "";
    private String operator = "";
    private double result = 0.0;
    private boolean lastClickedEquals = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
    }

    public void onDigitClick(View view) {
        if (lastClickedEquals) {
            currentNumber = "";
            lastClickedEquals = false;
        }

        Button button = (Button) view;
        currentNumber += button.getText().toString();
        display.setText(currentNumber);
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        String newOperator = button.getText().toString();

        if (!currentNumber.isEmpty()) {
            if (!previousNumber.isEmpty() && !operator.isEmpty()) {
                // Compute the result with the current operator and number
                computeResult();
            }

            if (newOperator.equals("%")) {
                if (!previousNumber.isEmpty()) {
                    double firstNumber = Double.parseDouble(previousNumber);
                    double percentage = Double.parseDouble(currentNumber) / 100;
                    result = firstNumber * percentage;
                    display.setText(String.valueOf(result));
                    currentNumber = String.valueOf(result);
                    previousNumber = "";
                    operator = "";
                    lastClickedEquals = true;
                }
            } else {
                previousNumber = currentNumber;
                currentNumber = "";
                operator = newOperator;
            }
        }
    }

    public void onEqualsClick(View view) {
        if (!previousNumber.isEmpty() && !currentNumber.isEmpty()) {
            computeResult();
            display.setText(String.valueOf(result));
            currentNumber = String.valueOf(result);
            previousNumber = "";
            operator = "";
            lastClickedEquals = true;
        }
    }

    private void computeResult() {
        double firstNumber = Double.parseDouble(previousNumber);
        double secondNumber = Double.parseDouble(currentNumber);

        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                result = (secondNumber != 0.0) ? firstNumber / secondNumber : Double.NaN;
                break;
            case "%":
                // Calculate percentage with respect to the previous number
                result = (secondNumber != 0.0) ? (firstNumber * (secondNumber / 100)) : Double.NaN;
                break;
            default:
                result = 0.0;
                break;
        }
    }

    public void onClearClick(View view) {
        currentNumber = "";
        previousNumber = "";
        operator = "";
        result = 0.0;
        display.setText("0");
        lastClickedEquals = false;
    }
}
