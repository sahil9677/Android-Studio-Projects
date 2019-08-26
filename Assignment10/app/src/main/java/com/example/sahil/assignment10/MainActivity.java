package com.example.sahil.assignment10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.sahil.assignment10.LogIN.MyFAVORITES;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences(MyFAVORITES, Context.MODE_PRIVATE);
        Boolean check = pref.getBoolean("auth", false);
        if(!check){
            Intent intent = new Intent(MainActivity.this, LogIN.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
