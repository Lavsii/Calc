package ru.geekbrains.calc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean flagPoint;
    String operand1, operand2, mathSymbol;
    int flagAction;
    double result;

    Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_dot,
            btn_plus, btn_minus, btn_equals, btn_multiply, btn_delitel, btn_clear;

    TextView screenCalc, resultScreen;

    int[] bt_ids;
    Button[] bt_array;

    int len;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenCalc = (TextView) findViewById(R.id.calcScreen);
        resultScreen = (TextView) findViewById(R.id.screenResult);

        bt_ids = new int[]{R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8,
                R.id.btn_9, R.id.btn_0, R.id.btn_plus, R.id.btn_minus,
                R.id.btn_multiply, R.id.btn_delitel, R.id.btn_equals, R.id.btn_clear,
                R.id.btn_dot};
        bt_array = new Button[]{btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_dot,
                btn_plus, btn_minus, btn_equals, btn_multiply, btn_delitel, btn_clear};

        len = bt_array.length;
        for (int i = 0; i < len; i++) {
            bt_array[i] = (Button) findViewById(bt_ids[i]);
            bt_array[i].setOnClickListener(this);
        }
        clearVariables();
        showNumber(operand1);
        screenResult(mathSymbol);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                ClickNumber("1");
                break;
            case R.id.btn_2:
                ClickNumber("2");
                break;
            case R.id.btn_3:
                ClickNumber("3");
                break;
            case R.id.btn_4:
                ClickNumber("4");
                break;
            case R.id.btn_5:
                ClickNumber("5");
                break;
            case R.id.btn_6:
                ClickNumber("6");
                break;
            case R.id.btn_7:
                ClickNumber("7");
                break;
            case R.id.btn_8:
                ClickNumber("8");
                break;
            case R.id.btn_9:
                ClickNumber("9");
                break;
            case R.id.btn_0:
                if (flagAction == 0) {
                    if (operand1.length() != 0) ClickNumber("0");
                } else {
                    if (operand2.length() != 0) ClickNumber("0");
                }
                break;
            case R.id.btn_dot:
                if (!flagPoint) {
                    if (flagAction == 0) {
                        if (operand1.length() != 0) {
                            ClickNumber(".");
                        } else {
                            ClickNumber("0.");
                        }
                    } else {
                        if (operand2.length() != 0) {
                            ClickNumber(".");
                        } else {
                            ClickNumber("0.");
                        }
                        flagPoint = true;
                    }
                }

            case R.id.btn_plus:
                if (flagAction == 0) {
                    flagAction = 1;
                    flagPoint = false;
                }
                break;
            case R.id.btn_minus:
                if (flagAction == 0) {
                    flagAction = 2;
                    flagPoint = false;
                }
                break;
            case R.id.btn_multiply:
                if (flagAction == 0) {
                    flagAction = 3;
                    flagPoint = false;
                }
                break;
            case R.id.btn_delitel:
                if (flagAction == 0) {
                    flagAction = 4;
                    flagPoint = false;
                }

            case R.id.btn_equals:
                if (operand1.length() == 0) operand1 = "0";
                if (operand2.length() == 0) operand2 = "0";
                switch (flagAction) {
                    case 1:
                        result = Double.parseDouble(operand1) + Double.parseDouble(operand2);
                        screenResult(procNumber(result));
                        clearVariables();
                        break;
                    case 2:
                        result = Double.parseDouble(operand1) - Double.parseDouble(operand2);
                        screenResult(procNumber(result));
                        clearVariables();
                        break;
                    case 3:
                        result = Double.parseDouble(operand1) * Double.parseDouble(operand2);
                        screenResult(procNumber(result));
                        clearVariables();
                        break;
                    case 4:
                        if (Double.parseDouble(operand2) == 0) {
                            result = Double.parseDouble(operand1) / Double.parseDouble(operand2);
                        }
                        screenResult(procNumber(result));
                        clearVariables();
                        break;
                    default:
                        Toast.makeText(this, R.string.no_operation, Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_clear:
                clearVariables();
                showNumber(operand1);
                screenResult(mathSymbol);

                break;
        }
    }

    private void ClickNumber(String num) {

        if (flagAction == 0) {
            if (checkOver(operand1)) {
                Toast.makeText(this, R.string.limit, Toast.LENGTH_LONG).show();
            } else {
                operand1 = operand1 + num;
                showNumber(operand1);
            }
        } else {
            if (checkOver(operand2)) {
                Toast.makeText(this, R.string.limit, Toast.LENGTH_LONG).show();
            } else {
                operand2 = operand2 + num;
                showNumber(operand2);

                }
            }
        }
        private void showNumber(String num) {

        if (num.length() == 0) num = "";
        screenCalc.setText(num);
        if (num.equals("error"))
            Toast.makeText(this, R.string.degree_overflow, Toast.LENGTH_LONG).show();
    }

    private void screenResult(String num) {
        resultScreen.setText(num);
    }

    private void clearVariables() {
        operand1 = "";
        operand2 = "";
        result = 0;
        flagAction = 0;
        flagPoint = false;
    }

    private boolean checkOver(String var) {
        int corr = 0;
        if (var.contains(".")) corr = 1;
        return var.length() - corr == 10;
    }

    private String procNumber(double res) {
        String num;
        long part_int = (long) (res - res % 1);
        long part_frac = Math.round(res % 1 * 1000000000);
        if (part_frac == 0) {
            num = Long.toString(part_int);
            if (num.length() > 10) num = "error";
        } else {
            if (Long.toString(part_int).length() > 9) {
                num = "error";
            } else {
                num = part_int + "." + Math.round(part_frac /
                        Math.pow(10, Long.toString(part_int).length() - 1));
            }
            while (num.startsWith("0", num.length() - 1) & num.length() == 0) {
                num = num.substring(0, num.length() - 1);
            }

        }
        return num;
    }
}
