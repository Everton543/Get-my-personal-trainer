   package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.math.RoundingMode;
import java.text.DecimalFormat;

   public class MainActivity extends AppCompatActivity implements LoginInterface, FastError{
      final static String PREFERENCES = "SharedPreference";
      final static String USER_ID = "userId";
      final static String NOT_FOUND = "notFound";
      static final int allGood = 0;
      static final int emptyInfo = 1;
      static final int passwordNotEqual = 2;
      static final int invalidPassword = 3;
      static final int invalidID = 4;
      private static final String TAG = "MyActivity";
      static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

      private String logId = null;

      public final static Presenter presenter = new Presenter();

      @Override
      protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         ActionBar actionBar = getSupportActionBar();
         assert actionBar != null;
         actionBar.hide();

         presenter.resetValues();
         presenter.setActualActivity(this);

         decimalFormat.setRoundingMode(RoundingMode.UP);
         SharedPreferences sharedPreferences;
         sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

         logId = sharedPreferences.getString(USER_ID, NOT_FOUND);
         if(logId.equals(NOT_FOUND)){
            //If not found Put the text as blank
            eraseText(R.id.edit_text_user_id_login_activity);
         } else{
            EditText editText = (EditText) findViewById(R.id.edit_text_user_id_login_activity);
            editText.setText(logId);
         }
      }

      @Override
      protected void onResume() {
         super.onResume();
         presenter.setLogged(false);
      }

      public void login(View view){
         Log.i(TAG, "login function called");
         EditText editText = (EditText) findViewById(R.id.edit_text_user_id_login_activity);
         logId = editText.getText().toString();

         editText = (EditText) findViewById(R.id.edit_text_password_login_activity);
         String password = editText.getText().toString();

         presenter.getModel().checkLogin(logId, password, this);
         Log.i(TAG, "login function finished");
      }

      private void saveLoginId(){
         SharedPreferences sharedPreferences;
         sharedPreferences = getSharedPreferences(PREFERENCES,
               Context.MODE_PRIVATE);
         SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
         preferencesEditor.putString(USER_ID, logId);
         preferencesEditor.apply();
      }

      public void singUp(View view){
         Intent intent = new Intent(this, ChooseSignUpType.class);
         startActivity(intent);
      }

      @Override
      public void loginUserType(UserTypes userType, boolean goodLoginResult) {
         if(goodLoginResult){
            switch (userType){
               case PERSONAL_TRAINER:{
                  presenter.setUserAsPersonalTrainer();
                  saveLoginId();
                  presenter.setGoingTo(PersonalTrainerMainActivity.class);
                  presenter.setGoBack(PersonalTrainerMainActivity.class);
                  presenter.getModel().getPersonalTrainerInfo();
                  Intent intent = new Intent(this, LoadingActivity.class);
                  startActivity(intent);
                  break;
               }

               case CLIENT:{
                  presenter.setLogged(true);
                  presenter.setUserAsClient();
                  saveLoginId();
                  Intent intent = new Intent(this, ClientMainActivity.class);
                  startActivity(intent);
                  break;
               }
            }
         }
      }

      @Override
      public void setPresenterUser(User user) {
         presenter.setUser(user);
      }

      public void eraseText(int textId){
         EditText editText = (EditText) findViewById(textId);
         editText.setText(R.string.blank);
      }

      @Override
      protected void onStop() {
         super.onStop();
         eraseText(R.id.edit_text_password_login_activity);

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
