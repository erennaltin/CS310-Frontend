package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseRecordAdapter extends RecyclerView.Adapter<ExerciseRecordAdapter.ListHolder>{
    Context ctx;
    List<ExerciseRecord> data;

    public ExerciseRecordAdapter(Context ctx, List<ExerciseRecord> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.exercise_record_row, parent, false);

        ListHolder holder = new ListHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.exerciseRecordDate.setText(data.get(position).date);
        holder.exerciseRecordWeightAndRep.setText(data.get(position).weight + " kg - " + data.get(position).maxRep + " Repeat");


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView exerciseRecordDate;
        TextView exerciseRecordWeightAndRep;


        public ListHolder(@NonNull View itemView){
            super(itemView);
            exerciseRecordDate = itemView.findViewById(R.id.exerciseRecordDate);
            exerciseRecordWeightAndRep = itemView.findViewById(R.id.exerciseRecordWeightAndRep);
        }

    }
}
