package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

public class ChooseSignUpType extends AppCompatActivity {
    //private String jsonPresenter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sign_up_type);

     //   jsonPresenter = getIntent().getStringExtra(MainActivity.PRESENTER);
    }

    public void personalTrainerSignUp(View view){
        Intent intent = new Intent(this, PersonalTrainerSingUpActivity.class);
      //  intent.putExtra(MainActivity.PRESENTER, jsonPresenter);
        startActivity(intent);
    }

    public void clientSignUp(View view){
        Intent intent = new Intent(this, ClientSignUpActivity.class);
        //intent.putExtra(MainActivity.PRESENTER, jsonPresenter);
        startActivity(intent);
    }
}