package com.example.karokojnr.nadab_hotels;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.utils.Constants;

import java.text.NumberFormat;


public class ItemDetails extends AppCompatActivity {

    public static final String  FRAGRANCE_NAME = "fragranceName";
    public static final String  FRAGRANCE_DESCRIPTION = "fragranceDescription";
    public static final String  FRAGRANCE_RATING = "fragranceRating";
    public static final String  FRAGRANCE_IMAGE = "fragranceImage";
    public static final String  FRAGRANCE_PRICE = "fragrancePrice";

    private ImageView mImage;


    String fragranceName, name, fragImage;
    int rating;
    int price;
    private int mQuantity = 1;
    private double mTotalPrice;
    TextView costTextView;
    private SQLiteDatabase mDb;

    private int mNotificationsCount = 0;
    Button medit;

    // Item values
    private String itemName;
    private String itemPrice;
    private String itemUnitMeasure;
    private String itemImageUrl;

    public static final String TAG = ItemDetails.class.getSimpleName();
    private ImageView imageView;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvUnitMeasure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_deatails);

        Intent intent = getIntent();
        itemName = intent.getStringExtra(Constants.M_NAME);
        itemPrice = intent.getStringExtra(Constants.M_PRICE);
        itemUnitMeasure = intent.getStringExtra(Constants.M_UNITMEASURE);
        itemImageUrl = intent.getStringExtra(Constants.M_IMAGE);


        medit = (Button) findViewById(R.id.edit);
        imageView = (ImageView) findViewById(R.id.imageView);
        tvName = (TextView) findViewById(R.id.tvName);
        tvUnitMeasure = (TextView) findViewById(R.id.tvUnitMeasure);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        Switch aSwitch = (Switch) findViewById ( R.id.switch1 );

        tvName.setText(itemName);
        tvUnitMeasure.setText(itemUnitMeasure);
        tvPrice.setText("Kshs." + price);
        float f = Float.parseFloat(Double.toString(rating));
        setTitle(fragranceName);
        /*ratingBar = (RatingBar) findViewById(R.id.ratingLevel);
        ratingBar.setRating(f);*/
        Glide.with(this)
                .load(RetrofitInstance.BASE_URL+"images/uploads/thumbs/"+ itemImageUrl)
                .into(imageView);

        if (mQuantity == 1){
            mTotalPrice = Integer.parseInt(itemPrice);
            displayCost(mTotalPrice);
        }

    }

    private void displayCost(double totalPrice) {
        String convertPrice = NumberFormat.getCurrencyInstance().format(totalPrice);
        costTextView.setText(convertPrice);
    }


}
