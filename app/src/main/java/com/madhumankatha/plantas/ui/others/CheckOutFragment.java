package com.madhumankatha.plantas.ui.others;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.madhumankatha.plantas.R;
import com.madhumankatha.plantas.databinding.FragmentCheckoutBinding;
import com.madhumankatha.plantas.models.Orders;
import com.madhumankatha.plantas.models.Products;
import com.madhumankatha.plantas.models.Users;
import com.madhumankatha.plantas.network.RetrofitApp;
import com.madhumankatha.plantas.ui.dashboard.OrdersViewModel;
import com.madhumankatha.plantas.ui.home.ProductsViewModel;
import com.madhumankatha.plantas.ui.home.UserViewModel;
import com.madhumankatha.plantas.utlis.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutFragment extends Fragment {
    private FragmentCheckoutBinding binding;
    private UserViewModel userViewModel;
    private ProductsViewModel productsViewModel;

    private Products _products = new Products();
    private Users _users = new Users();

    private Orders orders;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater,container,false);

        productsViewModel = new ViewModelProvider(requireActivity())
                .get(ProductsViewModel.class);

        userViewModel = new ViewModelProvider(requireActivity())
                .get(UserViewModel.class);

        orders = new Orders();

        productsViewModel.getSelected().observe(getViewLifecycleOwner(), products1 -> {
            Log.d("TAG", "onCreateView: " + products1.getName());

            orders.setPid(products1.getId());

            Glide.with(requireContext())
                    .load(AppUtils.IMAGE_PATH + products1.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.image_eeror)
                    .error(R.drawable.image_eeror)
                    .fallback(R.drawable.image_eeror)
                    .into(binding.imdOrderHead);

            binding.tvOrderTitle.setText(products1.getName());
            binding.tvOrderPrice.setText("₹ " + products1.getPrice());
            binding.tvOrderQty.setText("Qty : x1");
        });


        userViewModel.getSelected().observe(getViewLifecycleOwner(),users -> {
            binding.tvTitle.setText(users.getUsername());
            binding.tvPrice.setText(users.getPhone());
            binding.tvTags.setText(users.getAddress());

            orders.setUid((int) users.getId());
        });


        orders.setTotal(1);
        //orders.setCreatedAt("29/12/2020 12:00:00 AM");
        orders.setStatus("Confirmed");

        binding.btnCheckOut.setOnClickListener(v -> {
            RetrofitApp.getInstance().getAppApi().makeOrder(orders).enqueue(new Callback<Orders>() {
                @Override
                public void onResponse(Call<Orders> call, Response<Orders> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(requireContext(), "Order Placed Successful!!" , Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_checkOutFragment_to_navigation_dashboard);
                    }
                }

                @Override
                public void onFailure(Call<Orders> call, Throwable t) {
                    Log.d("TAG", "onFailure: " + t.getMessage());
                }
            });
        });
        binding.btnCheckOut.setOnClickListener(v -> {
            RetrofitApp.getInstance().getAppApi().makeOrder(orders).enqueue(new Callback<Orders>() {
                @Override
                public void onResponse(Call<Orders> call, Response<Orders> response) {
                    Log.d("TAG", "onResponse: " + response.code());
                    Log.d("TAG", "onResponse: " + response.message());
                    Log.d("TAG", "onResponse: " + response.errorBody());
                    if (response.isSuccessful()){
                        Log.d("TAG", "onResponse: " );
                        Log.d("TAG", "onResponse: " + response.body().getUid());
                        Toast.makeText(requireContext(), "Order Placed Successful!!" , Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_checkOutFragment_to_navigation_dashboard);
                    }
                }

                @Override
                public void onFailure(Call<Orders> call, Throwable t) {
                    Log.d("TAG", "onFailure: " + t.getMessage());
                }
            });
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
