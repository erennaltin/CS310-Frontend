package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProgramExerciseAdaptor extends RecyclerView.Adapter<ProgramExerciseAdaptor.ListHolder>{
    Context ctx;
    List<ProgramExercise> data;

    public ProgramExerciseAdaptor(Context ctx, List<ProgramExercise> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.program_exercise_row, parent, false);

        ListHolder holder = new ListHolder(root);
        holder.itemView.setOnClickListener(v -> {this.onClick(root);});
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.exerciseName.setText(data.get(position).name);
        holder.repAmount.setText("Repeat per set: " + Integer.toString(data.get(position).repAmount));
        holder.setAmount.setText("Set Amount: " + Integer.toString(data.get(position).setAmount));

        Picasso.get()
                .load(data.get(position).photoURL)
                .into(holder.exerciseImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void onClick(View view) {
        // Handle item click here
        int position = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
        ProgramExercise selectedItem = data.get(position);
        Context context = view.getContext();
        Intent intent = new Intent(context, ExerciseDetail.class);
        intent.putExtra("exerciseId", selectedItem.exerciseId);
        context.startActivity(intent);
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView repAmount;
        TextView setAmount;
        ImageView exerciseImage;

        public ListHolder(@NonNull View itemView){
            super(itemView);
            Log.i("DEV", "test");
            exerciseName = itemView.findViewById(R.id.exerciseName);
            repAmount = itemView.findViewById(R.id.repAmount);
            setAmount = itemView.findViewById(R.id.setAmount);
            exerciseImage = itemView.findViewById(R.id.exerciseImage);
        }

    }
}
