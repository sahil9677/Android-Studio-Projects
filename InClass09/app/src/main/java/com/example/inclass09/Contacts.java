package com.example.inclass09;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
// Sahil Deshmukh (801100363) Aashwin Patki (801079127)
public class Contacts extends AppCompatActivity {

    ArrayList<Item> items = new ArrayList<>();
    private static final String TAG = "Contacts";
//    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    String item;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        if (getIntent() != null) {
            item = (String) getIntent().getExtras().getSerializable(MainActivity.KEY);
            final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(item);

//            ListView listView = findViewById(R.id.listView);
            Button create = findViewById(R.id.create_new);
            ImageView imageView = findViewById(R.id.logout);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    items.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Item item = ds.getValue(Item.class);
                        Log.d(TAG, item.toString());
                        items.add(item);
                    }

                    if (items.size() > 0) {
                        listView = findViewById(R.id.listView);
                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                myRef.child(items.get(position).getId()).removeValue();
                                items.remove(position);
                                listView = findViewById(R.id.listView);
                                ItemAdapter adapter = new ItemAdapter(Contacts.this, R.layout.item_layout, items);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("demo", "Clicked Item " + position);
                                    }
                                });
//                                mListener.itemLongClicked(items);
                                items.clear();
                                return true;
                            }
                        });

                        ItemAdapter adapter = new ItemAdapter(Contacts.this, R.layout.item_layout, items);
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Error", databaseError.toString());
                }
            });

            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // create new contact
                    Intent intent = new Intent(getBaseContext(), NewContactActivity.class);
                    intent.putExtra(MainActivity.KEY, item);
                    startActivity(intent);
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Logout
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(Contacts.this, MainActivity.class);
                    startActivity(intent);
                }
            });

        }

    }
}
