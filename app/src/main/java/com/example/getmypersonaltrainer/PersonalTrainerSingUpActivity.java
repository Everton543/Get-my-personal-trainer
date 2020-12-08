package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
public class PersonalTrainerSingUpActivity extends AppCompatActivity{

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

   public void signUp(View view){
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

      boolean validPassword = MainActivity.presenter
              .getModel()
              .getValidateInfo()
              .password(password);

      if(name == null || name.equals("") ||
         confirmPassword == null || confirmPassword.equals("") ||
         password == null || password.equals("") ||
         id == null || id.equals("")){

         MainActivity.presenter.getModel().getWarnings().blankInformation();
      } else {

         if (passwordsAreEqual && validPassword) {
            MainActivity.presenter.setGetInfoFromDatabase(true);
            User personalTrainer = new User(UserTypes.PERSONAL_TRAINER, password, name, id, aboutMyselfText);
            MainActivity.presenter.getModel().saveUser(personalTrainer);
            MainActivity.presenter.setGoingTo(MainActivity.class);
            MainActivity.presenter.setGoBack(PersonalTrainerSingUpActivity.class);
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
         } else if (validPassword == false) {
            MainActivity.presenter.getModel().getWarnings().invalidPassword();
         } else {
            MainActivity.presenter.getModel().getWarnings().passwordNotEqualError();
         }
      }
   }
}