package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AllPersonalTrainerListResultActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_all_personal_trainer_list_result);

      ActionBar actionBar = getSupportActionBar();
      assert actionBar != null;
      actionBar.setTitle(R.string.personal_trainer_list_title);

      if(MainActivity.presenter.getAllPersonalTrainers() == null) {
         MainActivity.presenter.setGetInfoFromDatabase(true);
         MainActivity.presenter.getModel().getAllPersonalTrainers();
      }
   }
}