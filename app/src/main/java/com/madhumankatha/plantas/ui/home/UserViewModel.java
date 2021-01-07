package com.madhumankatha.plantas.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.madhumankatha.plantas.models.Products;
import com.madhumankatha.plantas.models.Users;

import java.util.List;

public class UserViewModel extends ViewModel {
    private MutableLiveData<Users> usersMutableLiveData = new MutableLiveData<>();

    public void setSelected(Users selected){
        usersMutableLiveData.setValue(selected);
    }

    public MutableLiveData<Users> getSelected(){
        return usersMutableLiveData;
    }

}
