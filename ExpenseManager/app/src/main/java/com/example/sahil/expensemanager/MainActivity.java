package com.example.sahil.expensemanager;
//Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
        int REQ_CODE = 100;

    public static final String VALUE_KEY = "value";
    private DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("users/");
    private static final String DESCRIBABLE_KEY = "describable_key";
    ArrayList<Expense> expenses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddExpense.class);
                startActivityForResult(intent,REQ_CODE);
            }
        });
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenses.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Expense myexpense = ds.getValue(Expense.class);
                    expenses.add(myexpense);
                }
                try {
                    if(expenses.size() > 0){
                        Double total = 0.0;
                        for(int i = 0; i<expenses.size(); i++){
                            Expense expense = new Expense();
                            expense = expenses.get(i);
                            total = total + expense.cost;
                            TextView totalTv = (TextView) findViewById(R.id.textView3);
                            totalTv.setText(String.valueOf(total));
                        }
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setVisibility(View.VISIBLE);
                        TextView textView = (TextView) findViewById(R.id.textView6);
                        textView.setVisibility(View.INVISIBLE);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        ExpenseAdapter.AdapterInterface adapterInterface = new ExpenseAdapter.AdapterInterface() {
                            @Override
                            public void dataToEdit(Expense expense) {
                                Intent intent = new Intent(MainActivity.this, EditExpense.class);
                                intent.putExtra("Expense", expense);
                                startActivityForResult(intent, REQ_CODE);
                            }

                            @Override
                            public void dataToDisplay(Expense expense) {
                                Intent intent = new Intent(MainActivity.this, DisplayExpense.class);
                                intent.putExtra("Expense", expense);
                                startActivityForResult(intent, REQ_CODE);
                            }
                        };
                        RecyclerView.Adapter adapter = new ExpenseAdapter(expenses, getApplicationContext(), adapterInterface);
                        recyclerView.setAdapter(adapter);

                    }else{
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setVisibility(View.INVISIBLE);
                        TextView textView = (TextView) findViewById(R.id.textView6);
                        textView.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    expenses.clear();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE){
            if (resultCode == RESULT_OK){
                mDatabase2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        expenses.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            Expense myexpense = ds.getValue(Expense.class);
                            expenses.add(myexpense);
                        }
                        try {
                            if(expenses.size() > 0){
                                Double total = 0.0;
                                for(int i = 0; i<expenses.size(); i++){
                                    Expense expense = new Expense();
                                    expense = expenses.get(i);
                                    total = total + expense.cost;
                                    TextView totalTv = (TextView) findViewById(R.id.textView3);
                                    totalTv.setText(String.valueOf(total));
                                }
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setVisibility(View.VISIBLE);
                                TextView textView = (TextView) findViewById(R.id.textView6);
                                textView.setVisibility(View.INVISIBLE);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                                recyclerView.setLayoutManager(layoutManager);
                                ExpenseAdapter.AdapterInterface adapterInterface = new ExpenseAdapter.AdapterInterface() {
                                    @Override
                                    public void dataToEdit(Expense expense) {
                                        Intent intent = new Intent(MainActivity.this, EditExpense.class);
                                        intent.putExtra("Expense", expense);
                                        startActivityForResult(intent, REQ_CODE);
                                    }

                                    @Override
                                    public void dataToDisplay(Expense expense) {
                                        Intent intent = new Intent(MainActivity.this, DisplayExpense.class);
                                        intent.putExtra("Expense", expense);
                                        startActivityForResult(intent, REQ_CODE);
                                    }
                                };
                                RecyclerView.Adapter adapter = new ExpenseAdapter(expenses, getApplicationContext(), adapterInterface);
                                recyclerView.setAdapter(adapter);

                            }else{
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                                recyclerView.setVisibility(View.INVISIBLE);
                                TextView textView = (TextView) findViewById(R.id.textView6);
                                textView.setVisibility(View.VISIBLE);
                            }
                        }catch (Exception e){
                            expenses.clear();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("The read failed: " ,databaseError.toString());
                    }
                });

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.reset){
            DatabaseReference root = FirebaseDatabase.getInstance().getReference();
            root.setValue(null);
            mDatabase2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    expenses.clear();
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Expense myexpense = ds.getValue(Expense.class);
                        expenses.add(myexpense);
                    }
                    try {
                        if(expenses.size() > 0){
                            Double total = 0.0;
                            for(int i = 0; i<expenses.size(); i++){
                                Expense expense = new Expense();
                                expense = expenses.get(i);
                                total = total + expense.cost;
                                TextView totalTv = (TextView) findViewById(R.id.textView3);
                                totalTv.setText(String.valueOf(total));
                            }
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setVisibility(View.VISIBLE);
                            TextView textView = (TextView) findViewById(R.id.textView6);
                            textView.setVisibility(View.INVISIBLE);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            ExpenseAdapter.AdapterInterface adapterInterface = new ExpenseAdapter.AdapterInterface() {
                                @Override
                                public void dataToEdit(Expense expense) {
                                    Intent intent = new Intent(MainActivity.this, EditExpense.class);
                                    intent.putExtra("Expense", expense);
                                    startActivityForResult(intent, REQ_CODE);
                                }

                                @Override
                                public void dataToDisplay(Expense expense) {
                                    Intent intent = new Intent(MainActivity.this, DisplayExpense.class);
                                    intent.putExtra("Expense", expense);
                                    startActivityForResult(intent, REQ_CODE);
                                }
                            };
                            RecyclerView.Adapter adapter = new ExpenseAdapter(expenses, getApplicationContext(), adapterInterface);
                            recyclerView.setAdapter(adapter);

                        }else{
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                            recyclerView.setVisibility(View.INVISIBLE);
                            TextView textView = (TextView) findViewById(R.id.textView6);
                            textView.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e){
                        expenses.clear();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("The read failed: " ,databaseError.toString());
                }
            });
        }else {
            if(item.getItemId() == R.id.scost){
                Collections.sort(expenses, Expense.expenseCostComparator);
            }else if(item.getItemId() == R.id.sdate){
                Collections.sort(expenses, Expense.expenseDateComparator);
            }
            mDatabase2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        if(expenses.size() > 0){
                            Double total = 0.0;
                            for(int i = 0; i<expenses.size(); i++){
                                Expense expense = new Expense();
                                expense = expenses.get(i);
                                total = total + expense.cost;
                                TextView totalTv = (TextView) findViewById(R.id.textView3);
                                totalTv.setText(String.valueOf(total));
                            }
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setVisibility(View.VISIBLE);
                            TextView textView = (TextView) findViewById(R.id.textView6);
                            textView.setVisibility(View.INVISIBLE);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            ExpenseAdapter.AdapterInterface adapterInterface = new ExpenseAdapter.AdapterInterface() {
                                @Override
                                public void dataToEdit(Expense expense) {
                                    Intent intent = new Intent(MainActivity.this, EditExpense.class);
                                    intent.putExtra("Expense", expense);
                                    startActivityForResult(intent, REQ_CODE);
                                }

                                @Override
                                public void dataToDisplay(Expense expense) {
                                    Intent intent = new Intent(MainActivity.this, DisplayExpense.class);
                                    intent.putExtra("Expense", expense);
                                    startActivityForResult(intent, REQ_CODE);
                                }
                            };
                            RecyclerView.Adapter adapter = new ExpenseAdapter(expenses, getApplicationContext(), adapterInterface);
                            recyclerView.setAdapter(adapter);

                        }else{
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                            recyclerView.setVisibility(View.INVISIBLE);
                            TextView textView = (TextView) findViewById(R.id.textView6);
                            textView.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e){
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("The read failed: " ,databaseError.toString());
                }
            });
        }
        return true;
    }

}
