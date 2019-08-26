package com.example.sahil.baclevelcalculator;
// Group 11
// Sahil Deshmukh 801100363 Aashwin Patki 801079127
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    int current;
    String prog;
    Double weight1;
    double bac;
    double alc, r, o, p;
    double x,x1;
    double fbac = 0;
    int progress;
    Button button1;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);getSupportActionBar().setIcon(R.drawable.ic_launcher_foreground);
        getSupportActionBar().setTitle("BAC Calculator");
        final Switch simpleSwitch = (Switch) findViewById(R.id.switch1); // initiate Switch
        simpleSwitch.setShowText(true);
        final TextView text = (TextView) findViewById(R.id.textView4);
        final TextView text1 = (TextView) findViewById(R.id.textView6);
        final TextView text2 = (TextView) findViewById(R.id.textView9);
        text.setText("5%");
        text1.setText("0.00");
        text2.setText("You're Safe");
        text2.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current = progress*5;
                prog = String.valueOf(current);
                text.setText(current + "%");
                p = (double)(current);
                p = p/100;
                Log.d("demo", String.valueOf(p));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final EditText edit = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edit.getText())){
                    Toast.makeText(getApplicationContext(),"Enter the weight in lb",Toast.LENGTH_LONG).show();
                    edit.setError("Enter the weight in lb");
                }else {
                    x1 = x;
                    Log.d("demo", String.valueOf(x1));
                    String s = edit.getText().toString();
                    weight1 = Double.parseDouble(s);
                    if(simpleSwitch.isChecked()){
                        r = 0.55;
                    }else{
                        r = 0.68;
                    }
                    x = weight1 * r;
                    Log.d("demo", String.valueOf(x));
                    if (x != x1){
                        fbac = (fbac * x1)/x;
                        Log.d("demo", String.valueOf(fbac));
                    }
                    progress = (int)(fbac*100);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    DecimalFormat form = new DecimalFormat("0.00");
                    text1.setText(form.format(fbac));
                    if(progress <= 25){
                        progressBar.setProgress(progress);
                    }else{
                        progressBar.setProgress(25);
                        button.setEnabled(false);
                        button1.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"No More Drinks For You",Toast.LENGTH_LONG).show();
                    }
                    if(fbac <= 0.08){
                        text2.setText("You're Safe");
                        text2.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    }else if(fbac < 0.20 && fbac > 0.08){
                        text2.setText("Be Careful");
                        text2.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    }else if(fbac >= 0.20){
                        text2.setText("Over the limit");
                        text2.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });
        button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edit.getText())){
                    Toast.makeText(getApplicationContext(),"Enter the weight in lb",Toast.LENGTH_LONG).show();
                    edit.setError("Enter the weight in lb");
                }else {
                    RadioGroup radiogroup =  (RadioGroup) findViewById(R.id.radio);
                    int rb = radiogroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) findViewById(rb);
                    String ounce = radioButton.getText().toString();
                    if(String.valueOf(ounce).equals("1 oz"))
                    {
                        o = 1;
                        Log.d("demo", String.valueOf(o));
                    }else if(String.valueOf(ounce).equals("5 oz"))
                    {
                        o = 5;
                        Log.d("demo", String.valueOf(o));
                    }else if(String.valueOf(ounce).equals("12 oz"))
                    {
                        o = 12;
                        Log.d("demo", String.valueOf(o));
                    }
                    alc = o*p;
                    Log.d("demo", String.valueOf(alc));
                    bac = (alc*6.24/x);
                    Log.d("demo", String.valueOf(bac));
                    fbac = fbac + bac;
                    Log.d("demo", String.valueOf(fbac));
                    progress = (int)(fbac*100);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    DecimalFormat form = new DecimalFormat("0.00");
                    text1.setText(form.format(fbac));
                    if(progress <= 25){
                        progressBar.setProgress(progress);
                    }else{
                        progressBar.setProgress(25);
                        button.setEnabled(false);
                        button1.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"No More Drinks For You",Toast.LENGTH_LONG).show();
                    }
                    if(fbac <= 0.08){
                        text2.setText("You're Safe");
                        text2.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    }else if(fbac < 0.20 && fbac > 0.08){
                        text2.setText("Be Careful");
                        text2.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    }else if(fbac >= 0.20){
                        text2.setText("Over the limit");
                        text2.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });
        Button button2 = (Button) findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight1 = 0.00;
                r = 0.00;
                text.setText("5%");
                text1.setText("0.00");
                text2.setText("You're Safe");
                text2.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                edit.setText(null);
                edit.setHint(R.string.enter_w);
                seekBar.setProgress(5);
                simpleSwitch.setChecked(false);
                button.setEnabled(true);
                button1.setEnabled(true);
                fbac = 0.00;
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setProgress(0);
                RadioGroup radiogroup =  (RadioGroup) findViewById(R.id.radio);
                radiogroup.clearCheck();
            }
        });


    }
}
