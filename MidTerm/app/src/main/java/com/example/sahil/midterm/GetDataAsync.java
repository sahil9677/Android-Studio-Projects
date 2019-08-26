package com.example.sahil.midterm;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetDataAsync extends AsyncTask<String, Void, ArrayList<API_Object>> {
    ArrayList<API_Object> result = new ArrayList<>();
    Data data;
    public  GetDataAsync(Data data){
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
                JSONObject root1 = root.getJSONObject("message");
                JSONObject subroot = root1.getJSONObject("body");
                JSONArray rootJSONArray = subroot.getJSONArray("track_list");
                for (int i = 0; i < rootJSONArray.length(); i++) {
                    JSONObject trackJson1 = rootJSONArray.getJSONObject(i);
                    JSONObject trackJson = trackJson1.getJSONObject("track");
                    API_Object track = new API_Object();
                    track.track_name = trackJson.getString("track_name");
                    track.album_name = trackJson.getString("album_name");
                    track.artist_name = trackJson.getString("artist_name");
                    track.track_share_url = trackJson.getString("track_share_url");
                    DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
                    String date = trackJson.getString("updated_time");
                    Date date1 = formatter.parse(date);
                    SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
                    track.updated_time = mdyFormat.format(date1);
                    result.add(track);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPostExecute(ArrayList<API_Object> objects) {
        if (objects != null){
            Log.d("demo", objects.toString() );
        } else{
            Log.d("demo", "no result");
        }
        data.handleData(objects);
    }
    public static interface Data{
        public void handleData(ArrayList<API_Object> tracks);
    }
}
