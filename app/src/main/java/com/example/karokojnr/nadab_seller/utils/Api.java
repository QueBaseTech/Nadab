package com.example.karokojnr.nadab_seller.utils;

import static com.example.karokojnr.nadab_seller.utils.Constants.BASE_URL;

public class Api {
    private Api() {}

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
