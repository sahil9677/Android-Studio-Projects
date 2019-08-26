package com.example.sahil.newsapi;
//Sahil Deshmukh    (801100363)

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetDataAsync.Data, DisplayAsync.Data{
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    ArrayList categories = new ArrayList<String>();
    String selectedcategory;
    String strUrl;
    public static Context context;
    ArrayList<Article> result = new ArrayList<>();

    TextView title;
    TextView description;
    TextView publishedAt;
    ImageView imageView1;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        final TextView category = findViewById(R.id.textView);
        title = findViewById(R.id.textView2);
        description = findViewById(R.id.textView4);
        publishedAt = findViewById(R.id.textView3);
        progressBar = findViewById(R.id.progressBar2);
        final ImageView imageView1 = findViewById(R.id.imageView);
        categories.add("business");
        categories.add("entertainment");
        categories.add("general");
        categories.add("health");
        categories.add("science");
        categories.add("sports");
        categories.add("technology");
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Choose Category");
                    builder.setItems((CharSequence[]) categories.toArray(new CharSequence[categories.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedcategory = (String) categories.get(which);
                            category.setText(selectedcategory);
                            strUrl    = "https://itunes.apple.com/search" + "?" + "term=" + "0" + "&" + "apiKey=" + "3c2b369f7b1d4091b40ef2cbda811e0c" + "&" + "country=" + "canada" + "&" + "category=" + selectedcategory;
                            new GetDataAsync(MainActivity.this).execute(strUrl);
                            title.setVisibility(View.INVISIBLE);
                            description.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            imageView1.setVisibility(View.INVISIBLE);
                            publishedAt.setVisibility(View.INVISIBLE);
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();

                }else{
                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
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
    public void handleData(ArrayList<Article> articles) {
        result = articles;
        final int[] y ={0};
        final TextView count = findViewById(R.id.textView5);
        TextView title = findViewById(R.id.textView2);
        TextView description = findViewById(R.id.textView4);
        TextView publishedAt = findViewById(R.id.textView3);
        ImageView imageView1 = findViewById(R.id.imageView);
        title.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        imageView1.setVisibility(View.VISIBLE);
        publishedAt.setVisibility(View.VISIBLE);
        title.setText(String.valueOf(result.get(0).title));
        description.setText(String.valueOf(result.get(0).description));
        publishedAt.setText(String.valueOf(result.get(0).publishedAt));
        imageView1.setTag(String.valueOf(result.get(1).urlToImage));
        new DisplayAsync(MainActivity.this).execute(imageView1);
        count.setText(String.valueOf(y[0]+1)+" out of 20");
        ImageButton button1 = findViewById(R.id.imageButton);
        ImageButton button2 = findViewById(R.id.imageButton3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    y[0]--;
                    if (y[0] < 0){
                        y[0] = result.size()-1;
                        TextView title = findViewById(R.id.textView2);
                        TextView description = findViewById(R.id.textView4);
                        TextView publishedAt = findViewById(R.id.textView3);
                        ImageView imageView1 = findViewById(R.id.imageView);
                        title.setText(result.get(y[0]).title);
                        publishedAt.setText(result.get(y[0]).publishedAt);
                        description.setText(result.get(y[0]).description);
                        imageView1.setTag(result.get(y[0]).urlToImage);
                        new DisplayAsync(MainActivity.this).execute(imageView1);
                        count.setText(String.valueOf(y[0]+1)+" out of 20");
                    }else {
                        TextView title = findViewById(R.id.textView2);
                        TextView description = findViewById(R.id.textView4);
                        TextView publishedAt = findViewById(R.id.textView3);
                        ImageView imageView1 = findViewById(R.id.imageView);
                        title.setText(result.get(y[0]).title);
                        publishedAt.setText(result.get(y[0]).publishedAt);
                        description.setText(result.get(y[0]).description);
                        imageView1.setTag(result.get(y[0]).urlToImage);
                        new DisplayAsync(MainActivity.this).execute(imageView1);;
                        count.setText(String.valueOf(y[0]+1)+" out of 20");
                    }
                }else{
                    Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    y[0]++;
                    if (y[0] > result.size()-1){
                        y[0] = 0;
                        TextView title = findViewById(R.id.textView2);
                        TextView description = findViewById(R.id.textView4);
                        TextView publishedAt = findViewById(R.id.textView3);
                        ImageView imageView1 = findViewById(R.id.imageView);
                        title.setText(result.get(y[0]).title);
                        publishedAt.setText(result.get(y[0]).publishedAt);
                        description.setText(result.get(y[0]).description);
                        imageView1.setTag(result.get(y[0]).urlToImage);
                        new DisplayAsync(MainActivity.this).execute(imageView1);
                        count.setText(String.valueOf(y[0]+1)+" out of 20");
                    }else {
                        TextView title = findViewById(R.id.textView2);
                        TextView description = findViewById(R.id.textView4);
                        TextView publishedAt = findViewById(R.id.textView3);
                        ImageView imageView1 = findViewById(R.id.imageView);
                        title.setText(result.get(y[0]).title);
                        publishedAt.setText(result.get(y[0]).publishedAt);
                        description.setText(result.get(y[0]).description);
                        imageView1.setTag(result.get(y[0]).urlToImage);
                        new DisplayAsync(MainActivity.this).execute(imageView1);
                        count.setText(String.valueOf(y[0]+1)+" out of 20");
                    }
                }else{
                    Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void handleDisplay(Bitmap result) {
        final ImageView imageView1;
        imageView1= (ImageView) findViewById(R.id.imageView);
        imageView1.setImageBitmap(result);
    }
}
