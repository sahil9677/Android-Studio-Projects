package com.example.sahil.homework04;
//Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  Search.HandleSearch, Display.HandleDisplay{
    ArrayList<String> arrayList = new ArrayList();
    ArrayList<API_Object> recipes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new Search(), "tagSearch").commit();
    }

    @Override
    public void onDataReceived(ArrayList<API_Object> recipe) {
        recipes = recipe;
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new Display();
        ft1.replace(R.id.container, fragment).disallowAddToBackStack();
        ft1.commit();
        ((Display) fragment).onRecipeReceived(recipes);
    }

    @Override
    public void onFinishButtonPressed() {
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new Search();
        ft1.replace(R.id.container, fragment);
        ft1.commit();
    }
}
