 package com.example.sahil.alertdialogdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

 public class MainActivity extends AppCompatActivity {
     CharSequence[] items = {"Red", "Blue", "Green", "Yellow"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a Color")
        .setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("demo", "Selected" + items[which]);
            }
        });
                //.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            //@Override
            //public void onClick(DialogInterface dialog, int which) {
            //    Log.d("demo", "Clicked Ok");
            //}
        //})
        //.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          //  @Override
           // public void onClick(DialogInterface dialog, int which) {
             //   Log.d("demo", "Clicked Cancel");
            //}
        //});
        final AlertDialog alert = builder.create();
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });
    }
}
