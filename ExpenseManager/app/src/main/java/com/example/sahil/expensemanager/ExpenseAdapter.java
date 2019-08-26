package com.example.sahil.expensemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    ArrayList<Expense> expenses = new ArrayList<>();
    Context context;
    AdapterInterface adapterInterface;
     public ExpenseAdapter(ArrayList<Expense> expenses, Context context, AdapterInterface adapterInterface){
         this.expenses = expenses;
         this.context = context;
         this.adapterInterface = adapterInterface;
     }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Expense expense = expenses.get(i);
        viewHolder.name.setText(expense.name);
        viewHolder.amount.setText(expense.cost.toString());
        viewHolder.date.setText(expense.date);
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterInterface.dataToEdit(expenses.get(i));
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterInterface.dataToDisplay(expenses.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, amount;
        ImageButton imageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
        }
    }
    public interface AdapterInterface{
         public void dataToEdit(Expense expense);
         public void dataToDisplay(Expense expense);
    }
}
