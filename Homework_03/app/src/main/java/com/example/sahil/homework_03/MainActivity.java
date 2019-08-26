package com.example.sahil.homework_03;
//Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements GetDataAsync.Data{
    public static EditText edit1;
    public static SeekBar seekBar;
    public static Button button1,button2;
    public static Switch switch1;

    int limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit1 =(EditText) findViewById(R.id.editText);
        edit1.setHint("Enter the Keyword");
        ListView listView = (ListView) findViewById(R.id.listView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView textView = findViewById(R.id.limit);
        textView.setText(String.valueOf(limit));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                limit = progress + 10;
                textView.setText(String.valueOf(limit));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()){
                    String keyword =  edit1.getText().toString();
                    keyword.replaceAll(" ", "+");
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    String strUrl = "https://itunes.apple.com/search?term=" + keyword + "&limit=" + limit;
                    ProgressBar progressBar = findViewById(R.id.progressBar);
                    TextView loading = findViewById(R.id.loading);
                    progressBar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.VISIBLE);
                    new GetDataAsync(MainActivity.this).execute(strUrl);
                }else {
                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(null);
                edit1.setHint("Enter the Keyword");
                limit = 10;
                edit1.setText("");
                textView.setText(String.valueOf(limit));
                switch1.setChecked(true);
                seekBar.setProgress(1);
            }
        });

    }
    final public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null || !networkInfo.isConnected()|| (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)){
            return false;
        }
        return  true;

    }
    @Override
    public void handleData(final ArrayList<Track> tracks) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView loading = findViewById(R.id.loading);
        progressBar.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
        switch1 = findViewById(R.id.switch1);
        if(switch1.isChecked()==true){
            Collections.sort(tracks, Track.trackDateComparator);
        }else {
            Collections.sort(tracks, Track.trackPriceComparator);
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        TrackAdapter adapter = new TrackAdapter(this, R.layout.tracks_list, tracks);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayDetails.class);
                Track track = tracks.get(position);
                intent.putExtra("Song", track);
                startActivity(intent);
            }
        });
    }


}
