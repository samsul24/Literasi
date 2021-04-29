package com.example.maskoki.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskoki.R;
import com.example.maskoki.adapters.CommentsAdapter;
import com.example.maskoki.databinding.ActivityDetailResepBinding;
import com.example.maskoki.models.Comment;
import com.example.maskoki.models.Recipes;
import com.example.maskoki.rest.services.ApiServices;
import com.example.maskoki.util.SharedPrefManager;
import com.example.maskoki.util.UtilApi;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailResepActivity extends AppCompatActivity {

    public static String USER_ID = "";

    private ActivityDetailResepBinding binding;
    private ApiServices apiServices;
    private CommentsAdapter adapter;
    private SharedPrefManager sp;
    private Recipes recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_resep);
        sp = new SharedPrefManager(this);

        USER_ID = sp.getSPIdUser();

        recipes = Objects.requireNonNull(getIntent().getExtras().getParcelable("KEY_RECIPES"));
        binding.setRecipes(recipes);
        setImageRecipeBackground(recipes.getImage());

        apiServices = UtilApi.getApiService();
        Call<List<Comment>> comments = apiServices.listComments(recipes.getId());
        comments.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.body() != null) {
                    generateDataList(response.body());
                } else {
                    showSnackbar(binding.svDetail, "Resep ini masih belum ada yang mengkomentari lho, ayuk sampaikan komentarmu.");
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                showSnackbar(binding.svDetail, "Ups! Sepertnya koneksi kamu sedang bermasalah, coba lagi nanti.");
            }
        });
    }

    private void setImageRecipeBackground(String url) {
        Picasso.get()
                .load(Uri.parse(url))
                .into(binding.ivImageDetail);
    }

    private void generateDataList(List<Comment> commentList) {
        adapter = new CommentsAdapter(commentList);
        binding.rvComments.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailResepActivity.this);
        binding.rvComments.setLayoutManager(layoutManager);
    }

    public void handleMoveToKomentar(View view) {
        Intent intent = new Intent(DetailResepActivity.this, KomentarActivity.class);
        intent.putExtra("KEY_ID_RECIPE", recipes.getId());
        startActivity(intent);
    }

    private void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}