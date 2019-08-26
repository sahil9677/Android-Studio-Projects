package com.example.sahil.homework04;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {
    ArrayList<String> list1 = new ArrayList<>();
    HandleSearch handler;
    EditText dish;
    public Search() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            handler = (HandleSearch) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "should implement OnFragmentTextChange");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Recipe Puppy");
        dish = (EditText) getActivity().findViewById(R.id.dishName);

        RecyclerView  recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter;
        ArrayList<String> inputIngredients = new ArrayList();
        final API_Adapter.GetIngredients getIngredients = new API_Adapter.GetIngredients() {
            @Override
            public void handleIngredients(ArrayList<String> ingredient) {
                list1 = ingredient;
            }
        };
        adapter = new API_Adapter(getActivity(), inputIngredients, getIngredients);
        recyclerView.setAdapter(adapter);
        Button button = (Button) getActivity().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (list1.isEmpty() || dish.getText().equals("") ){
                     Toast.makeText(getActivity(), "Please enter an ingredient", Toast.LENGTH_SHORT).show();
                 }else {
                     String finalIngredients = list1.get(0);
                     for (int i=1;i<list1.size();i++){
                         finalIngredients += "," + list1.get(i);
                     }
                     GetDataAsync.Data data = new GetDataAsync.Data() {
                         @Override
                         public void handleData(ArrayList<API_Object> recipes) {
                             ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
                             progressBar.setVisibility(View.INVISIBLE);
                             handler.onDataReceived(recipes);
                         }
                     };
                     String dishAttribute = dish.getText().toString();
                     View view = getView();
                     String URL = "http://www.recipepuppy.com/api/?i=" + finalIngredients + "&q=" + dishAttribute;
                     new GetDataAsync(getActivity(), URL, data).execute(URL);
                     ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
                     progressBar.setVisibility(View.VISIBLE);
                 }
            }
        });
    }


    public interface HandleSearch {
        public  void  onDataReceived(ArrayList<API_Object> recipe);

    }
}
