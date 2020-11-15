   package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.Serializable;

   public class MainActivity extends AppCompatActivity implements LoginInterface{
      final static String PREFERENCES = "SharedPreference";
      final static String USER_ID = "userId";
      final static String NOT_FOUND = "notFound";
      final static String PRESENTER = "Presenter";

      public final static Presenter presenter = new Presenter();
      @Override
      protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

/*      SharedPreferences sharedPreferences;
      sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

      String userId = sharedPreferences.getString(USER_ID, NOT_FOUND);
      if(userId.equals(NOT_FOUND)){
         //To-do: leave the user login id blank
      } else{
         //To-do: Put the userId in the login information
      }*/
   }

      public void login(View view){
      EditText editText = (EditText) findViewById(R.id.edit_text_user_id_login_activity);
      String id = editText.getText().toString();

      editText = (EditText) findViewById(R.id.edit_text_password_login_activity);
      String password = editText.getText().toString();


      presenter.getModel().checkLogin(id, password, this);
   }

      public void saveLoginId(String loginId){
         SharedPreferences sharedPreferences;
         sharedPreferences = getSharedPreferences(PREFERENCES,
            Context.MODE_PRIVATE);
         SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
         preferencesEditor.putString(USER_ID, loginId);
         preferencesEditor.apply();
      }

      public void singUp(View view){
         Intent intent = new Intent(this, ChooseSignUpType.class);
         startActivity(intent);
      }

      @Override
      public void loginUserType(UserTypes userType, boolean goodLoginResult) {
         if(goodLoginResult){
            System.out.println("Valid login UHUUUUUUU");
            System.out.println("THE USER TYPE IS " + userType);
         } else{
            System.out.println("Wrong id or password AGAIN");
         }

         Context context = getApplicationContext();
         CharSequence text = "USER TYPE: " + userType;
         int duration = Toast.LENGTH_LONG;

         Toast toast = Toast.makeText(context, text, duration);
         toast.show();
      }
   }
