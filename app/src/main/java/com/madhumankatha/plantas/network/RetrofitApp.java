package com.madhumankatha.plantas.network;

import com.madhumankatha.plantas.utlis.AppUtils;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApp {
    private static RetrofitApp mInstance;
    private Retrofit mRetrofit;

    public RetrofitApp() {
        mRetrofit = new Retrofit.Builder().baseUrl(AppUtils.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitApp getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitApp();
        }
        return mInstance;
    }

    public APICalls getAppApi() {
        return mRetrofit.create(APICalls.class);
    }
}
