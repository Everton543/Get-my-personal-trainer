package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PersonalTrainerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trainer_info);
        MainActivity.presenter.setActualActivity(this);

        int invalidInput = 0;

        PersonalTrainer personalTrainer = MainActivity.presenter.getMyPersonalTrainer();

        if(personalTrainer.getName() != null) {
            TextView textName = findViewById(R.id.text_personal_trainer_name_personal_trainer_info);
            textName.setText(personalTrainer.getName());
        }

        if(personalTrainer.getAboutMyselfText() != null){
            TextView aboutMySelf = findViewById(R.id.text_about_self_personal_trainer_info_activity);
            aboutMySelf.setText(personalTrainer.getAboutMyselfText());
        }


        if(personalTrainer.getVoteQuantity() > invalidInput){
            TextView textViewScore = findViewById(R.id.text_score_personal_trainer_info_activity);
            String score = String.valueOf(personalTrainer.getAverageScore());
            textViewScore.setText(score);
        }
    }

    public void evaluateTrainer(View view){
        if(MainActivity.presenter.getUser() instanceof Client) {

            if(((Client) MainActivity.presenter.getUser()).getVoted() == false) {
                Intent intent = new Intent(this, EvaluatePersonalTrainerActivity.class);
                startActivity(intent);
            } else{
                MainActivity.presenter.getModel().getWarnings().alreadyVoted();
            }
        }
    }

}