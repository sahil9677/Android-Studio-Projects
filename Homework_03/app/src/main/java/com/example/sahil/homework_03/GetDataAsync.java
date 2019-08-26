package com.example.sahil.homework_03;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class GetDataAsync extends AsyncTask<String, Void, ArrayList<Track>> {
    ArrayList<Track> result = new ArrayList<>();
    Bitmap bitmap;
    Data data;
    public  GetDataAsync(Data data){
        this.data = data;
    }


    @Override
    protected ArrayList<Track> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                String json = IOUtils.toString(connection.getInputStream(),"UTF8");
                JSONObject root = new JSONObject(json);
                JSONArray rootJSONArray = root.getJSONArray("results");
                for (int i = 0; i < rootJSONArray.length(); i++) {
                    JSONObject trackJson = rootJSONArray.getJSONObject(i);

                    Track track = new Track();
                    track.trackName = trackJson.getString("trackName");
                    track.collectionName = trackJson.getString("collectionName");
                    track.primaryGenreName = trackJson.getString("primaryGenreName");
                    track.artistName = trackJson.getString("artistName");

                    track.trackViewURL = trackJson.getString("artworkUrl100");

                    DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
                    String date = trackJson.getString("releaseDate");
                    track.releaseDate = formatter.parse(date);
                    //2007-11-27T08:00:00Z
//                        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd G 'at' hh:mm:ss a zzz");
//                        Date currentTime_1 = new Date();
//                        String dateeee = formatter.format(currentTime_1);

                    track.collectionPrice = trackJson.getDouble("collectionPrice");
                    track.trackPrice = trackJson.getDouble("trackPrice");

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
    protected void onPostExecute(ArrayList<Track> tracks) {
        if (tracks != null){
            Log.d("demo", tracks.toString() );
        } else{
            Log.d("demo", "no result");
        }
        data.handleData(tracks);
    }


    public static interface Data{
        public void handleData(ArrayList<Track> tracks);
    }
}
