package hr.fer.android.jmbag0036482927;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * The main activity that offers user to choose which operation between
 * two entered number he wants to execute.
 *
 * @author Vjeran
 */
public class CalculusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        final EditText firstNumber = (EditText) findViewById(R.id.etFirstNumber);
        final EditText secondNumber = (EditText) findViewById(R.id.etSecondNumber);
        final RadioGroup operation = (RadioGroup) findViewById(R.id.rgOperation);
        final Button calculate = (Button) findViewById(R.id.btnCalculate);

        operation.check(R.id.rbSum);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstStr = firstNumber.getText().toString();
                if(firstStr.isEmpty()) firstStr = "0";
                double first = Double.parseDouble(firstStr);

                String secondStr = secondNumber.getText().toString();
                if(secondStr.isEmpty()) secondStr = "0";
                double second = Double.parseDouble(secondStr);

                int checkedId = operation.getCheckedRadioButtonId();
                try {
                    double res = 0;
                    switch (checkedId) {
                        case R.id.rbSum:
                            res = first + second;
                            break;
                        case R.id.rbSub:
                            res = first - second;
                            break;
                        case R.id.rbMul:
                            res = first * second;
                            break;
                        case R.id.rbDiv:
                            if(second == 0) {
                                throw new IllegalArgumentException("Division by zero");
                            }
                            res = first / second;
                            break;
                        default:
                            break;
                    }

                    String pattern = res % 1 == 0 ? "#" : "#.###";
                    String resString = String.format("%s", new DecimalFormat(pattern).format(res));

                    Intent i = new Intent(getApplicationContext(), DisplayActivity.class);
                    i.putExtra("opId", checkedId);
                    i.putExtra("result", resString);
                    startActivity(i);

                } catch (Exception e){
                    Intent i = new Intent(getApplicationContext(), DisplayActivity.class);
                    i.putExtra("first", firstStr);
                    i.putExtra("second", secondStr);
                    i.putExtra("opId", checkedId);
                    i.putExtra("error", e.getMessage());
                    startActivity(i);
                    return;
                }
            }
        });
    }
}
