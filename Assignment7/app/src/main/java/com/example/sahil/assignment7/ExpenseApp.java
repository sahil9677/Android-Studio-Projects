package com.example.sahil.assignment7;


import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseApp extends Fragment {
    TextView textView;
    ListView listView;
    private static final String DESCRIBABLE_KEY = "describable_key";
    private static ArrayList<Expense> expenses1 = new ArrayList<Expense>();
    private DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("users/");

    public ExpenseApp() {
        // Required empty public constructor
    }
    OnAddButtonPressed mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAddButtonPressed) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "should implement OnFragmentTextChange");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                View view =inflater.inflate(R.layout.fragment_expense_app, container, false);
                return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenses1.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Expense myexpense = ds.getValue(Expense.class);
                    expenses1.add(myexpense);
                }
                try {
                    if(expenses1.size() > 0){
                        listView = (ListView) getActivity().findViewById(R.id.listView);
                        ExpenseAdapter adapter = new ExpenseAdapter(getActivity(), R.layout.expense_item, expenses1);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.d("demo", "Clicked Item " + position);
                            }
                        });
                        textView = getActivity().findViewById(R.id.textView2);
                        textView.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                mListener.itemClicked(position);
                            }
                        });
                    }else {
                        textView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.INVISIBLE);
                        /*
                        TextView tv = getActivity().findViewById(R.id.textView_EmptyListDescription);
                        tv.setVisibility(View.VISIBLE);
                        ListView lv = (ListView) getActivity().findViewById(R.id.MyListView);
                        lv.setVisibility(View.INVISIBLE);
                        */
                    }
                }
                catch (Exception e){
                    expenses1.clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.toString());
            }
        });
        listView = (ListView) getActivity().findViewById(R.id.listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mDatabase2.child(expenses1.get(position).getExpenseID()).removeValue();
                expenses1.remove(position);
                listView = (ListView) getActivity().findViewById(R.id.listView);
                ExpenseAdapter adapter = new ExpenseAdapter(getActivity(), R.layout.expense_item, expenses1);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("demo", "Clicked Item " + position);
                    }
                });
                mListener.itemLongClicked(expenses1);
                expenses1.clear();
                return true;
            }
        });
        getActivity().setTitle("Expense App");

        Button button = getActivity().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddButtonClicked();
            }
        });
    }



    public interface OnAddButtonPressed{
        void onAddButtonClicked();
        void itemClicked(int position);
        void itemLongClicked(ArrayList<Expense> expenses2);
    }
}
