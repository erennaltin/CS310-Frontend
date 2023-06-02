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

public class ForumTopicsAdapter extends RecyclerView.Adapter<ForumTopicsAdapter.ForumTopicHolder> {


    Context ctx;
    List<ForumTopics> data;

    public ForumTopicsAdapter(Context ctx, List<ForumTopics> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ForumTopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(ctx).inflate(R.layout.forum_topic_row,parent,false);
        ForumTopicHolder holder = new ForumTopicHolder(root);

        holder.itemView.setOnClickListener(v -> {this.onClick(root);});

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ForumTopicHolder holder, int position) {

        holder.topicName.setText(data.get(position).name);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ForumTopicHolder extends RecyclerView.ViewHolder {
        TextView topicName;
        public ForumTopicHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.txtTopicName);
        }
    }

    public void onClick(View root) {
        int position = ((RecyclerView) root.getParent()).getChildAdapterPosition(root);
        ForumTopics selectedItem = data.get(position);
        Context context = root.getContext();
        Intent intent = new Intent(context, ForumTopicDetail.class);
        intent.putExtra("topicName", selectedItem.name);
        context.startActivity(intent);


    }


}
