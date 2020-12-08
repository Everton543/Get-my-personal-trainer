package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EvaluatePersonalTrainerActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_evaluate_personal_trainer);
      MainActivity.presenter.setActualActivity(this);


      int invalidInput = 0;

      PersonalTrainer personalTrainer = MainActivity.presenter.getMyPersonalTrainer();

      if(personalTrainer.getName() != null) {
         TextView textName = findViewById(R.id.text_personal_trainer_name_evaluate_personal_trainer_info);
         textName.setText(personalTrainer.getName());
      }

      if(personalTrainer.getVoteQuantity() > invalidInput){
         TextView textViewScore = findViewById(R.id.text_score_evaluate_personal_trainer_activity);
         String score = String.valueOf(personalTrainer.getAverageScore());
         textViewScore.setText(score);
      }
   }

   public void sendVote(View view){
      EditText editTextScore = findViewById(R.id.edit_text_score_vote_evaluate_personal_trainer);
      int score = Integer.parseInt(String.valueOf(editTextScore.getText()));

      boolean goodResult = MainActivity.presenter.getModel().saveVoteInfo(score);

      if(goodResult) {
         Intent intent = new Intent(this, ClientMainActivity.class);
         startActivity(intent);
      }
   }
}