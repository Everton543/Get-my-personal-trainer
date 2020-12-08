package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class ChooseSignUpType extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sign_up_type);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

    }

    public void personalTrainerSignUp(View view){
        Intent intent = new Intent(this, PersonalTrainerSingUpActivity.class);
        startActivity(intent);
    }

    public void clientSignUp(View view){
        Intent intent = new Intent(this, ClientSignUpActivity.class);
        startActivity(intent);
    }
}