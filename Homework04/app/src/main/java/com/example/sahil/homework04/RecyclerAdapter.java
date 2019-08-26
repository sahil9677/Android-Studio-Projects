package com.example.sahil.homework04;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<API_Object> objects;
    public RecyclerAdapter(ArrayList<API_Object> objects){
        this.objects = objects;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        API_Object object = objects.get(i);
        Log.d("display", object.toString());
        viewHolder.textView1.setText(object.title);
        viewHolder.textView2.setText(object.ingredients);
        if(!object.thumbnail.equals("")){
            Picasso.get().load(object.thumbnail).into(viewHolder.imageView1);
        }
        viewHolder.textView3.setMovementMethod(LinkMovementMethod.getInstance());
        viewHolder.textView3.setText(object.href);

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3;
        ImageView imageView1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.textView5);
            textView2 = (TextView) itemView.findViewById(R.id.textView7);
            textView3 = (TextView) itemView.findViewById(R.id.textView9);
            imageView1 = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
