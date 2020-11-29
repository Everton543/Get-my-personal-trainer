package com.example.getmypersonaltrainer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseListViewAdapter extends RecyclerView.Adapter<
      ExerciseListViewAdapter.ExerciseListViewHolder> {

   private final Context context;
   private final List<Exercise> exerciseList;
   boolean changingClient = true;

   public class ExerciseListViewHolder extends RecyclerView.ViewHolder {
      Button exercise;
      public ExerciseListViewHolder(@NonNull View itemView) {
         super(itemView);
         exercise = itemView.findViewById(R.id.button_exercise_list);
      }
   }

   public ExerciseListViewAdapter(Context context, List<Exercise> exerciseList){
      this.exerciseList = exerciseList;
      this.context = context;
   }

   public ExerciseListViewAdapter(Context context, List<Exercise> exerciseList, boolean changingClient){
      this.exerciseList = exerciseList;
      this.context = context;
      this.changingClient = changingClient;
   }

   @NonNull
   @Override
   public ExerciseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View view = inflater.inflate(R.layout.exercise_list, parent, false);
      return new ExerciseListViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ExerciseListViewHolder holder, final int position) {
      String name = exerciseList.get(position).getName();
      holder.exercise.setText(name);
      if(this.changingClient == true) {
         holder.exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context, CreateExerciseActivity.class);
               String exerciseId = exerciseList.get(position).getExerciseId();
               intent.putExtra("exerciseId", exerciseId);
               context.startActivity(intent);
            }
         });
      }
      //otherwise show client's exercise list
      holder.exercise.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String exerciseId = exerciseList.get(position).getExerciseId();
            Intent intent = new Intent(context, ExerciseInfoActivity.class);
            intent.putExtra("exerciseId", exerciseId);
            context.startActivity(intent);
         }});
      }

   @Override
   public int getItemCount() {
      return exerciseList.size();
   }

}
