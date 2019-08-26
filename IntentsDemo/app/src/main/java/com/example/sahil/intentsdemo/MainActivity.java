package com.example.sahil.intentsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button2second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                Intent intent= new Intent("com.example.sahil.intentsdemo.intent.action.VIEW");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
            }
        });
    }
}
