package com.example.sahil.tripplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>{
    ArrayList<Places> places = new ArrayList<>();
    Context context;
    PlaceInterface placeInterface;
    public PlaceAdapter(ArrayList<Places> places, Context context, PlaceInterface placeInterface) {
        this.places = places;
        this.context = context;
        this.placeInterface = placeInterface;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Places places1 = places.get(i);
        viewHolder.placeName.setText(places1.name);
        viewHolder.addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeInterface.onPlacesSelectedListener(places1);
                Toast.makeText(context, "The place was added to your list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;
        Button addPlace;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = (TextView)itemView.findViewById(R.id.place_name);
            addPlace = (Button)itemView.findViewById(R.id.add_place);

        }
    }

    public interface PlaceInterface{
        public void onPlacesSelectedListener(Places places);
    }

}
