package com.madhumankatha.plantas.ui.others;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.madhumankatha.plantas.R;
import com.madhumankatha.plantas.databinding.FragmentLoginBinding;
import com.madhumankatha.plantas.models.Response;
import com.madhumankatha.plantas.models.Users;
import com.madhumankatha.plantas.network.RetrofitApp;
import com.madhumankatha.plantas.ui.home.ProductsViewModel;
import com.madhumankatha.plantas.ui.home.UserViewModel;
import com.madhumankatha.plantas.utlis.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private UserViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);

        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        requestServerIP();


        viewModel = new ViewModelProvider(requireActivity())
                .get(UserViewModel.class);

        binding.tvSignUp.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment);
        });


        return binding.getRoot();
    }


    void requestServerIP() {
        // get alert_dialog.xml view
        LayoutInflater li = LayoutInflater.from(requireActivity());
        View promptsView = li.inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireActivity());

        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);
        userInput.setText(AppUtils.API_ENDPOINT);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        AppUtils.API_ENDPOINT = userInput.getText().toString();
                        Toast.makeText(requireContext(), "Entered: "+userInput.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogin.setOnClickListener(v -> {
            Users users = new Users(0, binding.edUsername.getText().toString().trim(),
                    binding.edPassword.getText().toString().trim(), "", "", "");

            RetrofitApp.getInstance().getAppApi().doLogin(users).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()){
                        Log.d("TAG", "onResponse: " + response.body().getUsers().getUsername());
                        if (response.body().isStatus()){
                            Log.d("TAG", "onResponse: " + response.body().getUsers().getUsername());
                            Toast.makeText(requireContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            viewModel.setSelected(response.body().getUsers());
                            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_navigation_home);
                        }else {
                            Toast.makeText(requireContext(), "" + response.body().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.d("TAG", "onFailure: " + t.getMessage());
                }
            });

        });

    }
}
