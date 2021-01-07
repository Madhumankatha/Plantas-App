package com.madhumankatha.plantas.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.madhumankatha.plantas.models.Products;
import com.madhumankatha.plantas.network.RetrofitApp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsViewModel extends ViewModel {
    private MutableLiveData<List<Products>> productsListMutableLiveData;
    private MutableLiveData<Products> productSelected = new MutableLiveData<>();

    public LiveData<List<Products>> getProductsLiveData(){
        if (productsListMutableLiveData == null){
            productsListMutableLiveData = new MutableLiveData<>();
            getProductsLiveDataFromApi();
        }
        return productsListMutableLiveData;
    }

    public void setSelected(Products selected){
        productSelected.setValue(selected);
    }

    public MutableLiveData<Products> getSelected(){
        return productSelected;
    }



    private void getProductsLiveDataFromApi(){
        Log.d("TAG", "getArticlesLiveDataFromApi: ");
        RetrofitApp.getInstance().getAppApi().getProducts().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()){
                    productsListMutableLiveData.setValue(response.body());
                    Log.d("value 0","" + response.body().get(0).getName());
                }

            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Log.d("Error","" + t.getMessage());
            }
        });
    }

}
