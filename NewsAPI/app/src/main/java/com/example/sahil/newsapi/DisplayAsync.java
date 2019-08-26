package com.example.sahil.newsapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



public class DisplayAsync extends AsyncTask<ImageView, Void, Bitmap> {
    Data data;
    public  DisplayAsync(GetDataAsync.Data data){
        this.data = (Data) data;
    }
    ImageView imageView;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Bitmap doInBackground(ImageView... imageViews) {
        this.imageView = imageViews[0];
        return get_Image_Bitmap((String)imageView.getTag());
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        data.handleDisplay(result);

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
    public static interface Data{
        public void handleDisplay(Bitmap result);
    }
}

