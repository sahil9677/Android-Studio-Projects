package com.example.sahil.tripplanner;
//Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Trips trips;
    TextView textView;
    TextView textView1;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        trips = (Trips) getIntent().getSerializableExtra("trips");
        textView = findViewById(R.id.trip_name);
        textView1 = findViewById(R.id.date_display);
        textView2 = findViewById(R.id.city_display);
        textView.setText(trips.trip);
        textView1.setText(trips.date);
        textView2.setText(trips.city);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<Places> places = new ArrayList<>();
        places = trips.places;
        ArrayList<LatLng> pointList = new ArrayList<>();
        for (int i = 0; i<places.size(); i++){
            Places places1 = places.get(i);
            Location location = places1.location;
            LatLng sydney = new LatLng(Double.parseDouble(location.latitude),Double.parseDouble(location.longitude));
            pointList.add(sydney);
            mMap.addMarker(new MarkerOptions().position(sydney).title(places1.name));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.setLatLngBoundsForCameraTarget(bounds);
                mMap.animateCamera(cu);
            }
        });



    }
}
