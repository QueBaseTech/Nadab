//package com.example.karokojnr.nadab;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.joe.nadabapp.api.HotelService;
//import com.example.joe.nadabapp.api.RetrofitInstance;
//import com.example.joe.nadabapp.model.Hotel;
//import com.example.joe.nadabapp.model.HotelRegister;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class AddHotel extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_hotel);
//
////        TODO:: initi UI components
//    }
//
//    public void addHotel(View view) {
//        HotelService service = RetrofitInstance.getRetrofitInstance().create(HotelService.class);
//        Hotel hotel = new Hotel();
//
//        // TODO:: Fetch fields from form
//        hotel.setApplicantName("Joasae");
//        hotel.setBusinessEmail("joesasnyugoh@gmail.com");
//        hotel.setBusinessName("Paraasdadadise Lost");
//        hotel.setAddress("56");
//        hotel.setCity("Nairobi");
//        hotel.setMobileNumber(792346789);
//        hotel.setPayBillNo("457890");
//        hotel.setPassword("password");
//        // TODO :: Remove all the hard coded values
//
//        Call<HotelRegister> call = service.addHotel(hotel);
//
//        call.enqueue(new Callback<HotelRegister>() {
//            @Override
//            public void onResponse(Call<HotelRegister> call, Response<HotelRegister> response) {
//
//                if(response.isSuccessful()) {
//                    Log.d("JOA", "Hotel:: "+response.body().getHotel().toString());
//                    startActivity(new Intent(AddHotel.this, NadabHotels.class));
//                }
//                else
//                    Toast.makeText(AddHotel.this,   "Error adding...", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<HotelRegister> call, Throwable t) {
//                Toast.makeText(AddHotel.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//}
