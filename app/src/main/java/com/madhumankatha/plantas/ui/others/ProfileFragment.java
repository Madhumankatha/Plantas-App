package com.madhumankatha.plantas.ui.others;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.madhumankatha.plantas.databinding.FragmentLoginBinding;
import com.madhumankatha.plantas.databinding.FragmentProfileBinding;
import com.madhumankatha.plantas.utlis.AppUtils;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);

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
