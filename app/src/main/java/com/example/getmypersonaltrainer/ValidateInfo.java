package com.example.getmypersonaltrainer;

import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Everton Alves
 */
public class ValidateInfo {

   private final static String TAG = "Validate Info";

   public boolean checkIfPasswordAreEqual(String databasePassword, String password){
      Log.i(TAG, "Checking if password are equal");
      return databasePassword.equals(password);
   }

   public boolean checkIfPersonalTrainerHasGivenExercise(PersonalTrainer personalTrainer, Exercise exercise){
      if(personalTrainer.getExerciseList() == null){
         return false;
      }

      return personalTrainer.getExerciseList().containsKey(exercise.getName());
   }


   public boolean checkId(String id){
      if(id == null){
         return false;
      }

      if (id.length() < 1) {
         return  false;
      }

      Pattern pattern = Pattern.compile("[\\\\.\\]\\[<>\"@#$%&*!';:,()/]");
      Matcher matcher = pattern.matcher(id);
      boolean passwordHasBadSymbols = matcher.find();

      if(passwordHasBadSymbols){
         return false;
      }

      return true;
   }

   public boolean validScore(int score){
      if(score < 0 || score > 10){
         MainActivity.presenter.getModel().getWarnings().invalidScoreRange();
         return false;
      }

      return true;
   }

   public boolean exerciseId(String id){
      if(id == null){
         return false;
      }

      if(id.length() < 1){
         return false;
      }

      Pattern pattern = Pattern.compile("[A-Z|a-z]", Pattern.UNICODE_CASE);
      Matcher matcher = pattern.matcher(id);
      boolean idHasLetter = matcher.find();

      pattern = Pattern.compile("[\\\\.\\]\\[<>\"'()/]");
      matcher = pattern.matcher(id);
      boolean idDoNotHasBadSymbols = !matcher.find();

      return idDoNotHasBadSymbols && idHasLetter;
   }


   public boolean password(String password) {
      if(password.length() < 8){
         return false;
      }

      Pattern pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(password);
      boolean passwordHasNumber = matcher.find();

      pattern = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
      matcher = pattern.matcher(password);
      boolean passwordHasLowercaseLetter = matcher.find();

      pattern = Pattern.compile("[A-Z]", Pattern.UNICODE_CASE);
      matcher = pattern.matcher(password);
      boolean passwordHasUppercaseLetter = matcher.find();

      pattern = Pattern.compile("[\\\\.\\]\\[<\\s>\"';:,()/]");
      matcher = pattern.matcher(password);
      boolean passwordHasBadSymbols = matcher.find();

      return ((passwordHasNumber == true)
            && (passwordHasLowercaseLetter == true)
            && (passwordHasUppercaseLetter == true )
            && (passwordHasBadSymbols == false));
   }


}
