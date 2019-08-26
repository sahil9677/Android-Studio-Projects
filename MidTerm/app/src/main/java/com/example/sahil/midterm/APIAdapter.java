package com.example.sahil.midterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class APIAdapter extends ArrayAdapter<API_Object> {
    public APIAdapter(@NonNull Context context, int resource, @NonNull List<API_Object> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        API_Object email = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.track = (TextView) convertView.findViewById(R.id.track);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.artist);
            viewHolder.album = (TextView) convertView.findViewById(R.id.album);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.track.setText(email.track_name);
        viewHolder.artist.setText(email.artist_name);
        viewHolder.album.setText(String.valueOf(email.album_name));
        viewHolder.date.setText(String.valueOf(email.updated_time));
        return  convertView;
    }
    private static class ViewHolder{
        TextView track, artist, album, date;

    }
}
