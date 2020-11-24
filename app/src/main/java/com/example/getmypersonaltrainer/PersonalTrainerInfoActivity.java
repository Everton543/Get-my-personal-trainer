package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
            TextView score = findViewById(R.id.text_score_personal_trainer_info_activity);
            score.setText(personalTrainer.getAverageScore());
        }

    }
}