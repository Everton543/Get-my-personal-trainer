package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Warnings {
   private final static String TAG = "Warning";

   private void sendWarning(int duration, CharSequence text, Activity activity){
      Context context = activity.getApplicationContext();
      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
   }

   public void passwordNotEqualError(Activity activity){
      Log.e(TAG, "Password not equal");
      CharSequence text = "Password not equal";
      sendWarning(Toast.LENGTH_SHORT, text, activity);
   }


   public void signUpSuccessfully(Activity activity){
      CharSequence text = "Sign Up Successfully";
      sendWarning(Toast.LENGTH_SHORT, text, activity);
   }

   public void createdNewExerciseSuccessfully(Activity activity){
      CharSequence text = "Created new Exercise Successfully";
      sendWarning(Toast.LENGTH_SHORT, text, activity);
   }

   public void errorUserIdAlreadyExists(Activity activity){
      CharSequence text = "User Id Already exists";
      sendWarning(Toast.LENGTH_SHORT, text, activity);
   }

   public void errorExerciseAlreadyExists(Activity activity){
      CharSequence text = "Exercise Already exists";
      sendWarning(Toast.LENGTH_SHORT, text, activity);
   }

   public void invalidPassword(Activity activity){
      Log.e(TAG, "Invalid password");
      CharSequence text = "Invalid password, Must have 8 digits, 1 capital letter, 1 lower case letter, 1 number";
      sendWarning(Toast.LENGTH_LONG, text, activity);
   }

   public void invalidExerciseName(Activity activity){
      CharSequence text = "Invalid exercise name";
      sendWarning(Toast.LENGTH_LONG, text, activity);
   }

   public void wrongPasswordOrUserId(Activity activity){
      CharSequence text = "Wrong Password or User Id";
      sendWarning(Toast.LENGTH_SHORT, text, activity);
   }
}
