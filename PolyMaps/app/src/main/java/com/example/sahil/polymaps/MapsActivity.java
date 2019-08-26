package com.example.sahil.polymaps;
//Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolygonClickListener, GoogleMap.OnPolylineClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        String jsonParseOutput = loadJSONFromAsset();
        Log.d("demo", jsonParseOutput);
        Gson gson = new Gson();
        Location location = gson.fromJson(jsonParseOutput, Location.class);

        ArrayList<LocationParameters> locationParameters = new ArrayList<>();
        locationParameters = location.getPoints();

        if(locationParameters != null)for(LocationParameters point : locationParameters){
            Log.d("demo", point.toString());
        }
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        String Latitude = locationParameters.get(0).getLatitude();
        String Longitude = locationParameters.get(0).getLongitude();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Start Location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            ArrayList<LatLng> pointList = new ArrayList<>();
        for (int i = 0; i< locationParameters.size(); i++){
            pointList.add(new LatLng(Double.parseDouble(locationParameters.get(i).getLatitude()), Double.parseDouble(locationParameters.get(i).getLongitude())));
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i<pointList.size(); i++){
            builder.include(pointList.get(i));
        }
        int padding =20;
        final LatLngBounds bounds = builder.build();
        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(pointList));
        LatLng endSydney = new LatLng(Double.parseDouble(locationParameters.get(locationParameters.size()-1).getLatitude()), Double.parseDouble(locationParameters.get(locationParameters.size()-1).getLongitude()));
        mMap.addMarker(new MarkerOptions().position(endSydney).title("End Location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(endSydney));
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.setLatLngBoundsForCameraTarget(bounds);
                mMap.animateCamera(cu);
            }
        });
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("trip.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}
