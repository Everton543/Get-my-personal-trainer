   package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

   public class MainActivity extends AppCompatActivity {
   final static String PREFERENCES = "SharedPreference";
   final static String USER_ID = "userId";
   final static String NOT_FOUND = "notFound";
   private Model model = new Model();
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      //model = new Model();
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
      boolean valid = true;
      EditText editText = (EditText) findViewById(R.id.edit_text_user_id_login_activity);
      String id = editText.getText().toString();

      editText = (EditText) findViewById(R.id.edit_text_password_login_activity);
      String password = editText.getText().toString();


      boolean loginResult = model.checkLogin(id, password);
      if(loginResult == valid){
         System.out.println("Valid login");
      } else{
         System.out.println("Wrong id or password");
      }
   }

   public void saveLoginId(String loginId){
      SharedPreferences sharedPreferences;
      sharedPreferences = getSharedPreferences(PREFERENCES,
            Context.MODE_PRIVATE);
      SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
      preferencesEditor.putString(USER_ID, loginId);
      preferencesEditor.apply();
   }

   //test
   public void addNewClient(View view){
      EditText editText = (EditText) findViewById(R.id.edit_text_user_id_login_activity);
      String id = editText.getText().toString();

      editText = (EditText) findViewById(R.id.edit_text_password_login_activity);
      String password = editText.getText().toString();

      NewClient newClient = new NewClient(id, password);




      model.saveNewClient(newClient);
   }
}
