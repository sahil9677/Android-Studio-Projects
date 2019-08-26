package com.example.sahil.studentprofilebuilder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity{
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Select Avatar");
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = 1;
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VALUE_KEY, value);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = 2;
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VALUE_KEY, value);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = 3;
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VALUE_KEY, value);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.imageView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = 4;
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VALUE_KEY, value);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.imageView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = 5;
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VALUE_KEY, value);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.imageView7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = 6;
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VALUE_KEY, value);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
