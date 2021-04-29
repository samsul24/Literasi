package com.example.maskoki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskoki.R;
import com.example.maskoki.adapters.RecipesAdapter;
import com.example.maskoki.databinding.ActivityMainBinding;
import com.example.maskoki.models.Recipes;
import com.example.maskoki.rest.services.ApiServices;
import com.example.maskoki.util.SharedPrefManager;
import com.example.maskoki.util.UtilApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPrefManager sharedPrefManager;
//    private ApiServices apiServices;
//    private RecipesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);
        binding.tvUser.setText(sharedPrefManager.getSPIdUser());
//        apiServices = UtilApi.getApiService();

//        Call<List<Recipes>> recipes = apiServices.listRecipes();
//        recipes.enqueue(new Callback<List<Recipes>>() {
//            @Override
//            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
//                if (response.body() != null) {
//                    generateDataList(response.body());
//                } else {
//                    binding.clMain.setVisibility(View.VISIBLE);
//                    binding.tvStatus.setText("Sepertinya masih belum ada resep. Coba lagi nanti.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Recipes>> call, Throwable t) {
//                binding.clMain.setVisibility(View.VISIBLE);
//                binding.tvStatus.setText("Cek koneksi internet Anda");
//            }
//        });
    }

    public void handleLogout(View view) {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGIN, false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

//    private void generateDataList(List<Recipes> recipesList) {
//        adapter = new RecipesAdapter(recipesList);
//        binding.rvRecipes.setAdapter(adapter);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        binding.rvRecipes.setLayoutManager(layoutManager);
//    }

}