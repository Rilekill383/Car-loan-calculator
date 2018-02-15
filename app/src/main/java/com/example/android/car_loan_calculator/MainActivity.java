package com.example.android.car_loan_calculator;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText CarCost;
    private EditText DP;
    private EditText APR;
    private RadioButton BUTOON1;
    private SeekBar SEEKBAR;
    private int LoanLeaseLength = 15;
    private TextView Seekbarnumber;
    private TextView MonthlyBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarCost = findViewById(R.id.Car_Cost_input);
        DP = findViewById(R.id.DownPayment_input);
        APR = findViewById(R.id.APR_input);
        BUTOON1 = findViewById(R.id.rbutton1);
        SEEKBAR = findViewById(R.id.seekbar);
        Seekbarnumber = findViewById(R.id.SeekbarDisplay);
        MonthlyBill = findViewById(R.id.MonthlyBill_output);

        SEEKBAR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Seekbarnumber.setText("" + progress + " Months");
                LoanLeaseLength = progress;
                onClickBtn(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        CarCost.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                onClickBtn(textView);
                return false;
            }
        });

        DP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                onClickBtn(textView);
                return false;
            }
        });

        APR.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                onClickBtn(textView);
                return false;
            }
        });

        BUTOON1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onClickBtn(compoundButton);
            }
        });

        if (savedInstanceState != null)  {
            MonthlyBill.setText(savedInstanceState.getString("MONTHLY_PAYMENT"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)  {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("MONTHLY_PAYMENT", MonthlyBill.getText().toString());
    }

    public void onClickBtn(View v) {
        if (CarCost.getText().toString().equals("") || CarCost.getText().toString().isEmpty())  {
            Toast.makeText(this, "All Input fields must be filled", Toast.LENGTH_LONG).show();
            return;
        }
        double CarCostInput = Double.parseDouble(CarCost.getText().toString());
        if (DP.getText().toString().equals("") || DP.getText().toString().isEmpty())  {
            Toast.makeText(this, "All Input fields must be filled", Toast.LENGTH_LONG).show();
            return;
        }
        double DownPaymentInput = Double.parseDouble(DP.getText().toString());
        if (APR.getText().toString().equals("") || APR.getText().toString().isEmpty())  {
            Toast.makeText(this, "All Input fields must be filled", Toast.LENGTH_LONG).show();
            return;
        }
        double APRInput = Double.parseDouble(APR.getText().toString());
        double mr = ((APRInput/100)/12);
        double L = CarCostInput - DownPaymentInput;
        double LX = ((CarCostInput/3) - DownPaymentInput);
        double Z = Math.pow(1+mr,-LoanLeaseLength);
        double ZX = Math.pow(1+mr,-36);
        double P;
        if(BUTOON1.isChecked())  {
            P = ((mr*L)/(1-Z));
        } else {
            P = ((mr*LX)/(1-ZX));
        }

        MonthlyBill.setText(String.format("($)%.2f", P));
    }
}