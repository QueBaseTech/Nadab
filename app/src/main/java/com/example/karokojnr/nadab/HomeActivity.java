package com.example.karokojnr.nadab;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

public class HomeActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    private TextView mTextMessage;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_home );

        //loading the default fragment
        loadFragment(new DashboardFragment ());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
//            case R.id.navigation_home:
//                fragment = new MealsFragment ();
//                break;

            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                break;

            /*case R.id.navigation_notifications:
                fragment = new NotificationsFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;*/
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

