package com.example.karokojnr.nadab_hotels;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.utils.HotelSharedPreference;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class Sales extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static ViewPager viewPager;
    static TabLayout tabLayout;
    private final static int EXIT_CODE = 100;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EXIT_CODE) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra("EXIT", true)) {
                    finish();
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_sales );

        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById ( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.setDrawerListener ( toggle );
        toggle.syncState ();

        NavigationView navigationView = (NavigationView) findViewById ( R.id.nav_view );
        View headerView = navigationView.getHeaderView ( 0 );
        TextView navUsername = (TextView) headerView.findViewById ( R.id.navTextview );
        ImageView navImageview = (ImageView) headerView.findViewById ( R.id.imageView );
        navigationView.setNavigationItemSelectedListener(this);


        viewPager = (ViewPager) findViewById ( R.id.viewpager );
        PagerAdapter pagerAdapter = new Adapter ( getSupportFragmentManager () );
        viewPager.setAdapter ( pagerAdapter );
        tabLayout = (TabLayout) findViewById ( R.id.tabs );

        if (viewPager != null) {
            setupViewPager ( viewPager );
            tabLayout.setupWithViewPager ( viewPager );
        }


        // tabLayout.setupWithViewPager (  viewPager);

        HotelSharedPreference hotel = SharedPrefManager.getInstance ( this ).getHotel ();
        navUsername.setText ( hotel.getUsername () );
        Glide.with ( this ).load ( RetrofitInstance.BASE_URL + "images/uploads/hotels/" + String.valueOf ( hotel.getIvImage () ) ).into ( navImageview );

    }
    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen( GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else

            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener () {

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface arg0, int arg1) {

                            finishAffinity();
                        }
                    }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(Sales.this, ProfileActivity.class));
        } else if (id == R.id.nav_add_meals) {
            startActivity(new Intent(Sales.this, AddMeals.class));
       /* } else if (id == R.id.nav_hotel_sales) {
            startActivity(new Intent(Sales.this, Sales.class));*/
        } else if (id == R.id.nav_sign_out) {
            // Log.wtf(TAG, "onOptionsItemSelected: Logout");
            SharedPrefManager.getInstance ( getApplicationContext () ).logout ();
            startActivity ( new Intent ( getApplicationContext (), LoginActivity.class ) );
        }else if (id == R.id.terms_conditions){
            startActivity(new Intent(Sales.this, Terms.class));

        }else if (id == R.id.nav_fees){
            startActivity(new Intent(Sales.this, FeesActivity.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<> ();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DailySalesFragment ();
                case 1:
                    return new WeeklySalesFragment ();
                case 2:
                    return new MonthlySalesFragment ();
                case 3:
                    return new TotalSalesFragment ();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                //Tab titles
                case 0: return  "Daily Sales";
                case 1: return "Weekly Sales";
                case 2: return "Monthly Sales";
                case 3: return "Total Sales";
                default: return null;
            }
        }
    }

}

