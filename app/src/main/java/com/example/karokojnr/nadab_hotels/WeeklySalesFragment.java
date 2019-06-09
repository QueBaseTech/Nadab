package com.example.karokojnr.nadab_hotels;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Stats;
import com.example.karokojnr.nadab_hotels.model.StatsResponse;
import com.example.karokojnr.nadab_hotels.utils.utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeeklySalesFragment extends Fragment {

    TextView totalOrders, totalSales;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_sales, container, false);

        totalOrders = (TextView) view.findViewById(R.id.total_orders);
        totalSales = (TextView) view.findViewById(R.id.total_sales);
        HotelService service = RetrofitInstance.getRetrofitInstance().create(HotelService.class);
        Call<StatsResponse> call = service.getStats(utils.getToken(getContext()));
        call.enqueue(new Callback<StatsResponse>() {
            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                if (response.body().isSuccessful()) {
                    Stats stats = response.body().getStats();
                    totalOrders.setText("" + stats.getCurrentWeek().getTotalItems());
                    totalSales.setText("" + stats.getCurrentWeek().getTotalPrice());
                }
            }

            @Override
            public void onFailure(Call<StatsResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}


