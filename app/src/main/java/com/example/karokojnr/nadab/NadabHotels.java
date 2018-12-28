//package com.example.karokojnr.nadab;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.widget.Toast;
//
//import com.example.joe.nadabapp.adapter.Hotels;
//import com.example.joe.nadabapp.api.HotelService;
//import com.example.joe.nadabapp.api.RetrofitInstance;
//import com.example.joe.nadabapp.model.Hotel;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class NadabHotels extends AppCompatActivity {
//    private Hotels adapter;
//    private RecyclerView recyclerView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hotels);
//
//        fetchHotels();
//    }
//
//    private void fetchHotels() {
//        /** Create handle for the RetrofitInstance interface*/
//        HotelService service = RetrofitInstance.getRetrofitInstance().create(HotelService.class);
//
//        /** Call the method with parameter in the interface to get the notice data*/
//        Call<com.example.joe.nadabapp.model.HotelsList> call = service.getHotels();
//
//
//        call.enqueue(new Callback<com.example.joe.nadabapp.model.HotelsList>() {
//            @Override
//            public void onResponse(Call<com.example.joe.nadabapp.model.HotelsList> call, Response<com.example.joe.nadabapp.model.HotelsList> response) {
//                generateHotelList(response.body().getHotelsArrayList());
//            }
//
//            @Override
//            public void onFailure(Call<com.example.joe.nadabapp.model.HotelsList> call, Throwable t) {
//                Toast.makeText(NadabHotels.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    /** Method to generate List of notice using RecyclerView with custom adapter*/
//    private void generateHotelList(ArrayList<Hotel> noticeArrayList) {
//        recyclerView = findViewById(R.id.hotels_list);
//        adapter = new Hotels(noticeArrayList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NadabHotels.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }
//}
