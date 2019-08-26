package com.example.sahil.simplelistviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EmailAdapter extends ArrayAdapter<Email> {


    public EmailAdapter(@NonNull Context context, int resource, @NonNull List<Email> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Email email = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.email_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewSubjext = (TextView) convertView.findViewById(R.id.textViewSubject);
            viewHolder.textViewSummary = (TextView) convertView.findViewById(R.id.textViewSummary);
            viewHolder.textViewEmail = (TextView) convertView.findViewById(R.id.textViewEmail);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textViewSubjext.setText(email.subject);
        viewHolder.textViewSummary.setText(email.summary);
        viewHolder.textViewEmail.setText(email.sender);
        return convertView;
    }

    private static class ViewHolder{
        TextView textViewSubjext;
        TextView textViewSummary;
        TextView textViewEmail;

    }
}
