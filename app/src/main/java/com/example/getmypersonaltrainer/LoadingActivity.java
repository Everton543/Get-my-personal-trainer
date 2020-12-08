package com.example.getmypersonaltrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity implements FastError{

   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_loading);
      MainActivity.presenter.setActualActivity(this);

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      ActionBar actionBar = getSupportActionBar();
      assert actionBar != null;
      actionBar.setTitle(R.string.login_activity_title);
   }

   @Override
   public void finishedCharge(){
      Intent intent = new Intent(this, MainActivity.presenter.getGoingTo());
      startActivity(intent);
   }

   @Override
   public void loadingError(){
      Intent intent = new Intent(this, MainActivity.presenter.getGoBack());
      startActivity(intent);
   }

}