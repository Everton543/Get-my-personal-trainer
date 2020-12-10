package com.example.getmypersonaltrainer;

import android.util.Log;

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

   public boolean checkIfPersonalTrainerExerciseGotChanged(Exercise personalExercise, Exercise exercise){
      if(personalExercise == null || exercise == null){
         return false;
      }

      return !personalExercise.getEmphasis().equals(exercise.getEmphasis())
            || personalExercise.getSeries() != exercise.getSeries()
            || !personalExercise.getVideoLink().equals(exercise.getVideoLink());
   }


   /**
    *
    * @param client Client class
    * @return boolean
    * If client has personal trainer it returns true,
    * it returns false otherwise.
    */
   public boolean checkIfClientHasPersonalTrainer(Client client){
      if (client == null){
         return false;
      }

      return client.getPersonalTrainerId() != null;
   }

   public boolean checkIfPersonalTrainerHasGivenExercise(PersonalTrainer personalTrainer, Exercise exercise){
      if(exercise == null){
         return false;
      }

      if(personalTrainer.getExerciseList() == null || exercise.getName() == null){
         return false;
      }

      return personalTrainer.getExerciseList().containsKey(exercise.getName());
   }

   public boolean isEmptyString(String value){
      if(value == null){
         return true;
      }

      if (value.length() < 1) {
         return true;
      }

      return false;
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
      boolean passwordHasBadSymbols = !matcher.find();

      pattern = Pattern.compile("[A-z]", Pattern.CASE_INSENSITIVE);
      matcher = pattern.matcher(id);
      boolean hasLetter = matcher.find();

      return passwordHasBadSymbols && hasLetter;
   }

   public boolean validScore(int score){
      return score >= 0 && score <= 10;
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

      pattern = Pattern.compile("[a-z]", Pattern.UNICODE_CASE);
      matcher = pattern.matcher(password);
      boolean passwordHasLowercaseLetter = matcher.find();

      pattern = Pattern.compile("[A-Z]", Pattern.UNICODE_CASE);
      matcher = pattern.matcher(password);
      boolean passwordHasUppercaseLetter = matcher.find();

      pattern = Pattern.compile("[\\\\.\\]\\[<\\s>\"';:,()/]");
      matcher = pattern.matcher(password);
      boolean passwordDoNotHasBadSymbols = !matcher.find();

      return ((passwordHasNumber)
            && (passwordHasLowercaseLetter)
            && (passwordHasUppercaseLetter)
            && (passwordDoNotHasBadSymbols));
   }


}
