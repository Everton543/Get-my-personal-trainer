package com.example.getmypersonaltrainer;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ExerciseListViewAdapter extends FirebaseRecyclerAdapter<
      Exercise, ExerciseListViewAdapter.ExerciseListViewHolder> {

   boolean changingClient = false;
   /**
    * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
    * {@link FirebaseRecyclerOptions} for configuration options.
    *
    * @param options This is the FirebaseRecyclerOptions
    */
   public ExerciseListViewAdapter(@NonNull FirebaseRecyclerOptions<Exercise> options, boolean changingClient) {
      super(options);
      this.changingClient = changingClient;
   }

   public static class ExerciseListViewHolder extends RecyclerView.ViewHolder {
      Button exercise;
      public ExerciseListViewHolder(@NonNull View itemView) {
         super(itemView);
         exercise = itemView.findViewById(R.id.button_exercise_list);
      }
   }


   @NonNull
   @Override
   public ExerciseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      View view = inflater.inflate(R.layout.exercise_list, parent, false);
      return new ExerciseListViewHolder(view);
   }

   @Override
   protected void onBindViewHolder(@NonNull ExerciseListViewHolder holder, int position, @NonNull final Exercise model) {
      String name = model.getName();
      holder.exercise.setText(name);
      holder.exercise.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            MainActivity.presenter.setSelectedExercise(model);
            Intent intent;
            if(changingClient) {
               intent = new Intent(MainActivity.presenter.getActualActivity(), CreateExerciseActivity.class);
            }else{
               intent = new Intent(MainActivity.presenter.getActualActivity(), ExerciseInfoActivity.class);
            }
            MainActivity.presenter.getActualActivity().startActivity(intent);
         }
      });
   }
}
