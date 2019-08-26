package com.example.sahil.assignment7;
// Sahil Deshmukh 801100363

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditExpense extends Fragment {
    int expenseID = 0;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Double amount1;
    String name1;
    private static final String DESCRIBABLE_KEY = "describable_key";
    private Expense expense = new Expense();

    public void expenseToEdit(Expense expense1){
        expense = expense1;
    }
    Interface2 data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_expense, container, false);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            data = (Interface2) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "should implement OnFragmentTextChange");
        }
    }
    EditText edit1;
    EditText edit3;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Edit Expense");
        Spinner spinner = getActivity().findViewById(R.id.eespinner);
        edit1 = getActivity().findViewById(R.id.eText1);
        edit3 = getActivity().findViewById(R.id.eText2);
        edit1.setText(expense.name);
        edit3.setText(expense.amount.toString());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categoryArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String setCategory = expense.category;
        int pos = adapter.getPosition(setCategory);
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String [] category = getResources().getStringArray(R.array.categoryArray);
                expense.category = category[position];
                Log.d("demo", expense.category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please enter a value", Toast.LENGTH_SHORT).show();
            }
        });
        Button add = (Button) getActivity().findViewById(R.id.eebutton3);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1 = String.valueOf(edit1.getText());
                Log.d("beforeClick", name1);
                String x = edit3.getText().toString();
                if (x.equals("")){
                    Log.d("demo", "amount is null");
                    edit1.setError("Please Enter a Value");
                }else{
                    amount1 = Double.parseDouble(x);
                }
                if (name1.equals("")){
                    Toast.makeText(getActivity(), "Please enter a value", Toast.LENGTH_SHORT).show();
                    edit3.setError("Please Enter a Value");
                }else{
                    expense.name = name1;
                    Log.d("Expense Name", expense.name);
                    expense.amount = amount1;
                    Log.d("Expense amount", expense.amount.toString());
                    expense.date = Calendar.getInstance().getTime();
                    Log.d("Expense date", expense.date.toString());
                    Log.d("demo", String.valueOf(expense));
                    DatabaseReference ref2 = mDatabase.child("users");
                    String key = expense.getExpenseID();
                    ref2.child(key).setValue(expense);
                    data.onExpenseChanged(expense);
                }
            }
        });
        Button close = getActivity().findViewById(R.id.eebutton4);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.closed();
            }
        });
    }

    public interface Interface2{
        public void onExpenseChanged(Expense expense);
        public void closed();
    }

}
