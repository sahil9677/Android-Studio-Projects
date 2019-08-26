package com.example.sahil.parampassingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if(getIntent() != null && getIntent().getExtras() != null){
            //Toast.makeText(this, getIntent().getExtras().getString(MainActivity.NAME_KEY) + "," + getIntent().getExtras().getDouble(MainActivity.AGE_KEY), Toast.LENGTH_SHORT).show();
            //User user = (User) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
            Person person = getIntent().getExtras().getParcelable(MainActivity.PERSON_KEY);
            Toast.makeText(this, person.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
