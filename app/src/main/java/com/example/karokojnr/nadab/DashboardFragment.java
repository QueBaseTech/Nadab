package com.example.karokojnr.nadab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.karokojnr.nadab.R;

public class DashboardFragment extends Fragment {
    ImageButton buttonMeal;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
//        return inflater.inflate(R.layout.fragment_dashboard, null);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard,
                container, false);
        //return view;
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
       /*buttonMeal = (ImageButton) view.findViewById ( R.id.meal );
        buttonMeal.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( getActivity (), Items.class );
                startActivity ( i );
            }
        } );*/

        FloatingActionButton fab = (FloatingActionButton) view.findViewById ( R.id.meal );
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( getActivity (), Items.class );
                startActivity ( i );
            }
        } );
        return view;


    }
}