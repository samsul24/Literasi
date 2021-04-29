package com.example.maskoki.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.maskoki.R;
import com.example.maskoki.databinding.ActivityRegisterBinding;
import com.example.maskoki.models.User;
import com.example.maskoki.rest.services.ApiServices;
import com.example.maskoki.util.SharedPrefManager;
import com.example.maskoki.util.UtilApi;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private ApiServices apiServices;
    private SharedPrefManager sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        apiServices = UtilApi.getApiService();
        sp = new SharedPrefManager(this);
    }

    public void handleRegister(View view) {
        if (!validateEditText()) {
            String name = binding.etRegNama.getText().toString();
            String username = binding.etRegUsername.getText().toString();
            String password = binding.etRegPassword.getText().toString();

            doRegister(username, name, password);
        }
    }

    private void doRegister(String username, String name,  String password){
        Call<User> user = apiServices.registerRequest(username, name, password);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    sp.saveSPBoolean(SharedPrefManager.SP_LOGIN, true);
//                    sp.saveSPString(SharedPrefManager.SP_NAME, response.body().getName());
//                    sp.saveSPString(SharedPrefManager.SP_ID_USER, response.body().getId());
//                    sp.saveSPString(SharedPrefManager.SP_TOKEN, response.body().getToken());

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    showSnackbar(binding.btDaftar, "Username sudah terdaftar");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showSnackbar(binding.btDaftar, getString(R.string.error_koneksi_request_api));
            }
        });
    }

    private boolean validateEditText() {
        boolean isError = false;
        if (TextUtils.isEmpty(binding.etRegNama.getText().toString().trim())) {
            binding.etRegNama.setError("Ups! Namanya masih kosong.");
            isError = true;
        }

        if (TextUtils.isEmpty(binding.etRegUsername.getText().toString().trim())) {
            binding.etRegUsername.setError("Ups! Usernamenya masih kosong.");
            isError = true;
        }

        if (TextUtils.isEmpty(binding.etRegPassword.getText().toString().trim())) {
            binding.etRegPassword.setError("Ups! Passwordnya masih kosong.");
            isError = true;
        }

        if (TextUtils.isEmpty(binding.etRegPassword2.getText().toString().trim())) {
            binding.etRegPassword2.setError("Ups! Konfirmasi passwordnya masih kosong.");
            isError = true;
        }

        if (!TextUtils.equals(binding.etRegPassword2.getText().toString(), binding.etRegPassword.getText().toString())) {
            binding.etRegPassword2.setError("Ups! Konfirmasi passwordnya tidak sama.");
            isError = true;
        }

        return isError;
    }

    public void handeMoveToLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}