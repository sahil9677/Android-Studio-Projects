package com.example.sahil.homework04;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Display extends Fragment {
    ArrayList<API_Object> recipe1 = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Recipe Display");
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.displayRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(recipe1);
        mRecyclerView.setAdapter(mAdapter);
        Button button = (Button) getActivity().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.onFinishButtonPressed();
            }
        });
    }

    public void onRecipeReceived(ArrayList<API_Object> recipes){
        recipe1 = recipes;
    }
    public Display() {
        // Required empty public constructor
    }
    HandleDisplay display;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            display = (HandleDisplay) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "should implement OnFragmentTextChange");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display, container, false);

    }
    public interface HandleDisplay{
        public void onFinishButtonPressed();
    }

}
