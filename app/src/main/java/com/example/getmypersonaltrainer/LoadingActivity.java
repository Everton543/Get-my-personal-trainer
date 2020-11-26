package com.example.getmypersonaltrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

public class LoadingActivity extends AppCompatActivity{

   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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