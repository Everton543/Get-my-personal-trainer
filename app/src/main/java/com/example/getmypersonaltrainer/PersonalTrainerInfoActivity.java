package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PersonalTrainerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trainer_info);
        PersonalTrainer personalTrainer = MainActivity.presenter.getMyPersonalTrainer();
        String name = personalTrainer.getName();
    }
}