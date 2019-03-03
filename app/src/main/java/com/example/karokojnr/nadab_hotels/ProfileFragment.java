package com.example.karokojnr.nadab_hotels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.utils.Constants;
import com.example.karokojnr.nadab_hotels.utils.HotelSharedPreference;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment  {


    private TextView tv_name,tv_email,tv_message,tv_applicant_name, tv_city,tv_address, tv_mobile, tv_paybill;
    private SharedPreferences pref;
    private Button btn_change_password;
    private FloatingActionButton btn_logout;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    ImageView imageView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment ();
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
        //return inflater.inflate ( R.layout.fragment_profile, container, false );
        View view = inflater.inflate ( R.layout.activity_profile, container, false);


        /*Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity ()).setSupportActionBar(toolbar);
        //setSupportActionBar(toolbar);
*/

        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_email = (TextView)view.findViewById(R.id.tv_email);
        tv_applicant_name = (TextView)view.findViewById ( R.id.tv_applicant_name );
        tv_city = (TextView)view.findViewById ( R.id.tv_city );
        tv_address = (TextView)view.findViewById ( R.id.tv_address );
        tv_paybill = (TextView)view.findViewById ( R.id.tv_paybill );
        tv_mobile = (TextView)view.findViewById ( R.id.tv_mobile );
        imageView = (ImageView)view.findViewById ( R.id.ivImage );



        //getting the current user
        HotelSharedPreference hotel = SharedPrefManager.getInstance(getContext ()).getHotel ();
        tv_name.setText(String.valueOf(hotel.getUsername ()));
        tv_email.setText(String.valueOf ( hotel.getEmail ()));
        tv_applicant_name.setText(String.valueOf ( hotel.getTv_applicant_name ()));
        tv_city.setText(String.valueOf ( hotel.getTv_city ()));
        tv_address.setText(String.valueOf ( hotel.getTv_address ()));
        tv_paybill.setText(String.valueOf ( hotel.getTv_paybill ()));
        tv_mobile.setText(String.valueOf ( hotel.getTv_mobile()));
        Glide.with(this)
                .load(RetrofitInstance.BASE_URL+"images/uploads/hotels/"+ String.valueOf ( hotel.getIvImage ()))
                .into(imageView);
        //imageView.setImageDrawable ( Drawable.createFromPath ( String.valueOf ( hotel.getIvImage ()) ) );




        btn_change_password = (Button)view.findViewById(R.id.btn_chg_password);
        btn_logout = (FloatingActionButton) view.findViewById(R.id.btn_logout);
        btn_change_password.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //changePasswordProcess (  );
            }
        } );
        btn_logout.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog.Builder and set the message, and click listeners
                // for the postivie and negative buttons on the dialog.
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext ());
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity ().finish();
                        SharedPrefManager.getInstance(getContext ()).logout();
                        startActivity(new Intent (getActivity (), LoginActivity.class));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Cancel" button, so dismiss the dialog
                        // and continue editing the items.
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

                // Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } );

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction ( uri );
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach ( context );
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException ( context.toString () + " must implement OnFragmentInteractionListener" );
        }
    }*/

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

    /*public void onClick (View v){
        switch (v.getId ()){
            case R.id.btn_logout:
                // Create an AlertDialog.Builder and set the message, and click listeners
                // for the postivie and negative buttons on the dialog.
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext ());
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity ().finish();
                        SharedPrefManager.getInstance(getContext ()).logout();
                        startActivity(new Intent (getActivity (), LoginActivity.class));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Cancel" button, so dismiss the dialog
                        // and continue editing the items.
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

                // Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                break;


        }


    }*/
    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext ());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = (EditText)view.findViewById(R.id.et_old_password);
        et_new_password = (EditText)view.findViewById(R.id.et_new_password);
        tv_message = (TextView)view.findViewById(R.id.tv_message);
        progress = (ProgressBar)view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_password.getText().toString();
                String new_password = et_new_password.getText().toString();
                if(!old_password.isEmpty() && !new_password.isEmpty()){

                    progress.setVisibility(View.VISIBLE);
                    //changePasswordProcess();

                }else {

                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Fields are empty");
                }
            }
        });
    }
   /* @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_chg_password:
                showDialog();
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }
*/

    private void goToLogin() {
        Intent intent = new Intent ( getActivity (), LoginActivity.class );
        startActivity ( intent );

            /*Fragment login = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,login);
            ft.commit();*/
    }
    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.UNIQUE_ID,"");
        editor.apply();
        goToLogin();
    }

    //@Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    /*private void changePasswordProcess(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HotelService requestInterface = retrofit.create(HotelService.class);

        Hotel hotel = new Hotel();
        hotel.setBusinessName (email;);
        hotel.setPassword (old_password;);
        hotel.setNew_password(new_password);
        HotelRegister request = new HotelRegister ();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setUser(hotel);
        Call<Hotel> response = requestInterface.operation(request);

        response.enqueue(new Callback<Hotel>() {
            @Override
            public void onResponse(Call<Hotel> call, retrofit2.Response<Hotel> response) {

                Hotel resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());

            }
        });
    }*/
}
