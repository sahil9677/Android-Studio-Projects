package com.example.sahil.fragmentdemo;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AFragment extends Fragment {
    public  void changeColor(int color){
        getView().setBackgroundColor(color);
        //getActivity().findViewById(R.id.fragmentroot).setBackgroundColor(color);
    }

    public AFragment() {
        // Required empty public constructor
    }

    OnFragmentTextChange mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("demo", "AFragment: onAttach");
        try {
            mListener = (OnFragmentTextChange) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "should implement OnFragmentTextChange");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("demo", "Afragment: onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("demo", "Afragment: onActivityCreated");
        getActivity().findViewById(R.id.editText);
        getActivity().findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) getActivity().findViewById(R.id.editText);
                mListener.onTextChanged(et.getText().toString());
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("demo", "Afragment: onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("demo", "Afragment: onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("demo", "Afragment: onResume");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("demo", "AFragment: onCreateView");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_a, container, false);

        /*view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button Pressed", Toast.LENGTH_SHORT).show();
            }
        });
        */
        return view;
    }

    public interface OnFragmentTextChange{
        void onTextChanged(String text);
    }


}
