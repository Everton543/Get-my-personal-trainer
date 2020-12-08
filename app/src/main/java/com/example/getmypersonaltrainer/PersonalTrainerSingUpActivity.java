package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

public class PersonalTrainerSingUpActivity extends AppCompatActivity{

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_personal_trainer_sing_up);
      MainActivity.presenter.setActualActivity(this);

      ActionBar actionBar = getSupportActionBar();
      assert actionBar != null;
      actionBar.setTitle(R.string.personal_trainer_sign_up_title);
   }

   public void signUp(View view){
      boolean result = false;

      EditText editText = (EditText) findViewById(R.id.edit_text_id_personal_trainer_sign_up_activity);
      String id = editText.getText().toString();

      editText = (EditText) findViewById(R.id.edit_text_password_personal_trainer_sign_up_activity);
      String password = editText.getText().toString();

      editText = (EditText) findViewById(R.id.edit_text_confirm_password_personal_trainer_sign_up_activity);
      String confirmPassword = editText.getText().toString();

      editText = (EditText) findViewById(R.id.edit_text_name_personal_trainer_sign_up_activity);
      String name = editText.getText().toString();

      editText = (EditText) findViewById(R.id.edit_text_about_myself_personal_trainer_sign_up_activity);
      String aboutMyselfText = editText.getText().toString();

      boolean passwordsAreEqual = MainActivity.presenter
            .getModel()
            .getValidateInfo()
            .checkIfPasswordAreEqual(password, confirmPassword);

      if(passwordsAreEqual == true){
         MainActivity.presenter.setGetInfoFromDatabase(true);
         User personalTrainer = new User(UserTypes.PERSONAL_TRAINER, password, name, id, aboutMyselfText);
         MainActivity.presenter.getModel().saveUser(personalTrainer);
         MainActivity.presenter.setGoingTo(MainActivity.class);
         MainActivity.presenter.setGoBack(PersonalTrainerSingUpActivity.class);
         Intent intent = new Intent(this, LoadingActivity.class);
         startActivity(intent);

      }else{
         MainActivity.presenter.getModel().getWarnings().passwordNotEqualError();
      }

   }
}