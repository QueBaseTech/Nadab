package com.example.karokojnr.nadab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.karokojnr.nadab.adapter.DashboardAdapter;
import com.example.karokojnr.nadab.model.Dashboard;


import java.util.ArrayList;
import java.util.List;



public class DashboardFragment extends Fragment implements DashboardAdapter.mClickListener{
    ImageButton buttonMeal;
    FloatingActionButton fab;
    private DashboardAdapter listAdapter;
    private ArrayList<Dashboard> dashboardList = new ArrayList<> ();
    private List<DashboardRecyclerViewItem> dashboardItemList = null;

    //private RecyclerView recycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
//        return inflater.inflate(R.layout.fragment_dashboard, null);
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_dashboard, container, false );
        //return view;
        //Toolbar toolbar = (Toolbar) view.findViewById ( R.id.toolbar );
        // setSupportActionBar(toolbar);
       /*buttonMeal = (ImageButton) view.findViewById ( R.id.meal );
        buttonMeal.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( getActivity (), Items.class );
                startActivity ( i );
            }
        } );*/

        /*FloatingActionButton fab = (FloatingActionButton) view.findViewById ( R.id.meal );
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( getActivity (), Items.class );
                startActivity ( i );
            }
        } );*/



        RecyclerView recycler = (RecyclerView) view.findViewById ( R.id.recycler_view );


        initializeDashboardItemList ();


        /*recycler.setHasFixedSize(true);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recycler.setLayoutManager(new GridLayoutManager (getContext (), 2));
        } else {
            recycler.setLayoutManager(new GridLayoutManager (getContext (), 4));
        }
*/
        // Create the grid layout manager with 2 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext (), 2);
        // Set layout manager.
        recycler.setLayoutManager(gridLayoutManager);

        // Create car recycler view data adapter with car item list.
        DashboardAdapter dashboardAdapter = new DashboardAdapter (dashboardItemList);
        // Set data adapter.
        recycler.setAdapter(dashboardAdapter);



        return view;


    }

    /* Initialise car items in list. */
    private void initializeDashboardItemList()
    {
        if(dashboardItemList == null)
        {
            dashboardItemList = new ArrayList<DashboardRecyclerViewItem>();
            dashboardItemList.add(new DashboardRecyclerViewItem ("Add Meals", R.drawable.meals));
            dashboardItemList.add(new DashboardRecyclerViewItem ("Profile", R.drawable.profile_pic));

        }
    }


    @Override
    public void onClick(View v, int position) {

    }
}





