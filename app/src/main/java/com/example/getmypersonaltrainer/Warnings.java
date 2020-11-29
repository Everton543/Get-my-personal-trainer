package com.example.getmypersonaltrainer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class Warnings {
   private final static String TAG = "Warning";

   private void sendWarning(int duration, CharSequence text){
      Context context = MainActivity.presenter.getActualActivity().getApplicationContext();
      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
   }



   public void passwordNotEqualError(){
      Log.e(TAG, "Password not equal");
      CharSequence text = "Password not equal";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void alreadyVoted(){
      CharSequence text = "You already evaluated this personal trainer";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void invalidDayOfWeek(){
      CharSequence text = "Invalid day of week";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void strangeError(){
      CharSequence text = "A ERROR happened please try again latter.";
      sendWarning(Toast.LENGTH_SHORT, text);
   }


   public void invalidScoreRange(){
      CharSequence text = "Score must be bellow 11 and greater then or equal to 0.";
      sendWarning(Toast.LENGTH_SHORT, text);
   }


   public void signUpSuccessfully(){
      CharSequence text = "Sign Up Successfully";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void createdNewExerciseSuccessfully(){
      CharSequence text = "Created new Exercise Successfully";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void errorUserIdAlreadyExists(){
      CharSequence text = "User Id Already exists";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void errorClientDoesNotExists(){
      CharSequence text = "Client does not exists";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void errorClientHasAlreadyReceivedAnInvitation(){
      CharSequence text = "Client has already received an invitation";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void invitationGotSend(){
      CharSequence text = "Client received yours invitation";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void errorExerciseAlreadyExists(){
      CharSequence text = "Exercise Already exists";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void errorClientWithOutPersonalTrainer(){
      CharSequence text = "You Do not have a personal trainer";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void invalidPassword(){
      Log.e(TAG, "Invalid password");
      CharSequence text = "Invalid password, Must have 8 digits, 1 capital letter, 1 lower case letter, 1 number";
      sendWarning(Toast.LENGTH_LONG, text);
   }

   public void invalidExerciseName(){
      CharSequence text = "Invalid exercise name";
      sendWarning(Toast.LENGTH_LONG, text);
   }

   public void noPersonalTrainerAvailable(){
      CharSequence text = "We don't have personal trainers available";
      sendWarning(Toast.LENGTH_LONG, text);
   }


   public void wrongPasswordOrUserId(){
      CharSequence text = "Wrong Password or User Id";
      sendWarning(Toast.LENGTH_SHORT, text);
   }
}
