package com.example.sahil.homework04;

import android.content.Context;
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
import java.text.ParseException;
import java.util.ArrayList;

public class GetDataAsync extends AsyncTask<String, Void, ArrayList<API_Object>> {
    Data data;
    String url;
    ArrayList<API_Object> results = new ArrayList<>();
    Context context;
    public GetDataAsync(Context context, String Url, Data data){
        this.results =results;
        this.context = context;
        this.data = data;
    }


    @Override
    protected ArrayList<API_Object> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                JSONObject root = new JSONObject(json);
                JSONArray jsonArray = root.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject recipeJSON = jsonArray.getJSONObject(i);
                    API_Object recipe = new API_Object();
                    recipe.href = recipeJSON.getString("href");
                    recipe.ingredients = recipeJSON.getString("ingredients");
                    recipe.thumbnail = recipeJSON.getString("thumbnail");
                    recipe.title = recipeJSON.getString("title");
                    results.add(recipe);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }
    protected void onPostExecute(ArrayList<API_Object> objects) {
        if (objects != null){
            Log.d("demo", objects.toString() );
        } else{
            Log.d("demo", "no result");
        }
        data.handleData(objects);
    }
    public static interface Data{
        public void handleData(ArrayList<API_Object> recipes);
    }
}
