package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PersonalTrainerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trainer_main);

        PersonalTrainer personalTrainer = (PersonalTrainer) MainActivity.presenter.getUser();
        String[] exerciseList = personalTrainer.getExerciseNameList();
    }
}