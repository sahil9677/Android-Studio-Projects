package com.example.sahil.homework_03;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TrackAdapter extends ArrayAdapter<Track> {
    public TrackAdapter(@NonNull Context context, int resource, @NonNull List<Track> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Track email = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tracks_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.track = (TextView) convertView.findViewById(R.id.track);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.artist);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.track.setText(email.trackName);
        viewHolder.artist.setText(email.artistName);
        viewHolder.price.setText(String.valueOf(email.trackPrice));
        viewHolder.date.setText(String.valueOf(email.releaseDate));
        return  convertView;
    }
    private static class ViewHolder{
        TextView track, artist, price, date;

    }
}
