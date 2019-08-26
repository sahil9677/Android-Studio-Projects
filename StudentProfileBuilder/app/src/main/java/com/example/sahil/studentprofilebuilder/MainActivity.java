package com.example.sahil.studentprofilebuilder;
// Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQ_CODE = 100;
    RadioGroup  rg;
    public static final String VALUE_KEY = "value";
    String first, last, dept;
    public static final String USER_KEY = "user";
    long id;
    int value = 20;
    EditText edit1;
    EditText edit2;
    EditText edit3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My Profile");

        findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit1 = (EditText) findViewById(R.id.editText);
                edit2 = (EditText) findViewById(R.id.editText3);
                edit3 = (EditText) findViewById(R.id.editText4);
                first = edit1.getText().toString();
                last = edit2.getText().toString();
                String temp = edit3.getText().toString();
                String temp1 = edit1.getText().toString();
                String temp2 = edit2.getText().toString();
                rg = (RadioGroup) findViewById(R.id.radioGroup);
                int rid=rg.getCheckedRadioButtonId();
                if(TextUtils.isEmpty(edit1.getText())){
                    Toast.makeText(getApplicationContext(),"please enter a value",Toast.LENGTH_SHORT).show();
                    edit1.setError("Invalid input");
                }else if(TextUtils.isEmpty(edit2.getText())){
                    Toast.makeText(getApplicationContext(), "please enter a value", Toast.LENGTH_SHORT).show();
                    edit2.setError("Invalid input");
                }else if(TextUtils.isEmpty(edit3.getText())) {
                    Toast.makeText(getApplicationContext(),"please enter a value",Toast.LENGTH_SHORT).show();
                    edit3.setError("Invalid input");
                }else if( value == 20){
                    Toast.makeText(getApplicationContext(),"please select an Avatar",Toast.LENGTH_SHORT).show();
                }else if(rid == -1){
                    Toast.makeText(getApplicationContext(),"please select a Department",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (TextUtils.isEmpty(edit3.getText())) {
                        id = 1;
                    } else {
                        try {
                            int num = Integer.parseInt(temp);
                            id = num;
                        } catch (NumberFormatException e) {
                            id = 1;
                        }
                    }
                    if ( rid!= -1) {
                        RadioButton rb = (RadioButton) findViewById(rid);
                        dept = rb.getText().toString();
                    } else {
                        dept = "Not Selected";
                    }
                    Intent intent2 = new Intent(MainActivity.this, ThirdActivity.class);
                    Student student = new Student(first, last, dept, id);
                    intent2.putExtra(USER_KEY, student);
                    ImageView image = (ImageView) findViewById(R.id.imageView3);
                    Drawable i = image.getDrawable();
                    Bitmap bitmap1 = ((BitmapDrawable) i).getBitmap();
                    intent2.putExtra("Bitmap", bitmap1);
                    startActivity(intent2);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        value = data.getExtras().getInt(VALUE_KEY);
        if(value == 1){
            ImageView image = (ImageView) findViewById(R.id.imageView3);
            image.setImageResource(R.drawable.avatar_f_1);
        }else if(value == 2){
            ImageView image = (ImageView) findViewById(R.id.imageView3);
            image.setImageResource(R.drawable.avatar_f_2);
        }else if(value == 3){
            ImageView image = (ImageView) findViewById(R.id.imageView3);
            image.setImageResource(R.drawable.avatar_f_3);
        }else if(value == 4){
            ImageView image = (ImageView) findViewById(R.id.imageView3);
            image.setImageResource(R.drawable.avatar_m_1);
        }else if(value == 5){
            ImageView image = (ImageView) findViewById(R.id.imageView3);
            image.setImageResource(R.drawable.avatar_m_2);
        }else if(value == 6){
            ImageView image = (ImageView) findViewById(R.id.imageView3);
            image.setImageResource(R.drawable.avatar_m_3);
        }else if(value == 20){
            ImageView image = (ImageView) findViewById(R.id.imageView3);
            image.setImageResource(R.drawable.select_image);
        }


    }



}
