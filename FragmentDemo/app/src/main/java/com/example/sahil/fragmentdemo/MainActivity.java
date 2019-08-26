package com.example.sahil.fragmentdemo;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements AFragment.OnFragmentTextChange{
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("demo", "MainActivity: onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("demo", "MainActivity: onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("demo", "MainActivity: onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("demo", "MainActivity: onCreate before inflating Layout");
        setContentView(R.layout.activity_main);
        Log.d("demo", "MainActivity: onCreate after inflating layout");
        getSupportFragmentManager().beginTransaction().add(R.id.container, new AFragment(), "tag_afragment").commit();
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Fragment f = (AFragment)getSupportFragmentManager().findFragmentByTag("tag_afragment");
                if(checkedId == R.id.radioButton){
                    ((AFragment) f).changeColor(Color.RED);
                }else if(checkedId == R.id.radioButton2){
                    ((AFragment) f).changeColor(Color.GREEN);
                }else if(checkedId == R.id.radioButton3){
                    ((AFragment) f).changeColor(Color.BLUE);
                }
            }
        });
    }

    @Override
    public void onTextChanged(String text) {
        TextView tv = (TextView) findViewById(R.id.text_view);
        tv.setText(text);
    }
}
