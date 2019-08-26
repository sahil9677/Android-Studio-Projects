package com.example.sahil.homework_03;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayDetails extends AppCompatActivity {
    Track track;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaydetails);
        if(getIntent() != null && getIntent().getExtras() != null){
            track = (Track) getIntent().getExtras().getSerializable("Song");
        }
        TextView text1,text2,text3,text4,text5;
        ImageView imageView = findViewById(R.id.imageView);
        text1 = findViewById(R.id.genre);
        text2 = findViewById(R.id.display_artist);
        text3 = findViewById(R.id.album);
        text4 = findViewById(R.id.trackprice);
        text5 = findViewById(R.id.albumprice);
        Picasso.get().load(track.trackViewURL).into(imageView);
        text1.setText(track.primaryGenreName);
        text2.setText(track.artistName);
        text3.setText(track.collectionName);
        text4.setText(String.valueOf(track.trackPrice));
        text5.setText(String.valueOf(track.collectionPrice));

        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
