package com.example.sahil.homework04;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class API_Adapter extends RecyclerView.Adapter<API_Adapter.ViewHolder> {
    ArrayList<String> inputIngredients = new ArrayList();
    GetIngredients getIngredients;
    Context context;
    public API_Adapter(Context context, ArrayList<String> inputIngredients, GetIngredients getIngredients){
        this.context = context;
        this.inputIngredients = inputIngredients;
        this.getIngredients = getIngredients;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = viewHolder.getAdapterPosition();
                inputIngredients.add(viewHolder.input1.getText().toString());
                ArrayList<String> strings = new ArrayList<>();
                strings = inputIngredients;
                getIngredients.handleIngredients(inputIngredients);
                Log.d("demo", strings.toString());
                viewHolder.input1.setVisibility(View.INVISIBLE);
                viewHolder.button1.hide();
                viewHolder.text1.setVisibility(View.VISIBLE);
                viewHolder.text1.setText(inputIngredients.get(index));
                viewHolder.button2.show();
            }
        });
        viewHolder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = viewHolder.getAdapterPosition();
                inputIngredients.remove(a);
                ArrayList<String> strings = new ArrayList<>();
                strings = inputIngredients;
                Log.d("remove", strings.toString());
                if(inputIngredients != null){
                    getIngredients.handleIngredients(strings);
                }
                notifyItemRemoved(a);
                viewHolder.input1.setVisibility(View.VISIBLE);
                viewHolder.button1.show();
                viewHolder.text1.setVisibility(View.INVISIBLE);
                viewHolder.button2.hide();
            }
        });
    }

    @Override
    public int getItemCount() {
        return inputIngredients.size()+1;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText input1;
        TextView text1;
        FloatingActionButton button1, button2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            input1 = (EditText)itemView.findViewById(R.id.editText);
            text1 = (TextView)itemView.findViewById(R.id.textView);
            button1 = (FloatingActionButton)itemView.findViewById(R.id.floatingActionButton2);
            button2 = (FloatingActionButton)itemView.findViewById(R.id.floatingActionButton);
        }
    }

    public static interface GetIngredients{
        public void handleIngredients(ArrayList<String> ingredient);
    }

}