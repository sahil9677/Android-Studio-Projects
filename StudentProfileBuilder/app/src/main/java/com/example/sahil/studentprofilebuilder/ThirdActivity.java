package com.example.sahil.studentprofilebuilder;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {
    TextView text1;
    TextView text4;
    TextView text3;
    ImageView image1;
    Student student;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setTitle("Display My Profile");
        text1 = (TextView) findViewById(R.id.textView3);
        text4 = (TextView) findViewById(R.id.textView5);
        text3 = (TextView) findViewById(R.id.textView7);
        if(getIntent() != null && getIntent().getExtras() != null){
            student = (Student) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
        }
        String first = student.first;
        String last = student.last;
        String dept;
        dept = student.dept;
        long id = student.id;
        text1.setText(first + " " + last);
        String std;
        std = Long.toString(id);
        text4.setText(std);
        text3.setText(dept);
        Bitmap bitmap = (Bitmap)this.getIntent().getParcelableExtra("Bitmap");
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        image1 = (ImageView) findViewById(R.id.imageView8);
        image1.setImageDrawable(drawable);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
