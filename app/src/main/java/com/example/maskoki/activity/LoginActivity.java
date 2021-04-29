package com.example.maskoki.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.maskoki.R;
import com.example.maskoki.databinding.ActivityLoginBinding;
import com.example.maskoki.models.Auth;
import com.example.maskoki.models.Recipes;
import com.example.maskoki.models.User;
import com.example.maskoki.rest.services.ApiServices;
import com.example.maskoki.util.SharedPrefManager;
import com.example.maskoki.util.UtilApi;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ApiServices apiServices;
    private SharedPrefManager sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        apiServices = UtilApi.getApiService();
        sp = new SharedPrefManager(this);
    }

    public void handleLogin(View view) {
        if (!validateEditText()) {
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            doLogin(username, password);
        }
    }

    private void doLogin(String username, String password) {
        Call<User> user = apiServices.loginRequest(username, password);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    sp.saveSPBoolean(SharedPrefManager.SP_LOGIN, true);
                    sp.saveSPString(SharedPrefManager.SP_NAME, response.body().getData().getUsername());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    System.out.println(response);
                    showSnackbar(binding.btLogin, "Username atau password salah!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.toString());
                showSnackbar(binding.btLogin, getString(R.string.error_koneksi_request_api));
            }
        });
    }

    private boolean validateEditText() {
        boolean isError = false;
        if (TextUtils.isEmpty(binding.etUsername.getText().toString().trim())) {
            binding.etUsername.setError("Ups! Usernamenya masih kosong.");
            isError = true;
        }

        if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
            binding.etPassword.setError("Ups! Passwordnya masih kosong.");
            isError = true;
        }
        return isError;
    }

    public void handleMoveToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}