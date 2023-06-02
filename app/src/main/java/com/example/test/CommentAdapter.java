package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {


    Context ctx;
    List<Comment> data;

    public CommentAdapter(Context ctx, List<Comment> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(ctx).inflate(R.layout.comment_row,parent,false);
        CommentHolder holder = new CommentHolder(root);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {

        holder.commentDate.setText(data.get(position).dateCreated);
        holder.commentUsername.setText(data.get(position).username);
        holder.commentComment.setText(data.get(position).text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        TextView commentUsername;
        TextView commentComment;
        TextView commentDate;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            commentUsername = itemView.findViewById(R.id.commentUsername);
            commentComment = itemView.findViewById(R.id.commentComment);
            commentDate = itemView.findViewById(R.id.commentDate);
        }
    }

}
