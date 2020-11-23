package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class PersonalTrainerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trainer_info);
        MainActivity.presenter.setActualActivity(this);

        PersonalTrainer personalTrainer = MainActivity.presenter.getMyPersonalTrainer();
        String name = personalTrainer.getName();
        Log.i("PersonalInfo", "Personal trainer name = " + name);
    }
}