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


   public void sendingInvitationToClientSinceTheyHaveAPersonalTrainer(){
      CharSequence text = "The client already has a personal trainer." +
              " He/she will receive one invitation asking if " +
              "he/she wants to change it.";
      sendWarning(Toast.LENGTH_LONG, text);
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

   public void emptyInfo(){
      CharSequence text = "Empty information";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void acceptedInvitation(){
      CharSequence text = "You accepted the invitation";
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

   public void invalidId(){
      CharSequence text = "Invalid ID value. Please write only letters and numbers";
      sendWarning(Toast.LENGTH_LONG, text);
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
      CharSequence text = "This ID Already exists";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void errorClientDoesNotExists(){
      CharSequence text = "This member does not exists";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void errorUserDoesNotExists(){
      CharSequence text = "This member does not exists";
      sendWarning(Toast.LENGTH_SHORT, text);
   }

   public void invitationGotSend(){
      CharSequence text = "The member received yours invitation";
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
