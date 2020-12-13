package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AllPersonalTrainerListResultActivity extends AppCompatActivity {

   private PersonalTrainerListViewAdapter personalTrainerListViewAdapter = null;
   private RecyclerView personalTrainerRecyclerView = null;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_all_personal_trainer_list_result);
      MainActivity.presenter.setActualActivity(this);

      ActionBar actionBar = getSupportActionBar();
      assert actionBar != null;
      actionBar.setTitle(R.string.personal_trainer_list_title);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      personalTrainerRecyclerView = findViewById(R.id.recycler_view_personal_trainer_list);
      personalTrainerListViewAdapter = new PersonalTrainerListViewAdapter(
        MainActivity.presenter.getModel().getAllPersonalTrainers()
      );
      personalTrainerRecyclerView.setAdapter(personalTrainerListViewAdapter);
      personalTrainerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
   }
   
   @Override
   protected void onStart() {
      super.onStart();
      if(personalTrainerListViewAdapter != null) {
         personalTrainerListViewAdapter.startListening();
      }
   }

   @Override
   protected void onStop() {
      super.onStop();
      if(personalTrainerListViewAdapter != null) {
         personalTrainerListViewAdapter.stopListening();
      }
   }
}
