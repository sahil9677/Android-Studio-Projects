package com.example.sahil.simplelistviews;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] colors = {"Red", "Blue", "Green", "White", "Black", "Yellow"};
    //Color[] colorObjects = {new Color("Red"),new Color("Blue"),new Color("Green"),new Color("White"),new Color("Black"),new Color("Yellow")};
    //ArrayList<Color> data = new ArrayList<>();
    ArrayList<Email> emails = new ArrayList<>();
    //ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        data.add(new Color("Red"));
        data.add(new Color("Blue"));
        data.add(new Color("Green"));
        data.add(new Color("White"));
        data.add(new Color("Black"));
        data.add(new Color("Yellow"));
        */
        emails.add(new Email("Hi ", "Summary 1 ", "bsmith1@test.com"));
        emails.add(new Email("Hello ", "Summary 2 ", "bsmith2@test.com"));
        emails.add(new Email("Whats up ", "Summary 3 ", "bsmith3@test.com"));
        emails.add(new Email("Hiiiii ", "Summary 4 ", "bsmith4@test.com"));
        emails.add(new Email("Ae ", "Summary 5 ", "bsmith5@test.com"));
        emails.add(new Email("TP ", "Summary 6 ", "bsmith6@test.com"));
        emails.add(new Email("Hi ", "Summary 7 ", "bsmith7@test.com"));
        ListView listView = (ListView) findViewById(R.id.listView);
        EmailAdapter adapter = new EmailAdapter(this, R.layout.email_item, emails);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, colors);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo", "Clicked Item " + position);
            }
        });
        /*
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Color color = adapter.getItem(position);
                adapter.remove(color);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.editText);
                adapter.add(new Color(text.getText().toString()));
                adapter.notifyDataSetChanged();

            }
        });
        */
    }
    static class Color{
        String name;
        int hex;

        public Color(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
