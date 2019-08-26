package com.example.sahil.assignment7;
//Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseApp.OnAddButtonPressed, ShowExpense.Interface2, AddExpense.Interface1, EditExpense.Interface2{
    private static final String DESCRIBABLE_KEY = "describable_key";
    private static ArrayList<Expense> expenses = new ArrayList<Expense>();
    private DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("users/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new ExpenseApp(), "tag_ExpenseApp").commit();
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenses.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Expense myexpense = ds.getValue(Expense.class);
                    expenses.add(myexpense);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.toString());
            }
        });
    }

    @Override
    public void onAddButtonClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddExpense(), "tag_AddExpense").commit();
    }

    @Override
    public void itemClicked(int position) {
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        Fragment fragment1 = new ShowExpense();
        ft1.replace(R.id.container, fragment1);
        ft1.commit();
        ((ShowExpense) fragment1).onExpeseSelected(expenses.get(position));
    }
    @Override
    public void itemLongClicked(ArrayList<Expense> expenses2) {
        expenses = expenses2;
    }

    @Override
    public void onExpenseAdded(Expense expense) {
        expenses.add(expense);
        Log.d("ArrayList",expenses.toString());
        ArrayList<Expense> expenses3 = new ArrayList<>();
        expenses3 = expenses;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new ExpenseApp();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void onExpenseChanged(Expense expense) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new ExpenseApp();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void closed() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new ExpenseApp();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void onclosed() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new ExpenseApp();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public void onedit(Expense expense) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new EditExpense();
        ft.replace(R.id.container, fragment);
        ft.commit();
        ((EditExpense) fragment).expenseToEdit(expense);
    }
}
