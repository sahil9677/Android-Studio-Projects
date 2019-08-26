package com.example.sahil.inclass2a;
//Assignment number 2
//Group 11
//Sahil Deshmukh(801100363), Ashwin Patki(801079127)
//InClass2a
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText edit;
    public EditText edit1;
    public EditText edit2;
    public TextView edit3;
    public TextView edit4;
    public double w,f,i,tinch, bmi;
    public String weight, feet, inches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("BMI Calculator");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = (EditText)findViewById(R.id.editText1);
        edit1 = (EditText)findViewById(R.id.editText2);
        edit2 = (EditText)findViewById(R.id.editText3);
        edit3 = (TextView)findViewById(R.id.textView7);
        edit4 = (TextView)findViewById(R.id.textView8);
        final Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(TextUtils.isEmpty(edit.getText())){
                    Toast.makeText(getApplicationContext(),"please enter a value",Toast.LENGTH_SHORT).show();
                    edit.setError("Invalid input");
                }else if(TextUtils.isEmpty(edit1.getText())){
                    Toast.makeText(getApplicationContext(), "please enter a value", Toast.LENGTH_SHORT).show();
                    edit1.setError("Invalid input");
                }else if(TextUtils.isEmpty(edit2.getText())){
                    Toast.makeText(getApplicationContext(),"please enter a value",Toast.LENGTH_SHORT).show();
                    edit2.setError("Invalid input");
                }else{
                    w = Double.parseDouble(edit.getText().toString());
                    f = Double.parseDouble(edit1.getText().toString());
                    i = Double.parseDouble(edit2.getText().toString());
                    if(i > 12){
                        edit2.setError("Please enter value below 12");
                    }else {
                        tinch = (f * 12) + i;
                        bmi = (w / (tinch * tinch)) * 703;
                        String finalresult = new Double(bmi).toString();
                        Toast.makeText(getApplicationContext(),"BMI Calculated",Toast.LENGTH_SHORT).show();
                        String s = getResources().getString(R.string.your_bmi);
                        edit3.setText(s +" " + finalresult);
                        String str = getResources().getString(R.string.status);
                        if(bmi <= 18.5){
                            edit4.setText(str + " Underweight");
                        }else if(bmi > 18.5 && bmi <= 24.9){
                            edit4.setText(str + " Normal Weight");
                        }else if(bmi > 24.9 && bmi <= 29.9){
                            edit4.setText(str + " Overweight");
                        }else if(bmi > 29.9){
                            edit4.setText(str + " Obese");
                        }
                    }
                }



            }

        });
    }
}
