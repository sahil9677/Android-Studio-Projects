package com.example.inclass09;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
    }

    private static class ViewHolder {
        TextView name, phone, email;
        ImageView imageView;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item contact = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.item_name);
            viewHolder.phone = convertView.findViewById(R.id.item_number);
            viewHolder.email = convertView.findViewById(R.id.item_email);
            viewHolder.imageView = convertView.findViewById(R.id.imageView2);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(contact.getName());
        viewHolder.phone.setText(contact.getPhoneNumber());
        viewHolder.email.setText(String.valueOf(contact.getEmail()));
//        viewHolder.imageView.setImageDrawable();

        return  convertView;
    }



}
