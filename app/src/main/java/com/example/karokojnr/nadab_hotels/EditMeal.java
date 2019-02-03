package com.example.karokojnr.nadab_hotels;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Product;
import com.example.karokojnr.nadab_hotels.model.Products;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import retrofit2.Call;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class EditMeal extends AppCompatActivity {
    ImageView ivImage;
    EditText meal_name, price;
    FloatingTextButton edit, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_meal );

        ivImage = findViewById ( R.id.ivImage );
        meal_name = findViewById ( R.id.name );
        price = findViewById ( R.id.add_item_price );

        edit = findViewById ( R.id.btn_edit );
        cancel = findViewById ( R.id.btn_cancel );
    }

    private void updateMeal() {
        String image = ivImage.getDrawable ().toString ().trim ();
        String mealName = meal_name.getText ().toString ().trim ();
        String mealPrice = price.getText ().toString ().trim ();

        if (mealName.isEmpty ()) {
            meal_name.setError ( "Name is required" );
            meal_name.requestFocus ();
            return;
        }


        if (mealPrice.isEmpty ()) {
            price.setError ( "Price required" );
            price.requestFocus ();
            return;
        }

        if (image.isEmpty ()) {
            ivImage.setImageDrawable ( Drawable.createFromPath ( "Image is required!" ) );
            ivImage.requestFocus ();
            return;
        }


        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<Product> call = service.getProductsEdit (mealName);





    }
}
