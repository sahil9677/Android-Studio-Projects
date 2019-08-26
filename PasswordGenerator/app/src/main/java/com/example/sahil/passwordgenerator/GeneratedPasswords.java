package com.example.sahil.passwordgenerator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class GeneratedPasswords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_passwords);
        Intent intent = getIntent();
        ArrayList passwords = new ArrayList<String>();
        ArrayList passwords1 = new ArrayList<String>();
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.ll1);
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout parentLayout1 = (LinearLayout) findViewById(R.id.ll2);
        View view;
        passwords.clear();
        passwords1.clear();
        parentLayout.removeAllViewsInLayout();
        parentLayout1.removeAllViewsInLayout();
        passwords = intent.getStringArrayListExtra("key");
        passwords1 = intent.getStringArrayListExtra("key1");
        for(int i = 0; i < passwords.size(); i++){
            view = inflater.inflate(R.layout.text_layout, parentLayout, false);
            TextView textView = (TextView) view.findViewById(R.id.text);
            Log.d("demo", (String) passwords.get(i));
            textView.setText((String) passwords.get(i));
            parentLayout.addView(textView);
        }
        for(int i = 0; i < passwords1.size(); i++){
            view = inflater.inflate(R.layout.text_layout, parentLayout1, false);
            TextView textView = (TextView) view.findViewById(R.id.text);
            Log.d("demo", (String) passwords1.get(i));
            textView.setText((String) passwords1.get(i));
            parentLayout1.addView(textView);
        }
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.seekBar1.setProgress(1);
                MainActivity.seekBar2.setProgress(1);
                MainActivity.seekBar3.setProgress(1);
                MainActivity.seekBar4.setProgress(1);
                MainActivity.textView.setText("1");
                MainActivity.textView1.setText("7");
                MainActivity.textView2.setText("1");
                MainActivity.textView3.setText("7");
                MainActivity.passwords.clear();
                MainActivity.passwords1.clear();
                MainActivity.progressDialog.setProgress(0);
                finish();
            }
        });
    }
}
