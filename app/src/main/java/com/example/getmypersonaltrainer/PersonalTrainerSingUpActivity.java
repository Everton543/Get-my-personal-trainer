package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
public class PersonalTrainerSingUpActivity extends AppCompatActivity{
   private User personalTrainer = null;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_personal_trainer_sing_up);
      MainActivity.presenter.setActualActivity(this);

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      ActionBar actionBar = getSupportActionBar();
      assert actionBar != null;
      actionBar.setTitle(R.string.personal_trainer_sign_up_title);
   }


   private int setPersonalTrainerInfo() {
      EditText editText = (EditText) findViewById(R.id.edit_text_id_personal_trainer_sign_up_activity);
      String id = editText.getText().toString();

      boolean emptyText = !MainActivity.presenter.getModel().getValidateInfo().checkId(id);
      if(emptyText){
         return MainActivity.invalidID;
      }


      editText = (EditText) findViewById(R.id.edit_text_password_personal_trainer_sign_up_activity);
      String password = editText.getText().toString();

      editText = (EditText) findViewById(R.id.edit_text_confirm_password_personal_trainer_sign_up_activity);
      String confirmPassword = editText.getText().toString();

      editText = (EditText) findViewById(R.id.edit_text_name_personal_trainer_sign_up_activity);
      String name = editText.getText().toString();
      emptyText = MainActivity.presenter.getModel().getValidateInfo().isEmptyString(name);
      if(emptyText){
         return MainActivity.emptyInfo;
      }

      editText = (EditText) findViewById(R.id.edit_text_about_myself_personal_trainer_sign_up_activity);
      String aboutMyselfText = editText.getText().toString();
      emptyText = MainActivity.presenter.getModel().getValidateInfo().isEmptyString(aboutMyselfText);
      if(emptyText){
         return MainActivity.emptyInfo;
      }

      boolean passwordsNotEqual = !MainActivity.presenter
              .getModel()
              .getValidateInfo()
              .checkIfPasswordAreEqual(password, confirmPassword);
      if(passwordsNotEqual){
         return MainActivity.passwordNotEqual;
      }

      boolean validPassword = MainActivity.presenter
              .getModel()
              .getValidateInfo()
              .password(password);
      if(validPassword) {
         personalTrainer= new User(UserTypes.PERSONAL_TRAINER, password, name, id, aboutMyselfText);
         return MainActivity.allGood;
      }

      return MainActivity.invalidPassword;
   }

   public void signUp(View view){
      int situationCase = setPersonalTrainerInfo();
      switch (situationCase) {
         case MainActivity.allGood: {
            MainActivity.presenter.setGetInfoFromDatabase(true);
            MainActivity.presenter.getModel().saveUser(personalTrainer);
            MainActivity.presenter.setGoingTo(MainActivity.class);
            MainActivity.presenter.setGoBack(PersonalTrainerSingUpActivity.class);
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
            break;
         }

         case MainActivity.invalidPassword: {
            MainActivity.presenter.getModel().getWarnings().invalidPassword();
            break;
         }

         case MainActivity.passwordNotEqual: {
            MainActivity.presenter.getModel().getWarnings().passwordNotEqualError();
            break;
         }

         case MainActivity.emptyInfo: {
            MainActivity.presenter.getModel().getWarnings().emptyInfo();
            break;
         }

         case MainActivity.invalidID: {
            MainActivity.presenter.getModel().getWarnings().invalidId();
            break;
         }
      }
   }
}
