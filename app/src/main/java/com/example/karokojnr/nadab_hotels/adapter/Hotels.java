package com.example.karokojnr.nadab_hotels.adapter;//package com.example.karokojnr.nadab.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//
//import com.example.karokojnr.nadab.R;
//import com.example.karokojnr.nadab.model.Hotel;
//
//import java.util.ArrayList;
//
//public class Hotels extends RecyclerView.Adapter<Hotels.HotelViewHolder> {
//
//    private ArrayList<Hotel> dataList;
//
//    public Hotels(ArrayList<Hotel> dataList) {
//        this.dataList = dataList;
//    }
//
//    @Override
//    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.hotel_row, parent, false);
//        return new HotelViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(HotelViewHolder holder, int position) {
//        holder.hotelName.setText(dataList.get(position).getBusinessName());
//        holder.hotelPaybill.setText(dataList.get(position).getPayBillNo());
//        holder.hotelMobile.setText(dataList.get(position).getMobileNumber());
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    class HotelViewHolder extends RecyclerView.ViewHolder {
//
//        TextView hotelName, hotelMobile, hotelPaybill;
//
//        HotelViewHolder(View itemView) {
//            super(itemView);
//            hotelName =  itemView.findViewById(R.id.hotel_name);
//            hotelMobile =  itemView.findViewById(R.id.hotel_phone_number);
//            hotelPaybill =  itemView.findViewById(R.id.hotel_paybill);
//        }
//    }
//}
