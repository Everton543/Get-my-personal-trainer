package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

public class PersonalTrainerMainActivity extends AppCompatActivity {
    private static final String TAG = "TrainerMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trainer_main);

        Log.i(TAG, "Started Personal Trainer main Activity");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart called");
        PersonalTrainer personalTrainer = (PersonalTrainer) MainActivity.presenter.getUser();

        if(personalTrainer.getExerciseNameList() != null) {
            String[] exerciseList = personalTrainer.getExerciseNameList().toArray(new String[0]);
        }

    }
}