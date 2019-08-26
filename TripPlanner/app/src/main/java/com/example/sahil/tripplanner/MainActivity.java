package com.example.sahil.tripplanner;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button add;
    public static final String VALUE_KEY = "value";
    int REQ_CODE = 100;
    ArrayList<Trips> trips1 = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("trips/");
    Trips trips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Trips myTrips = ds.getValue(Trips.class);
                    trips1.add(myTrips);
                }
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                TripAdapter.TripInterface tripInterface = new TripAdapter.TripInterface() {
                    @Override
                    public void OnTripSelected(Trips trips) {
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        intent.putExtra("trips", trips);
                        startActivity(intent);
                    }
                };
                RecyclerView.Adapter adapter = new TripAdapter(trips1, MainActivity.this, tripInterface);
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        trips1.clear();
        add = (Button) findViewById(R.id.add_trip);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
                startActivity(intent);

            }
        });
        trips = (Trips) getIntent().getSerializableExtra("trip");
        if (trips!= null){
            Log.d("trips12",trips.toString());
            DatabaseReference reference = mDatabase.child("trips");
            String key = reference.push().getKey();
            trips.tripID = key;
            reference.child(key).setValue(trips);
        }

    }

}
