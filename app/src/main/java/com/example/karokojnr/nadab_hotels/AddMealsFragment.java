package com.example.karokojnr.nadab_hotels;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.adapter.ItemsAdapter;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Product;
import com.example.karokojnr.nadab_hotels.utils.HotelSharedPreference;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddMealsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddMealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMealsFragment extends Fragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks  {

    private ArrayList<Product> productList = new ArrayList<> ();
    private ItemsAdapter adapter;
    private static final String TAG = "Items";
    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView image;
    private Button btn_okay, btn_cancel;
    private EditText name, price;
    private Uri selectedImage;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private String filePath;
    private File file;

    private ProgressBar mLoading;

    private int progressStatus = 0;
    ProgressDialog progressDialog;
    private Handler handler = new Handler ();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddMealsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMealsFragment newInstance(String param1, String param2) {
        AddMealsFragment fragment = new AddMealsFragment ();
        Bundle args = new Bundle ();
        args.putString ( ARG_PARAM1, param1 );
        args.putString ( ARG_PARAM2, param2 );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        if (getArguments () != null) {
            mParam1 = getArguments ().getString ( ARG_PARAM1 );
            mParam2 = getArguments ().getString ( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate ( R.layout.activity_add_meals, container, false );
        View view = inflater.inflate ( R.layout.activity_add_meals, container, false);
        progressDialog = new ProgressDialog (getContext ());


        mLoading = (ProgressBar) view.findViewById(R.id.login_loading);


        /*Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        name = (EditText) view.findViewById ( R.id.name );
        price = (EditText) view.findViewById ( R.id.add_item_price );

        image = (ImageView) view.findViewById ( R.id.ivImage );
        image.setOnClickListener (  this );

        //btn_okay = (Button) view.findViewById ( R.id.btn_ok );
        //btn_cancel = (Button) view.findViewById ( R.id.btn_cancel );
        final ViewGroup mcontainer = (RelativeLayout) view.findViewById(R.id.container);

        FloatingTextButton btn_cancel = (FloatingTextButton) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mcontainer, "Home button clicked", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent ( getContext (), MainActivity.class );
                startActivity ( intent );
            }
        });
        FloatingTextButton btn_okay = (FloatingTextButton) view.findViewById(R.id.btn_ok);
        btn_okay.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //first getting the values
                String mname = name.getText().toString();
                String mprice = price.getText().toString();
                String mimage = image.getDrawable ().toString();

                //validating inputs
                if (TextUtils.isEmpty(  mname )) {
                    name.setError("Please enter Food name");
                    name.requestFocus();
                    return;
                }



                if (TextUtils.isEmpty(  mprice )) {
                    price.setError("Please enter price of the food");
                    price.requestFocus();
                    return;
                }
                /*if (TextUtils.isEmpty(  mimage )) {
                    image.setImageDrawable (Drawable.createFromPath ( "Please choose image of the Food" ) );
                    image.requestFocus();
                    return;
                }*/
                //  mLoading.setVisibility(View.VISIBLE); // show progress dialog*/
                showProgressDialogWithTitle (  );

                Snackbar.make(mcontainer, "Adding Meal...", Snackbar.LENGTH_SHORT).show();

                HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
                Product product = new Product(
                        name.getText ().toString ().trim (),
                        price.getText ().toString ().trim (),
                        image.getDrawable ().toString ().trim ()
                );
                //Set defaults
                HotelSharedPreference hotelSharedPreference = SharedPrefManager.getInstance(getActivity ()).getHotel();
                String hotelId = hotelSharedPreference.getId();
                productList.add (product);
                String filePath = getRealPathFromURIPath(selectedImage, getActivity ());
                File file = new File(filePath);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                RequestBody mName = RequestBody.create(MediaType.parse("text/plain"), name.getText().toString());
                RequestBody mPrice = RequestBody.create(MediaType.parse("text/plain"), price.getText().toString());
                RequestBody mUnitMeasure = RequestBody.create(MediaType.parse("text/plain"), "Box");
                RequestBody mHotelId = RequestBody.create(MediaType.parse("text/plain"), hotelId);
                Call<Product> call = service.addProduct(fileToUpload, filename, mName, mPrice, mUnitMeasure, mHotelId);

                call.enqueue ( new Callback<Product> () {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful ()) {
                            assert response.body () != null;
                            //mLoading.setVisibility(View.GONE);
                            hideProgressDialogWithTitle ();
                            Toast.makeText ( getActivity (), "Added Successfully...", Toast.LENGTH_SHORT ).show ();
                            Intent intent = new Intent ( getContext (), MainActivity.class );
                            startActivity ( intent );
                            //notify data set changed in RecyclerView adapter
//                            adapter.notifyDataSetChanged ();
                        } else {
                            // mLoading.setVisibility(View.INVISIBLE);
                            hideProgressDialogWithTitle ();
                            Toast.makeText ( getActivity (), "Error adding...", Toast.LENGTH_SHORT ).show ();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
//                        mLoading.setVisibility(View.INVISIBLE);
                        hideProgressDialogWithTitle ();
                        Toast.makeText ( getActivity (), "Something went wrong...Error message: " + t.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        } );
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity ().finish();
            }
        });
        //btn_cancel.setOnClickListener ( this );
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction ( uri );
        }
    }
/*

    @Override
    public void onAttach(Context context) {
        super.onAttach ( context );
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException ( context.toString () + " must implement OnFragmentInteractionListener" );
        }
    }
*/

    @Override
    public void onDetach() {
        super.onDetach ();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void onClick (View v){
        switch (v.getId ()){
            case R.id.ivImage:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult ( galleryIntent, RESULT_LOAD_IMAGE  );
                break;

            case R.id.btn_ok:

                break;

            case R.id.btn_cancel:
                break;
        }


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData ();
            image.setImageURI(selectedImage);
        }
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            if(EasyPermissions.hasPermissions(getContext (), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                filePath = getRealPathFromURIPath(selectedImage, getActivity ());
                file = new File(filePath);
                Log.d(TAG, "Filename " + file.getName());
            }else{
                EasyPermissions.requestPermissions(this, "This app needs access to your file storage so that it can read photos.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    //@Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, getActivity ());
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (selectedImage != null) {
            filePath = getRealPathFromURIPath(selectedImage, getActivity ());
            file = new File(filePath);
            Log.d(TAG, "Filename " + file.getName());
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }
    // Method to show Progress bar
    private void showProgressDialogWithTitle() {
        progressDialog.setTitle("Adding Meal");
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.show();

        // Start Process Operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    try{
                        // This is mock thread using sleep to show progress
                        Thread.sleep(200);
                        progressStatus += 5;
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    // Change percentage in the progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            progressDialog.setProgress(progressStatus);
                        }
                    });
                }
                //hide Progressbar after finishing process
                hideProgressDialogWithTitle();
            }
        }).start();

    }

    // Method to hide/ dismiss Progress bar
    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }

}
