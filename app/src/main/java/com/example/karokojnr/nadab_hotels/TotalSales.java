package com.example.karokojnr.nadab_hotels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TotalSales extends AppCompatActivity {

    TextView dailyOrders, dailySales, weeklyOrders, weeklySales, monthlyOrders, monthlySales, yearlyOrders, yearlySales, totalOrders, totalSales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_total_sales );

        dailyOrders = (TextView) findViewById ( R.id.dailyOrders );
        dailySales = (TextView) findViewById ( R.id.dailySales );
        weeklyOrders = (TextView) findViewById ( R.id.weeklyOrders );
        weeklySales = (TextView) findViewById ( R.id.weeklySales );
        monthlyOrders = (TextView) findViewById ( R.id.monthlyOrders );
        monthlySales = (TextView) findViewById ( R.id.monthlySales );
        yearlyOrders = (TextView) findViewById ( R.id.yearlyOrders );
        yearlySales = (TextView) findViewById ( R.id.yearlySales );
        totalOrders = (TextView) findViewById ( R.id.totalOrders );
        totalSales = (TextView) findViewById ( R.id.totalSales );


    }
}
