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
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.madhumankatha.plantas.R;
import com.madhumankatha.plantas.databinding.FragmentLoginBinding;
import com.madhumankatha.plantas.databinding.FragmentSignupBinding;
import com.madhumankatha.plantas.models.Users;
import com.madhumankatha.plantas.network.RetrofitApp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private FragmentSignupBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentSignupBinding.inflate(inflater,container,false);

        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        binding.tvLogin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_loginFragment);
        });

        binding.btnSignUp.setOnClickListener(v -> {
            Users users = new Users(0,binding.edUsername.getText().toString(),
                    binding.edPassword.getText().toString(),
                    binding.edPhone.getText().toString(),
                    binding.edAddress.getText().toString(),
                    binding.edUsername.getText().toString());

            RetrofitApp.getInstance().getAppApi().createUsers(users).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.isSuccessful()){
                        if (response.body().isStatus()){
                            Toast.makeText(requireContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "onResponse: " + response.body().getUsername());
                        }else {
                            Toast.makeText(requireContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
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
