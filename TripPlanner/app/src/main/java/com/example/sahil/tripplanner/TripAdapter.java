package com.example.sahil.tripplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder>{
    ArrayList<Trips> trips = new ArrayList<>();
    Context context;
    TripInterface tripInterface;

    public TripAdapter(ArrayList<Trips> trips, Context context, TripInterface tripInterface) {
        this.trips = trips;
        this.context = context;
        this.tripInterface = tripInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.track_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Trips trips1 = new Trips();
        trips1 = trips.get(i);
        viewHolder.textView.setText(trips1.trip+" "+trips1.date);
        final Trips finalTrips = trips1;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripInterface.OnTripSelected(finalTrips);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.trip_name);

        }
    }
    public interface TripInterface{
        public void OnTripSelected(Trips trips);
    }
}
