package com.example.karokojnr.nadab_hotels;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int notificationCountCart = 0;
    static ViewPager viewPager;
    static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        } else {
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
       /* MenuItem item = menu.findItem(R.id.action_cart);
        NotificationCountSetClass.setAddToCart(MainActivity.this, item,notificationCountCart);*/
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            startActivity(new Intent(MainActivity.this, SearchResultActivity.class));
            return true;
        }else if (id == R.id.action_cart) {

           *//* NotificationCountSetClass.setAddToCart(MainActivity.this, item, notificationCount);
            invalidateOptionsMenu();*//*
            startActivity(new Intent(MainActivity.this, CartListActivity.class));

           *//* notificationCount=0;//clear notification count
            invalidateOptionsMenu();*//*
            return true;
        }else {
            startActivity(new Intent(MainActivity.this, EmptyActivity.class));

        }*/
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
       /* Adapter adapter = new Adapter(getSupportFragmentManager());
        MealsFragment fragment = new MealsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.meals));

        fragment = new OrderFragment ();
        bundle = new Bundle();
        bundle.putInt("type", 2);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_2));

        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 3);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_3));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 4);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_4));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 5);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_5));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 6);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_6));
        viewPager.setAdapter(adapter);*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_add_meals) {
            startActivity(new Intent(MainActivity.this, AddMeals.class));
        } else if (id == R.id.nav_sign_out) {
            // Log.wtf(TAG, "onOptionsItemSelected: Logout");
            SharedPrefManager.getInstance ( getApplicationContext () ).logout ();
            startActivity ( new Intent ( getApplicationContext (), LoginActivity.class ) );
        }
        /*    this.finish();
        } else if (id == R.id.nav_item5) {
            viewPager.setCurrentItem(4);
        }else if (id == R.id.nav_item6) {
            viewPager.setCurrentItem(5);
        }else if (id == R.id.my_wishlist) {
            startActivity(new Intent(MainActivity.this, WishlistActivity.class));
        }else if (id == R.id.my_cart) {
            startActivity(new Intent(MainActivity.this, CartListActivity.class));
        }else {
            startActivity(new Intent(MainActivity.this, EmptyActivity.class));
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

     class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
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
                    return new MealsFragment ();
                case 1:
                    return new OrderFragment ();
                case 2:
                    return new BillsFragment ();
                case 3:
                    return new SalesFragment ();

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
                case 0: return  "Meals";
                case 1: return "Orders";
                case 2: return "Bills";
                case 3: return "Sales";
                default: return null;
            }
        }
    }
}
