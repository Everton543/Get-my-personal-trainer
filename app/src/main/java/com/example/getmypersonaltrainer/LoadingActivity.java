package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity{

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_loading);
      MainActivity.presenter.setActualActivity(this);
   }

   public void finishedCharge(){
      Intent intent = new Intent(this, MainActivity.presenter.getGoingTo());
      startActivity(intent);
   }

   public void loadingError(){
      Intent intent = new Intent(this, MainActivity.presenter.getGoBack());
      startActivity(intent);
   }

}