package com.example.maskoki.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskoki.activity.KomentarEditActivity;
import com.example.maskoki.databinding.CommentsListBinding;
import com.example.maskoki.models.Comment;
import com.example.maskoki.util.SharedPrefManager;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>{

    List<Comment> resultComments;
    Comment comment;

    public CommentsAdapter(List<Comment> resultComments) {
        this.resultComments = resultComments;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CommentsListBinding binding = CommentsListBinding.inflate(layoutInflater, parent, false);
        return new CommentsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comment comment = resultComments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return resultComments.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CommentsListBinding binding;

        public CommentsViewHolder(CommentsListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.tvEdit.setOnClickListener(this);
        }

        public void bind(Comment comment){
            binding.setComments(comment);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            comment = new Comment();
            comment.setId(resultComments.get(position).getId());
            comment.setRecipeId(resultComments.get(position).getRecipeId());
            comment.setComment(resultComments.get(position).getComment());

            Intent intent = new Intent(view.getContext(), KomentarEditActivity.class);
            intent.putExtra("KEY_COMMENT", comment);
            view.getContext().startActivity(intent);
        }
    }
}
