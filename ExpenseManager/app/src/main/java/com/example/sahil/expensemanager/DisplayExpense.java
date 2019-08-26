package com.example.sahil.expensemanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class DisplayExpense extends AppCompatActivity {
    Expense expense = new Expense();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expense = (Expense) getIntent().getSerializableExtra("Expense");
        setContentView(R.layout.activity_display_expense);
        TextView name = (TextView) findViewById(R.id.textView11);
        TextView amount = (TextView) findViewById(R.id.textView13);
        TextView date = (TextView) findViewById(R.id.textView14);
        name.setText(expense.name);
        amount.setText(expense.cost.toString());
        date.setText(expense.date);
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        try {
            if(expense.image.equals("")){
                Bitmap imageBitmap = decodeFromFirebaseBase64(expense.image);
                imageView.setImageBitmap(imageBitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VALUE_KEY, "value");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
