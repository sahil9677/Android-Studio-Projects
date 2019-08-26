package com.example.sahil.newsapi;


import android.graphics.Bitmap;

import android.os.AsyncTask;
import android.util.Log;


import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;




class GetDataAsync extends AsyncTask<String, Void, ArrayList<Article>>{
    ArrayList<Article> result = new ArrayList<>();
    Bitmap bitmap;
    Data data;
    public  GetDataAsync(Data data){
        this.data = data;
    }


    @Override
    protected ArrayList<Article> doInBackground(String... params) {
        //StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                /*BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }*/
            String json = IOUtils.toString(connection.getInputStream());
            JSONObject root = new JSONObject(json);
            JSONArray articles = root.getJSONArray("articles");
            for (int i = 0; i<articles.length();i++){
                JSONObject articleJson = articles.getJSONObject(i);
                Article article = new Article();
                article.description = articleJson.getString("description");
                article.urlToImage = articleJson.getString("urlToImage");
                article.publishedAt = articleJson.getString("publishedAt");
                article.title = articleJson.getString("title");
                result.add(article);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(ArrayList<Article> article) {
        if (article != null){
            Log.d("demo", article.toString() );
        } else{
            Log.d("demo", "no result");
        }
        data.handleData(article);
    }


    public static interface Data{
        public void handleData(ArrayList<Article> articles);
    }
}