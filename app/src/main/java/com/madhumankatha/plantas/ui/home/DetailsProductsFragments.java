package com.madhumankatha.plantas.ui.home;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.madhumankatha.plantas.MainActivity;
import com.madhumankatha.plantas.R;
import com.madhumankatha.plantas.databinding.DetailsProductBinding;
import com.madhumankatha.plantas.databinding.ProductsItemholderBinding;
import com.madhumankatha.plantas.utlis.AppUtils;

public class DetailsProductsFragments extends Fragment {
    private DetailsProductBinding binding;
    private ProductsViewModel productsViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailsProductBinding.inflate(inflater,container,false);

        productsViewModel = new ViewModelProvider(requireActivity()).get(ProductsViewModel.class);

        productsViewModel.getSelected().observe(getViewLifecycleOwner(), products -> {
            Glide.with(requireContext())
                    .load(AppUtils.API_ENDPOINT + "/images/" + products.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.image_eeror)
                    .error(R.drawable.image_eeror)
                    .fallback(R.drawable.image_eeror)
                    .into(binding.imageViewHeader);

            binding.tvTitle.setText(products.getName());
            binding.tvPrice.setText("₹ " + products.getPrice());
            binding.tvDesc.setText(products.getDescription());
            binding.tvDesc.setMovementMethod(new ScrollingMovementMethod());
            binding.tvTags.setText(products.getTags());
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAddToCart.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_detailsProductsFragments_to_checkOutFragment);
        });
    }
}
