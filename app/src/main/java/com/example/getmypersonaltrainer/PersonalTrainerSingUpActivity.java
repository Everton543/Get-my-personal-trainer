package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

public class PersonalTrainerSingUpActivity extends AppCompatActivity implements SignUpInterface{

   //private Presenter presenter = null;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_personal_trainer_sing_up);

     // String jsonPresenter = getIntent().getStringExtra(MainActivity.PRESENTER);
      //presenter = new Gson().fromJson(jsonPresenter, Presenter.class);
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

      if(MainActivity.presenter.getModel().checkIfPasswordAreEqual(password, confirmPassword)){
         PersonalTrainer personalTrainer = new PersonalTrainer(UserTypes.PERSONAL_TRAINER, password, name, id, aboutMyselfText);
    //     result = MainActivity.presenter.getModel().addNewPersonalTrainer(personalTrainer, this);
         MainActivity.presenter.getModel().saveUser(personalTrainer, this);

      }else{
         MainActivity.presenter.getModel().passwordNotEqualError(this);
      }

    //  if(result == true){
//         signUpSuccessfully();
  //    }
   }

   public void signUpSuccessfully(){
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
   }
}