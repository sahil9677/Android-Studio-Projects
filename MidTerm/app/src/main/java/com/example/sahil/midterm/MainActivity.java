package com.example.sahil.midterm;
// Sahil Deshmukh 801100363
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetDataAsync.Data{
    EditText editText;
    SeekBar seekBar;
    RadioGroup rg;
    Button button1;
    TextView text1;
    String keyword;
    int limit = 5;
    String rating, rating1;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        seekBar = findViewById(R.id.seekBar);
        rg = findViewById(R.id.radioGroup);
        button1 = findViewById(R.id.button);
        text1 = findViewById(R.id.textView2);
        text1.setText(String.valueOf(limit));
        progressBar = findViewById(R.id.progressBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                limit = progress + 5;
                text1.setText(String.valueOf(limit));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int rid=rg.getCheckedRadioButtonId();
        if ( rid!= -1) {
            RadioButton rb = (RadioButton) findViewById(rid);
            rating = rb.getText().toString();
        } else {
            rating = "Not Selected";
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    keyword = editText.getText().toString();
                    if (rating.equals("Track rating")){
                        rating1 = "s_track_rating";
                    }else {
                        rating1 = "s_artist_rating";
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    String strURL = "http://api.musixmatch.com/ws/1.1/track.search?apikey=76738dd1e6b8c31d0a7c15bc38b48d1d&q=" + keyword + "&page_size=" + limit + "&" + rating1;
                    new GetDataAsync(MainActivity.this).execute(strURL);
                }else {
                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                }

            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int rid = checkedId;
                if ( rid!= -1) {
                    RadioButton rb = (RadioButton) findViewById(rid);
                    rating = rb.getText().toString();
                    if (rating.equals("Track rating")){
                        rating1 = "s_track_rating";
                    }else {
                        rating1 = "s_artist_rating";
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    String strURL = "http://api.musixmatch.com/ws/1.1/track.search?apikey=76738dd1e6b8c31d0a7c15bc38b48d1d&q=" + keyword + "&page_size=" + limit + "&" + rating1;
                    Log.d("demo", strURL);
                    new GetDataAsync(MainActivity.this).execute(strURL);
                } else {
                    rating = "Not Selected";
                }
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
    public void handleData(final ArrayList<API_Object> objects) {
        ListView listView = (ListView) findViewById(R.id.list_view);
        APIAdapter adapter = new APIAdapter(this, R.layout.listview_item, objects);
        listView.setAdapter(adapter);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = objects.get(position).track_share_url;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
