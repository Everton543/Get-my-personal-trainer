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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientExerciseListActivity extends AppCompatActivity {
   @RequiresApi(api = Build.VERSION_CODES.O)
   private Client client = null;
   private List<ExerciseListViewAdapter> exerciseListViewAdapters = new ArrayList<ExerciseListViewAdapter>();
   private List<RecyclerView> exerciseListRecyclerView = new ArrayList<RecyclerView>();
   @RequiresApi(api = Build.VERSION_CODES.O)
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_client_exercise_list);
      MainActivity.presenter.setActualActivity(this);

      client = MainActivity.presenter.getChangingClient();
      if(exerciseListRecyclerView.size() < 1) {
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

         exerciseListRecyclerView.add(mondayView);
         exerciseListRecyclerView.add(tuesdayView);
         exerciseListRecyclerView.add(wednesdayView);
         exerciseListRecyclerView.add(thursdayView);
         exerciseListRecyclerView.add(fridayView);
         exerciseListRecyclerView.add(saturdayView);

      }
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   private void setRecyclerViewAdapter(RecyclerView recyclerView, DayOfWeek dayOfWeek){
      ExerciseListViewAdapter exerciseListViewAdapter =
            new ExerciseListViewAdapter(
                    MainActivity.presenter.getModel().getClientExerciseList(client, dayOfWeek),
                  true
            );

      recyclerView.setAdapter(exerciseListViewAdapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      exerciseListViewAdapters.add(exerciseListViewAdapter);
   }

   @Override
   protected void onStart() {
      super.onStart();
      for(int i = 0 ; i < exerciseListViewAdapters.size(); i++) {
         exerciseListViewAdapters.get(i).startListening();
      }
   }

   @Override
   protected void onStop() {
      super.onStop();
      for(int i = 0 ; i < exerciseListViewAdapters.size(); i++) {
         exerciseListViewAdapters.get(i).stopListening();
      }
   }

}