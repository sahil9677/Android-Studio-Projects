package com.example.sahil.recyclerviewexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {

    ArrayList<Email> mData;

    public EmailAdapter(ArrayList<Email> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Email email= mData.get(i);
        viewHolder.textViewSubject.setText(email.subject);
        viewHolder.textViewSummary.setText(email.summary);
        viewHolder.textViewEmail.setText(email.sender);
        viewHolder.email = email;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewSubject;
        TextView textViewSummary;
        TextView textViewEmail;

        Email email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSubject = (TextView)itemView.findViewById(R.id.textViewSubject);
            textViewSummary = (TextView)itemView.findViewById(R.id.textViewSummary);
            textViewEmail = (TextView)itemView.findViewById(R.id.textViewEmail);
            itemView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Demo", "Clicked the button"+email.sender);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo", "clicked"+email.sender);
                }
            });
        }
    }

}
