package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AllPersonalTrainerListResultActivity extends AppCompatActivity {

   private PersonalTrainerListViewAdapter personalTrainerListViewAdapter = null;
   private RecyclerView personalTrainerRecyclerView= null;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_all_personal_trainer_list_result);

      ActionBar actionBar = getSupportActionBar();
      assert actionBar != null;
      actionBar.setTitle(R.string.personal_trainer_list_title);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      personalTrainerListViewAdapter = new PersonalTrainerListViewAdapter(MainActivity.presenter.getModel().getAllPersonalTrainers());


      //todo create the PersonalTrainerListViewAdapter
   }
}