package com.example.karokojnr.nadab_seller.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karokojnr.nadab_seller.R;
import com.example.karokojnr.nadab_seller.model.Hotel;
import com.example.karokojnr.nadab_seller.model.Response;
import com.example.karokojnr.nadab_seller.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.karokojnr.nadab_seller.utils.Validation.validateEmail;
import static com.example.karokojnr.nadab_seller.utils.Validation.validateFields;

public class RegisterFragment extends Fragment {

    public static final String TAG = RegisterFragment.class.getSimpleName();

    private EditText mEtbusinessName;

    private EditText mEtapplicantName;
    private EditText mEtbusinessEmail;
    private EditText mEtmobileNumber;
    private EditText mEtpaybillNo;
    private EditText mEtaddress;
    private EditText mEtcity;
    private EditText mEtpassword;
    private EditText mEtpaymentStatus;

    private Button   mBtRegister;

    private TextView mTvLogin;
    private TextInputLayout mTibusinessName;
    private TextInputLayout mTiapplicantName;
    private TextInputLayout mTibusinessEmail;
    private TextInputLayout mTimobileNumber;
    private TextInputLayout mTipaybillNo;
    private TextInputLayout mTiaddress;
    private TextInputLayout mTicity;
    private TextInputLayout mTipassword;
    private TextInputLayout mTipaymentStatus;
    private ProgressBar mProgressbar;

    private CompositeSubscription mSubscriptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        mSubscriptions = new CompositeSubscription();
        initViews(view);
        return view;
    }

    private void initViews(View v) {

        mEtbusinessName = (EditText) v.findViewById(R.id.et_businessName);
        mEtapplicantName = (EditText) v.findViewById(R.id.et_applicantName);
        mEtbusinessEmail = (EditText) v.findViewById(R.id.et_businessEmail);
        mEtmobileNumber = (EditText) v.findViewById(R.id.et_mobileNumber);
        mEtpaybillNo = (EditText) v.findViewById(R.id.et_paybillNo);
        mEtaddress = (EditText) v.findViewById(R.id.et_address);
        mEtcity = (EditText) v.findViewById(R.id.et_city);
        mEtpassword = (EditText) v.findViewById(R.id.et_password);
        mEtpaymentStatus = (EditText) v.findViewById(R.id.et_paymentStatus);

        mBtRegister = (Button) v.findViewById(R.id.btn_register);

        mTvLogin = (TextView) v.findViewById(R.id.tv_login);

        mTibusinessName = (TextInputLayout) v.findViewById(R.id.ti_businessName);
        mTiapplicantName = (TextInputLayout) v.findViewById(R.id.ti_applicantName);
        mTibusinessEmail = (TextInputLayout) v.findViewById(R.id.ti_businessEmail);
        mTimobileNumber = (TextInputLayout) v.findViewById(R.id.ti_mobileNumber);
        mTipaybillNo = (TextInputLayout) v.findViewById(R.id.ti_paybillNo);
        mTiaddress = (TextInputLayout) v.findViewById(R.id.ti_address);
        mTicity = (TextInputLayout) v.findViewById(R.id.ti_city);
        mTipassword = (TextInputLayout) v.findViewById(R.id.ti_password);
        mTipaymentStatus = (TextInputLayout) v.findViewById(R.id.ti_paymentStatus);
        mProgressbar = (ProgressBar) v.findViewById(R.id.progress);

        mBtRegister.setOnClickListener(view -> register());
        mTvLogin.setOnClickListener(view -> goToLogin());
    }

    private void register() {

        setError();

        String businessName = mEtbusinessName.getText().toString();
        String applicantName = mEtapplicantName.getText().toString();
        String businessEmail = mEtbusinessEmail.getText().toString();
        String mobileNumber = mEtmobileNumber.getText().toString();
        String paybillNo = mEtpaybillNo.getText().toString();
        String address = mEtaddress.getText().toString();
        String city = mEtcity.getText().toString();
        String password = mEtpassword.getText().toString();
        String paymentStatus = mEtpaymentStatus.getText().toString();


        int err = 0;

        if (!validateFields(businessName)) {

            err++;
            mTibusinessName.setError("Business name should not be empty !");
        }

        if (!validateFields (applicantName)) {

            err++;
            mTiapplicantName.setError("Application name should be valid !");
        }

        if (!validateEmail (businessEmail)) {

            err++;
            mTibusinessEmail.setError("Business email should not be empty !");
        }
        if (!validateFields (mobileNumber)) {

            err++;
            mTimobileNumber.setError("Mobile Number should not be empty !");
        }
        if (!validateFields(paybillNo)) {

            err++;
            mTipaybillNo.setError("Paybill No should not be empty !");
        }
        if (!validateFields(address)) {

            err++;
            mTiaddress.setError("Address should not be empty !");
        }
        if (!validateFields(city)) {

            err++;
            mTicity.setError("City should not be empty !");
        }
        if (!validateFields(password)) {

            err++;
            mTipassword.setError("Password  should not be empty !");
        }
        if (!validateFields(paymentStatus)) {

            err++;
            mTipaymentStatus.setError("Payment Status should not be empty !");
        }

        if (err == 0) {

            Hotel hotel = new Hotel ();
            hotel.setBusinessName(businessName);
            hotel.setApplicantName(applicantName);
            hotel.setBusinessEmail(businessEmail);
            hotel.setMobileNumber(mobileNumber);
            hotel.setPaybillNo(paybillNo);
            hotel.setAddress(address);
            hotel.setCity(city);
            hotel.setPassword(password);
            hotel.setPaymentStatus(paymentStatus);

            mProgressbar.setVisibility(View.VISIBLE);
            registerProcess( hotel );

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }
    }

    private void setError() {

        mTibusinessName.setError(null);
        mTiapplicantName.setError(null);
        mTibusinessEmail.setError(null);
        mTimobileNumber.setError(null);
        mTipaybillNo.setError(null);
        mTiaddress.setError(null);
        mTicity.setError(null);
        mTipassword.setError(null);
        mTipaymentStatus.setError(null);
    }

    private void registerProcess(Hotel hotel) {

        mSubscriptions.add(NetworkUtil.getRetrofit().register( hotel )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response) {

        mProgressbar.setVisibility(View.GONE);
        showSnackBarMessage(response.getMessage());
    }

    private void handleError(Throwable error) {

        mProgressbar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

    private void goToLogin(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        LoginFragment fragment = new LoginFragment();
        ft.replace(R.id.fragmentFrame, fragment, LoginFragment.TAG);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
