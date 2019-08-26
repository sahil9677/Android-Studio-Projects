package com.example.sahil.inclass04;
//Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                String URL = "https:cdn.pixabay.com/photo/2014/12/16/22/25/youth-570881_960_720.jpg";
                imageView.setTag(URL);
                new DoWorkAsync().execute(imageView);
            }
        });
        Button button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler = new Handler(new Handler.Callback() {
                    ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    @Override
                    public boolean handleMessage(Message msg) {
                        switch (msg.what){
                            case DoWork.STATUS_START:
                                imageView.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.VISIBLE);
                                break;
                                case DoWork.STATUS_PROGRESS:
                                    imageView.setImageBitmap((Bitmap) msg.obj);
                                    break;
                                    case DoWork.STATUS_STOP:
                                        imageView.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        break;
                        }
                        return false;
                    }

                });
                new Thread(new DoWork()).start();
            }
        });


    }
    class DoWorkAsync extends AsyncTask<ImageView, Void, Bitmap> {
        ImageView imageView = null;

        @Override
        protected void onPreExecute() {
            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
            imageView.setVisibility(View.INVISIBLE);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            return get_Image_Bitmap((String)imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            ImageView imageview = (ImageView) findViewById(R.id.imageView2);
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
    class DoWork implements Runnable{
        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;
        static final String PROGRESS_KEY = "PROGRESS";
        @Override
        public void run(){
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);
            String URL = "https:cdn.pixabay.com/photo/2017/12/31/06/16/boats-3051610_960_720.jpg ";
            Message message = new Message();
            message.what = STATUS_PROGRESS;
            Bitmap image = getImageBitmap(URL);
            message.obj = image;
            handler.sendMessage(message);
            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;
            handler.sendMessage(stopMessage);
        }
        private Bitmap getImageBitmap(String url) {
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

}
