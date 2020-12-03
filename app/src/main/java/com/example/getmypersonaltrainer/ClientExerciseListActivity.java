package com.example.getmypersonaltrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.time.DayOfWeek;
import java.util.Objects;

public class ClientExerciseListActivity extends AppCompatActivity {
   int clientIndex;
   @RequiresApi(api = Build.VERSION_CODES.O)
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_client_exercise_list);

      if(getIntent().hasExtra("index")){
         if(MainActivity.presenter.getUser() instanceof PersonalTrainer) {
            clientIndex = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("index")));
            MainActivity.presenter.setChangingClient(((PersonalTrainer) MainActivity
                  .presenter
                  .getUser())
                  .getClients()
                  .get(clientIndex)
            );
         }
      }

      RecyclerView mondayView = findViewById(R.id.recycler_view_monday);
      RecyclerView tuesdayView = findViewById(R.id.recycler_view_tuesday);
      RecyclerView wednesdayView = findViewById(R.id.recycler_view_wednesday);
      RecyclerView thursdayView = findViewById(R.id.recycler_view_thursday);
      RecyclerView fridayView = findViewById(R.id.recycler_view_friday);
      RecyclerView saturdayView = findViewById(R.id.recycler_view_saturday);

      setRecyclerViewAdapter(mondayView, DayOfWeek.MONDAY);
      setRecyclerViewAdapter(tuesdayView, DayOfWeek.TUESDAY);
      setRecyclerViewAdapter(wednesdayView, DayOfWeek.WEDNESDAY);
      setRecyclerViewAdapter(thursdayView, DayOfWeek.THURSDAY);
      setRecyclerViewAdapter(fridayView, DayOfWeek.FRIDAY);
      setRecyclerViewAdapter(saturdayView, DayOfWeek.SATURDAY);
   }

   private void setRecyclerViewAdapter(RecyclerView recyclerView, DayOfWeek dayOfWeek){
      ExerciseListViewAdapter exerciseListViewAdapter =
            new ExerciseListViewAdapter(this,
                  ((PersonalTrainer) MainActivity.presenter.getUser())
                        .getClients()
                        .get(clientIndex)
                        .getExercisesOfWeekDay(dayOfWeek)
            );

      recyclerView.setAdapter(exerciseListViewAdapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
   }

}