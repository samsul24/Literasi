package com.example.maskoki.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.maskoki.R;
import com.example.maskoki.databinding.ActivityKomentarEditBinding;
import com.example.maskoki.models.Comment;
import com.example.maskoki.rest.services.ApiServices;
import com.example.maskoki.util.SharedPrefManager;
import com.example.maskoki.util.UtilApi;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KomentarEditActivity extends AppCompatActivity {

    private ActivityKomentarEditBinding binding;
    private Comment comment;
    private SharedPrefManager sp;
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_komentar_edit);
        sp = new SharedPrefManager(this);
        apiServices = UtilApi.getApiService();

        comment = Objects.requireNonNull(getIntent().getExtras().getParcelable("KEY_COMMENT"));
        binding.setComment(comment);
    }

    public void handleEditComment(View view) {
        if (!validateEditText()){
            String comment = binding.etKomentar.getText().toString();
            doEditComment(comment);
        }
    }

    public void handleDeleteComment(View view) {
        doDeleteComment();
    }

    private void doEditComment(String comment){
        String token = sp.getSPToken();
        int recipe_id = this.comment.getRecipeId();
        int comment_id = this.comment.getId();
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+token);

        Call<Comment> request = apiServices.editComment(recipe_id, comment_id, header, comment);
        request.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(KomentarEditActivity.this, DetailResepActivity.class);
                    startActivity(intent);
                }else{
                    showSnackbar(binding.btHapusKomentar, " " + response.errorBody());
                    try {
                        Log.e("EROR EDIT", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                showSnackbar(binding.btHapusKomentar, getString(R.string.error_koneksi_request_api));
            }
        });
    }

    private void doDeleteComment(){
        String token = sp.getSPToken();
        int comment_id = this.comment.getId();
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+token);

        Call<Comment> request = apiServices.deleteComment(header, comment_id);
        request.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(KomentarEditActivity.this, DetailResepActivity.class);
                    startActivity(intent);
                }else{
                    showSnackbar(binding.btHapusKomentar, " " + response.errorBody());
                    try {
                        Log.e("EROR HAPUS", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                showSnackbar(binding.btHapusKomentar, getString(R.string.error_koneksi_request_api));
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