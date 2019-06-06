package com.example.karokojnr.nadab_hotels;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.karokojnr.nadab_hotels.ui.main.SectionsPagerAdapter;

public class HotelSales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_hotel_sales );
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter ( this, getSupportFragmentManager () );
        ViewPager viewPager = findViewById ( R.id.view_pager );
        viewPager.setAdapter ( sectionsPagerAdapter );
        TabLayout tabs = findViewById ( R.id.tabs );
        tabs.setupWithViewPager ( viewPager );
    }
}