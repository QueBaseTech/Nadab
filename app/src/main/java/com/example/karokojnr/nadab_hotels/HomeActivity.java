package com.example.karokojnr.nadab_hotels;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity{
    ActionBar actionBar;

    final Fragment fragment1 = new MealsFragment ();
    final Fragment fragment2 = new DashboardFragment ();
    //final Fragment fragment3 = new NotificationsFragment();
    final FragmentManager fm = getSupportFragmentManager ();
    Fragment active = fragment1;

    private TextView mTextMessage;
    private ActionBar toolbar;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_home );

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar ( toolbar );


        BottomNavigationView navigation = (BottomNavigationView) findViewById ( R.id.navigation );
        navigation.setOnNavigationItemSelectedListener ( mOnNavigationItemSelectedListener );


        //   fm.beginTransaction().add(R.id.frame_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction ().add ( R.id.frame_container, fragment2, "2" ).hide ( fragment2 ).commit ();
        fm.beginTransaction ().add ( R.id.frame_container, fragment1, "1" ).commit ();



    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener () {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //HomeActivity.super.onBackPressed();

                        System.exit(0);
                    }
                }).create().show();
    }



    /*//Styling for double press back button
    private static long back_pressed;
    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }*/




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener () {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId ()) {
                case R.id.navigation_home:
                    Toolbar toolbar =findViewById(R.id.toolbar);
                    setSupportActionBar ( toolbar );
                    toolbar.setTitle("Meals");
                    fm.beginTransaction ().hide ( active ).show ( fragment1 ).commit ();
                    active = fragment1;
                    return true;

                case R.id.navigation_dashboard:
                    Toolbar toolbar1 =findViewById(R.id.toolbar);
                    setSupportActionBar ( toolbar1 );
                    toolbar1.setTitle("Dashboard");
                    fm.beginTransaction ().hide ( active ).show ( fragment2 ).commit ();
                    active = fragment2;
                    return true;

//            case R.id.navigation_notifications:
//                fm.beginTransaction().hide(active).show(fragment3).commit();
//                active = fragment3;
//                return true;
            }
            return false;
        }

    };
}

    /*private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }*/


