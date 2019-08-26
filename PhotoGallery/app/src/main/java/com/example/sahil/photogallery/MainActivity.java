package com.example.sahil.photogallery;

//Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    ArrayList keywords = new ArrayList<String>();
    String[] results;
    String[] imageURLs;
    ArrayList images = new ArrayList<String>();
    int x = 0;
    int z = 0;
    AlertDialog alertDialog;
    AsyncTask async;
    ImageButton button1;
    ImageButton button2;
    ImageView imageView;
    TextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (ImageButton) findViewById(R.id.imageButton);
        button2 = (ImageButton) findViewById(R.id.imageButton2);
        button1.setEnabled(false);
        button2.setEnabled(false);
        Button button = (Button) findViewById(R.id.button);
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose a Keyword");
        if(isConnected()){
            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    keywords.clear();
                    images.clear();
                    new GetDataAsync().execute("http://dev.theappsdr.com/apis/photos/keywords.php");
                }else{
                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class GetDataAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }
                String x = stringBuilder.toString();
                results = x.split(";");
                for(int i=0; i<results.length; i++){
                    keywords.add(results[i]);
                }
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("demo", String.valueOf(keywords));
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null){
                Log.d("demo", result);
                Log.d("demo", String.valueOf(keywords));
            } else{
                Log.d("demo", "no result");
            }
            builder.setItems((CharSequence[]) keywords.toArray(new CharSequence[keywords.size()]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(isConnected()){
                        x = which;
                        Log.d("demo", String.valueOf(x));
                        String selectedKeyword = (String) keywords.get(x);
                        String targetURL = "https://dev.theappsdr.com/apis/photos/index.php?keyword=";
                        String finalURL = targetURL + selectedKeyword;
                        Log.d("demo", finalURL);
                        new ImageAsync().execute(finalURL);
                        textView = (TextView) findViewById(R.id.textView);
                        textView.setText((CharSequence) keywords.get(x));
                    }else{
                        Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private class ImageAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = " ";
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                    stringBuilder.append(" ");
                }
                String x = stringBuilder.toString();
                imageURLs = x.split(" ");
                for(int i=0; i<imageURLs.length; i++){
                    images.add(imageURLs[i]);
                }
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            z = 10;
            //for(int i =0; i<images.size(); i++) {
            Log.d("demo", String.valueOf(images.get(0)));
            //}
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null){
                Log.d("demo", result);
            } else{
                Log.d("demo", "no result");
            }
            final int[] y = {0};
            ListIterator<String> itr = images.listIterator();
            while (itr.hasNext()) {
                itr.set(itr.next().replaceAll("\\s", "1"));
            }
            Log.d("demo", "your string is " + images);
            final ImageView imageView = (ImageView) findViewById(R.id.imageView);
            if(images.isEmpty() || images.get(0).equals(" ") || images.get(0).toString().trim().equals("")){
                Toast.makeText(getBaseContext(),"No images found",Toast.LENGTH_LONG).show();
                imageView.setImageResource(0);
            }else {
                imageView.setTag(images.get(y[0]));
                new DoWorkAsync().execute(imageView);
            }
            if (images.size() >= 2){
                button1.setEnabled(true);
                button2.setEnabled(true);
            }
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnected()){
                        y[0]--;
                        if (y[0] < 0){
                            y[0] = images.size()-1;
                            imageView.setTag(images.get(y[0]));
                            new DoWorkAsync().execute(imageView);
                        }else {
                            imageView.setTag(images.get(y[0]));
                            new DoWorkAsync().execute(imageView);
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnected()){
                        y[0]++;
                        if (y[0] > images.size()-1){
                            y[0] = 0;
                            imageView.setTag(images.get(y[0]));
                            new DoWorkAsync().execute(imageView);
                        }else {
                            imageView.setTag(images.get(y[0]));
                            new DoWorkAsync().execute(imageView);
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    class DoWorkAsync extends AsyncTask<ImageView, Void, Bitmap> {
        ImageView imageView = null;

        @Override
        protected void onPreExecute() {
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setVisibility(View.INVISIBLE);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            return get_Image_Bitmap((String)imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
            progressBar.setVisibility(View.INVISIBLE);
            ImageView imageview = (ImageView) findViewById(R.id.imageView);
            imageview.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(result);

        }
        private Bitmap get_Image_Bitmap(String url) {
            //---------------------------------------------------
            Bitmap bm = null;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("Hub", "Error getting the image from server : " + e.getMessage().toString());
            }
            return bm;
        }
    }
    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null || !networkInfo.isConnected()|| (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)){
            return false;
        }
        return  true;

    }


}
