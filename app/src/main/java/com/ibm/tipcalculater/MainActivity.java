package com.ibm.tipcalculater;

import android.icu.text.NumberFormat;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final NumberFormat currencyFormat=NumberFormat.getNumberInstance();
    private static final NumberFormat percentFormat=NumberFormat.getPercentInstance();
    private Double billAmount =0.0;
    private Double percent =0.15;
    private TextView amountTextView;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountTextView=(TextView) findViewById(R.id.amounttextView);
        percentTextView=(TextView) findViewById(R.id.percenttextView);
        tipTextView=(TextView) findViewById(R.id.tipTextView);
        totalTextView=(TextView) findViewById(R.id.totaltextView);


        //set AmountText's textWatcher
        EditText amountEditText=(EditText) findViewById(R.id.AmounteditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);
        SeekBar percentSeekBar=(SeekBar)findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

    }
//calculate the tip and total amounts
    private void calculate (){

        percentTextView.setText(percentFormat.format(percent));
        double tip=billAmount*percent ;
        double total=billAmount+tip;
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }

  //listener object for the SeekBar's progress changed events

    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener=
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                    percent=progress/100.0;//set Percent based on progress
                    calculate();//update the tip and total textviews
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };
    private final TextWatcher amountEditTextWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

try{
    billAmount=Double.parseDouble(charSequence.toString())/1000.0;
    amountTextView.setText(currencyFormat.format(billAmount));

}catch (NumberFormatException e){    //if charSequence is empty or non-numeric
    amountTextView.setText("");
    billAmount=0.0;
}
calculate();//update the total and tip TextVeiws

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
