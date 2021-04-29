package com.example.maskoki.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskoki.R;
import com.example.maskoki.activity.DetailResepActivity;
import com.example.maskoki.activity.LoginActivity;
import com.example.maskoki.activity.MainActivity;
import com.example.maskoki.databinding.RecipesListBinding;
import com.example.maskoki.models.Recipes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    List<Recipes> resultRecipes;
    Recipes recipe;

    public RecipesAdapter(List<Recipes> resultRecipes) {
        this.resultRecipes = resultRecipes;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipesListBinding binding = RecipesListBinding.inflate(layoutInflater, parent, false);
        return new RecipesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
//        Recipes recipes = resultRecipes.get(position);
//        holder.bind(recipes);
//
//        Picasso.get()
//                .load(Uri.parse(resultRecipes.get(position).getImage()))
//                .into(holder.binding.ivResep);
    }

    @Override
    public int getItemCount() {
        return resultRecipes.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecipesListBinding binding;

        public RecipesViewHolder(RecipesListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.clResep.setOnClickListener(this);
        }

        public void bind(Recipes recipes){
//            binding.setRecipes(recipes);
//            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
//            int position = getLayoutPosition();
//            recipe = new Recipes();
//            recipe.setId(resultRecipes.get(position).getId());
//            recipe.setTitle(resultRecipes.get(position).getTitle());
//            recipe.setDescription(resultRecipes.get(position).getDescription());
//            recipe.setIngredients(resultRecipes.get(position).getIngredients());
//            recipe.setInstructions(resultRecipes.get(position).getInstructions());
//            recipe.setImage(resultRecipes.get(position).getImage());
//
//            Intent intent = new Intent(view.getContext(), DetailResepActivity.class);
//            intent.putExtra("KEY_RECIPES", recipe);
//            view.getContext().startActivity(intent);
        }
    }
}
