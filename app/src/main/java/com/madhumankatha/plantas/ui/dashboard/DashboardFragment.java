package com.madhumankatha.plantas.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.madhumankatha.plantas.R;
import com.madhumankatha.plantas.databinding.FragmentHomeBinding;
import com.madhumankatha.plantas.databinding.FragmentLoginBinding;
import com.madhumankatha.plantas.databinding.FragmentOrdersBinding;
import com.madhumankatha.plantas.models.Orders;
import com.madhumankatha.plantas.models.Products;
import com.madhumankatha.plantas.ui.home.ProductsAdapter;
import com.madhumankatha.plantas.ui.home.ProductsViewModel;
import com.madhumankatha.plantas.ui.home.UserViewModel;
import com.madhumankatha.plantas.utlis.VerticalItemDeco;
import com.madhumankatha.plantas.utlis.VerticalSpaceItemDeco;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private FragmentOrdersBinding binding;
    private OrdersViewModel viewModel;
    private UserViewModel userViewModel;
    private OrdersAdapter adapter;
    private List<Orders> _orders;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater,container,false);

        viewModel = new ViewModelProvider(requireActivity())
                .get(OrdersViewModel.class);

        userViewModel = new ViewModelProvider(requireActivity())
                .get(UserViewModel.class);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false));
        int verticalSpacing = 20;
        VerticalSpaceItemDeco itemDecorator =
                new VerticalSpaceItemDeco(verticalSpacing);
        VerticalItemDeco shadowItemDecorator =
                new VerticalItemDeco(requireContext(), R.drawable.drop_shadow);
        binding.recyclerView.addItemDecoration(itemDecorator);
        binding.recyclerView.addItemDecoration(shadowItemDecorator);

        viewModel.getOrdersLiveData(userViewModel.getSelected().getValue().getId()).observe(getViewLifecycleOwner(),orders -> {
            if (orders != null){
                adapter = new OrdersAdapter(orders,requireContext());
                binding.recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else {
                Toast.makeText(requireContext(), "No Internet..!!", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}