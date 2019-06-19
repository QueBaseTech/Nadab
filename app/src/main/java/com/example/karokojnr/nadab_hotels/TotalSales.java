package com.example.karokojnr.nadab_hotels;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Stat;
import com.example.karokojnr.nadab_hotels.model.Stats;
import com.example.karokojnr.nadab_hotels.model.StatsResponse;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class TotalSales extends AppCompatActivity {
    private Context context;
    private ArrayList<Stat> statArrayList;
    private Stats stats;
    TextView dailySales, dailyPrice;
    TextView weeklySales, weeklyPrice;
    TextView monthlySales, monthlyPrice;
    TextView yearlySales, yearlyPrice;
    TextView totalSales, totalPrice;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_total_sales );

        context = this;

        initTextViews();
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<StatsResponse> call = service.getStats( SharedPrefManager.getInstance(this).getToken() );
        call.enqueue ( new Callback<StatsResponse>() {
            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                if(response.body().isSuccessful()){
                    Log.wtf("Stats", "onResponse: "+ response.body().getStats().toString() );
                    stats = response.body().getStats();
                    Stat stat = stats.getToday();
                    dailyPrice.setHint(Double.toString(stat.getTotalPrice()));
                    dailySales.setHint(Double.toString(stat.getTotalItems()));

                    stat = stats.getCurrentWeek();
                    weeklyPrice.setHint(Double.toString(stat.getTotalPrice()));
                    weeklySales.setHint(Double.toString(stat.getTotalItems()));

                    stat = stats.getCurrentMonth();
                    monthlyPrice.setHint(Double.toString(stat.getTotalPrice()));
                    monthlySales.setHint(Double.toString(stat.getTotalItems()));

                    stat = stats.getCurrentYear();
                    yearlyPrice.setHint(Double.toString(stat.getTotalPrice()));
                    yearlySales.setHint(Double.toString(stat.getTotalItems()));

                    stat = stats.getTotal();
                    totalPrice.setHint(Double.toString(stat.getTotalPrice()));
                    totalSales.setHint(Double.toString(stat.getTotalItems()));

                }
                showStats();
            }

            @Override
            public void onFailure(Call<StatsResponse> call, Throwable t) {
                Toast.makeText ( getApplicationContext (), "Something went wrong...Please try later!", Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void initTextViews() {
        dailySales = (TextView) findViewById(R.id.dailyOrders);
        dailyPrice = (TextView) findViewById(R.id.dailySales);
        weeklySales = (TextView) findViewById(R.id.weeklyOrders);
        weeklyPrice = (TextView) findViewById(R.id.weeklySales);
        monthlySales = (TextView) findViewById(R.id.monthlyOrders);
        monthlyPrice = (TextView) findViewById(R.id.monthlySales);
        yearlySales = (TextView) findViewById(R.id.yearlyOrders);
        yearlyPrice = (TextView) findViewById(R.id.yearlySales);
        totalSales = (TextView) findViewById(R.id.totalOrders);
        totalPrice = (TextView) findViewById(R.id.totalSales);
    }

    private void showStats() {

    }
}
