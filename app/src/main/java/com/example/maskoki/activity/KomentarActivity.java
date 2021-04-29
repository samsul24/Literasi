package com.example.maskoki.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.maskoki.R;
import com.example.maskoki.databinding.ActivityKomentarBinding;
import com.example.maskoki.models.Comment;
import com.example.maskoki.rest.services.ApiServices;
import com.example.maskoki.util.SharedPrefManager;
import com.example.maskoki.util.UtilApi;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KomentarActivity extends AppCompatActivity {

    private ActivityKomentarBinding binding;
    private SharedPrefManager sp;
    private ApiServices apiServices;
    private int recipe_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_komentar);
        apiServices = UtilApi.getApiService();
        sp = new SharedPrefManager(this);
        recipe_id = getIntent().getIntExtra("KEY_ID_RECIPE", 0);
    }

    public void handleAddComment(View view) {
        if (!validateEditText()){
            String comment = binding.etKomentar.getText().toString();
            doAddComment(comment);
        }
    }

    public void doAddComment(String comment){
        String token = sp.getSPToken();
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+token);

        Call<Comment> request = apiServices.addComment(recipe_id,header,comment);
        request.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(KomentarActivity.this, DetailResepActivity.class);
                    startActivity(intent);
                }else{
                    showSnackbar(binding.btTambahKomentar, "Terjadi eror saat komentar, coba lagi nanti");
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                showSnackbar(binding.btTambahKomentar, getString(R.string.error_koneksi_request_api));
            }
        });
    }

    private boolean validateEditText() {
        boolean isError = false;
        if (TextUtils.isEmpty(binding.etKomentar.getText().toString().trim())) {
            binding.etKomentar.setError("Ups! Kamu belum mengisi komentar.");
            isError = true;
        }
        return isError;
    }

    private void showSnackbar(View view, String message){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}