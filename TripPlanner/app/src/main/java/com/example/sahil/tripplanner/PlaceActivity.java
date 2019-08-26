package com.example.sahil.tripplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class PlaceActivity extends AppCompatActivity  {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    EditText tripName;
    AlertDialog selectKeywords;
    String place_id;
    DatePickerDialog datePickerDialog;
    Button date;
    Trips trips;
    Location cityLocation;
    Button keywords;
    Button search;
    Button done;
    String city;
    ArrayList<Places> places= new ArrayList<>();
    String urlKeywords;
    ArrayList<String> finalKeywords = new ArrayList<>();
    private static final String LOG_TAG = "PlacesAPI";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyDRi6-NdYwi7aKg57WMk0E90O2alt1mwHU";
    ArrayAdapter<String> adapter;
    AutoCompleteTextView textView;
    String[] items = {"airport", "amusement_park", "aquarium", "art_gallery","bar","cafe","casino","church","museum", "night_club", "park"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        date =findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(PlaceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        trips.date = String.valueOf(month) + "," + dayOfMonth + "," + year;
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trips.trip = tripName.getText().toString();
                Log.d("trips",trips.toString());
                if(trips.date.equals("") || trips.trip.equals("") || trips.city.equals("")|| trips.places == null){
                    Log.d("trips",trips.toString());
                    Toast.makeText(PlaceActivity.this, "Please Enter all values", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(PlaceActivity.this, MainActivity.class);
                    intent.putExtra("trip",trips);
                    startActivity(intent);
                }

            }
        });
        tripName = findViewById(R.id.trip);
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);
        textView = findViewById(R.id.city);
        adapter.setNotifyOnChange(true);
        textView.setAdapter(adapter);
        search = findViewById(R.id.search);
        keywords = findViewById(R.id.keyword);
        trips = new Trips();

        keywords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(PlaceActivity.this);
                builder.setTitle("Selección")
                        .setMultiChoiceItems(items, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                        Log.i("Dialogos", "Opción elegida: " + items[item]);
                                        finalKeywords.add(items[item]);
                                    }
                                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog = builder.create();
                dialog.show();

            }
        });
        for (int i =0; i<finalKeywords.size(); i++){
            if(i == 0){
                urlKeywords = finalKeywords.get(i);
            }else {
                urlKeywords = urlKeywords +"," + finalKeywords.get(i);
            }
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetDataAsyncPlaces().execute(urlKeywords);

            }
        });
        textView.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count%3 == 1) {
                    adapter.clear();
                    new GetDataAsync().execute("true");
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                Log.d("placeid", String.valueOf(place_id));
                new GetDataAsyncCity().execute(place_id);
                trips.city = textView.getText().toString();
            }
        });


    }
    class GetDataAsync extends AsyncTask<String, Void , String >{

        @Override
        protected String doInBackground(String... strings) {
            try {

                ArrayList resultList = null;

                HttpURLConnection conn = null;
                StringBuilder jsonResults = new StringBuilder();
                try {
                    StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
                    sb.append("?key=" + API_KEY);

                    sb.append("&input=" + URLEncoder.encode(textView.getText().toString(), "utf8"));

                    URL url = new URL(sb.toString());
                    conn = (HttpURLConnection) url.openConnection();
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    int read;
                    char[] buff = new char[1024];
                    while ((read = in.read(buff)) != -1) {
                        jsonResults.append(buff, 0, read);
                    }
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, "Error processing API URL", e);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error connecting to Places API", e);
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }

                JSONObject jsonObj = new JSONObject(jsonResults.toString());
                JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");


                resultList = new ArrayList(predsJsonArray.length());
                for (int i = 0; i < predsJsonArray.length(); i++) {
                    System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                    System.out.println("============================================================");
                    resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                    adapter.add(predsJsonArray.getJSONObject(i).getString("description"));
                    place_id = predsJsonArray.getJSONObject(i).getString("place_id");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
    class GetDataAsyncCity extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {

                ArrayList resultList = null;
                Log.d("placeid2", String.valueOf(strings));
                HttpURLConnection conn = null;
                StringBuilder jsonResults = new StringBuilder();
                try {
                    String StrUrl = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyDRi6-NdYwi7aKg57WMk0E90O2alt1mwHU&placeid=" + place_id;
                    URL url = new URL(StrUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    int read;
                    char[] buff = new char[1024];
                    while ((read = in.read(buff)) != -1) {
                        jsonResults.append(buff, 0, read);
                    }
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, "Error processing API URL", e);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error connecting to Places API", e);
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }

                JSONObject jsonObj = new JSONObject(jsonResults.toString());
                JSONObject predsJsonArray = jsonObj.getJSONObject("result");
                resultList = new ArrayList(predsJsonArray.length());
                JSONObject geometry = predsJsonArray.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                cityLocation = new Location();
                cityLocation.latitude = location.getString("lat");
                cityLocation.longitude = location.getString("lng");
                Log.d("city", cityLocation.toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            search.setEnabled(true);
        }
    }
    class GetDataAsyncPlaces extends AsyncTask<String, Void, String>{
        String StrUrl;
        @Override
        protected String doInBackground(String... strings) {
            try {

                ArrayList resultList = null;

                HttpURLConnection conn = null;
                StringBuilder jsonResults = new StringBuilder();
                try {
                    Places places = new Places();
                    places.location =cityLocation;
                    places.name = textView.getText().toString();
                    trips.places.add(places);
                    StrUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + cityLocation.latitude+ ","+ cityLocation.longitude + "&radius=15&key=AIzaSyDRi6-NdYwi7aKg57WMk0E90O2alt1mwHU";

                    URL url = new URL(StrUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    int read;
                    char[] buff = new char[1024];
                    while ((read = in.read(buff)) != -1) {
                        jsonResults.append(buff, 0, read);
                    }
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, "Error processing API URL", e);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error connecting to Places API", e);
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }

                JSONObject jsonObj = new JSONObject(jsonResults.toString());
                JSONArray predsJsonArray = jsonObj.getJSONArray("results");



                resultList = new ArrayList(predsJsonArray.length());
                for (int i = 0; i < predsJsonArray.length(); i++) {

                    Places place= new Places();
                    JSONObject jobj = predsJsonArray.getJSONObject(i);
                    JSONObject geometry = jobj.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    Location location1 = new Location();
                    location1.latitude = location.getString("lat");
                    location1.longitude = location.getString("lng");
                    place.location = location1;
                    place.name = jobj.getString("name");
                    places.add(place);

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PlaceActivity.this);
                        PlaceAdapter.PlaceInterface placeInterface = new PlaceAdapter.PlaceInterface() {
                            @Override
                            public void onPlacesSelectedListener(Places places) {
                                trips.places.add(places);
                            }
                        };
                        RecyclerView.Adapter adapter = new PlaceAdapter(places,PlaceActivity.this, placeInterface);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        Log.d("places", places.toString());

                    }
                });

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }

}
