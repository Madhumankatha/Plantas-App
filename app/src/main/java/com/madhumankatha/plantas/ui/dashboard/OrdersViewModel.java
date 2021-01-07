package com.madhumankatha.plantas.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.madhumankatha.plantas.models.Orders;
import com.madhumankatha.plantas.models.Products;
import com.madhumankatha.plantas.network.RetrofitApp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersViewModel extends ViewModel {
    private MutableLiveData<List<Orders>> ordersListLiveData = new MutableLiveData<>();


    public OrdersViewModel() {

    }

    public LiveData<List<Orders>> getOrdersLiveData(long userId){
        /*if (ordersListLiveData == null){
            ordersListLiveData = new MutableLiveData<>();
            getOrdersLiveDataFromApi(userId);
        }*/
        getOrdersLiveDataFromApi(userId);
        return ordersListLiveData;
    }


    private void getOrdersLiveDataFromApi(long userId){
        RetrofitApp.getInstance().getAppApi().getOrders(userId).enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if (response.isSuccessful()){
                    ordersListLiveData.setValue(response.body());
                    //Log.d("TAG", "onResponse: " + response.body().get(0).getProducts().getName());
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }


}
