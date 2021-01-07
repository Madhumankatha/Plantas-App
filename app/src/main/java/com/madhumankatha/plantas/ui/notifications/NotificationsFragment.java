package com.madhumankatha.plantas.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.madhumankatha.plantas.R;
import com.madhumankatha.plantas.databinding.FragmentProfileBinding;
import com.madhumankatha.plantas.databinding.FragmentSignupBinding;
import com.madhumankatha.plantas.utlis.AppUtils;

public class NotificationsFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       binding = FragmentProfileBinding.inflate(inflater,container,false);

       binding.edServer.setText(AppUtils.API_ENDPOINT);

        binding.btnConfirm.setOnClickListener(v -> {
            AppUtils.API_ENDPOINT = binding.edServer.getText().toString();
        });

       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}