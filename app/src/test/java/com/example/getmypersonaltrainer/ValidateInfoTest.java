package com.example.getmypersonaltrainer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateInfoTest {

   private ValidateInfo validateInfo = new ValidateInfo();

   @Test
   @DisplayName("Test if Personal Trainer Exercise got changed; Should FAIL if the personal trainer " +
         "exercise is empty, or the exercise to compare is empty, or if Exercises emphasis are equal, or if " +
         "exercise series are equal, or if exercise series are equal")

   @Test
   @DisplayName("Test if Client has an Personal Trainer; Should FAIL if the Client variable" +
         " personalTrainerId is null, or Client is null")
   void variableToCheckIfClientHasPersonalTrainer(){
      boolean[] expected = {
         false, false, true
      };

      Client empty = new Client();

      Client hasPersonal = new Client();
      hasPersonal.setPersonalTrainerId("Personal");

      Client[] clients = {
            null, empty, hasPersonal
      };

      for (int i = 0; i < expected.length; i++){
         testClientHasPersonalTrainer(clients[i], expected[i]);
      }

   }

   void testClientHasPersonalTrainer(Client client, boolean expected){
      boolean result = validateInfo.checkIfClientHasPersonalTrainer(client);
      assertEquals(result, expected);
   }

   @Test
   @DisplayName("Test if Personal Trainer has given Exercise; Should FAIL if personal trainer don't has the exercise" +
         "name in their exercise list, exercise name is null, or exercise is null")
   void variablesToCheckIfPersonalTrainerHasExercise(){

      boolean[] expected = {
            false, false, false,
            true, true, true
      };

      PersonalTrainer personalTrainer = new PersonalTrainer();
      personalTrainer.getExerciseList().put("chess", new Exercise());
      personalTrainer.getExerciseList().put("soccer", new Exercise());
      personalTrainer.getExerciseList().put("sleep", new Exercise());

      Exercise eat = new Exercise();
      eat.setName("eat");

      Exercise s = new Exercise();
      s.setName("s");

      Exercise chess = new Exercise();
      chess.setName("chess");

      Exercise sleep = new Exercise();
      sleep.setName("sleep");

      Exercise soccer = new Exercise();
      soccer.setName("soccer");

      Exercise[] exercises = {
            new Exercise(), eat, s,
            chess, sleep, soccer
      };

      for (int i = 0; i < expected.length; i++){
         testPersonalTrainerHasExercise(personalTrainer, exercises[i], expected[i]);
      }
   }

   private void testPersonalTrainerHasExercise(PersonalTrainer personalTrainer, Exercise exercise, boolean expected){
      boolean result = validateInfo.checkIfPersonalTrainerHasGivenExercise(personalTrainer, exercise);
      assertEquals(result, expected);
   }

   @Test
   @DisplayName("Test id; Should FAIL IF id equals null, id is empty, id don't has letters, id has any of these symbols <>\"'([].) @#$%&*!;:,/")
   void variableToCheckID(){
      boolean[] expected = {
            false, false, false,
            false, false, false,
            false, false, false,
            false, false, false,
            false, true, true,
            true, true, true,
            false, false, false,
            false, false, false,
            false, false, false,
            false, false, false
      };
      String[] ids = {
            "  ", "", "/",
            "1", "12", "a<",
            "b>", "f\\", "m(",
            "n)", "a.", "k[",
            "l]", "ar", "run2",
            "F9", "0D", "Run",
            "/k", "af,", ":gd",
            ";d", "!a", "*adf",
            "&f", "%fv", "$cv",
            "#df", "@vb", null
      };


      for (int i = 0; i < expected.length; i++){
         testIds(ids[i], expected[i]);
      }
   }

   private void testIds(String id, boolean expected) {
      boolean result = validateInfo.checkId(id);
      assertEquals(result, expected);
   }

   @Test
   @DisplayName("Test score; Should FAIL if score is larger than 10 or lass than 0")
   void variablesToCheckScore(){
      boolean[] expected = {
         false, false, true, true, true
      };
      int[] scores = {
            -1, 11, 0, 10, 5
      };

      for (int i = 0; i < expected.length; i++){
         testScore(scores[i], expected[i]);
      }
   }

   void testScore(int score, boolean expected){
      boolean result = validateInfo.validScore(score);
      assertEquals(result, expected);
   }

   @Test
   @DisplayName("Test Exercise ID; Should FAIL IF id equals null, id is empty, id has any of these symbols <>\"'([].)/")
   void variablesToCheckExerciseID(){
      boolean[] expected = {
            false, false, false,
            false, false, false,
            false, false, false,
            false, false, false,
            false, true, true,
            true, true, true,
      };
      String[] ids = {
            null, "", "/",
            "1", "12", "a<",
            "b>", "f\\", "m(",
            "n)", "a.", "k[",
            "l]", "ar", "run2",
            "F9", "0D", "Run"
      };

      for (int i = 0; i < expected.length; i++){
         testExerciseId(ids[i], expected[i]);
      }
   }



   void testExerciseId(String id, boolean expected){
      boolean result = validateInfo.exerciseId(id);
      assertEquals(result, expected);
   }

   @Test
   @DisplayName("Test password validation; should FAIL IF password has less than 8 digits, doesn't has an lowercase letter, doesn't has an uppercase letter ")
   void variablesToCheckPasswordValidation(){
      boolean[] expected = {
         false, false, false, false, false, false, true,
         true, true, true, true, true, true, true,
         false, false, true, true, true, true, true,
         false, true, false, false, false, false, false,
         false, false, false, true, false, false, false,
            false,
      };
      String[] passwords = {
         "123", "1ag", "12345678", "1234567a", "asdfghjk", "adfhjkkJ", "asdfght8K",
         "1234Ja12", "1aBcDeFg", "0a1b2c3D", "A234567a", "Asdfghj0", "9dfhjkkJ", "asdfght8K",
         "1234Ja12)", "1aBcDeFg(", "0a1b2c3D!", "A234567a@", "Asdfghj0#", "9dfhjkkJ$", "asdfght8K%",
         "1234Ja12\"", "1aBcDeFg&", "0a1b2c3D\\", "A234567a/", "Asdfghj0<", "9dfhjkkJ>", "asdfght8K;",
         "1234Ja12:", "1aBcDeFg[", "0a1b2c3D]", "A234567a*", "Asdfghj0[]", "9dfhjkkJ()", "asdfght8K<>",
            "abd Anj125",
      };

      // Run all our tests...
      for (int i=0;i<passwords.length;++i)
         testPasswordValidation(passwords[i], expected[i]);
   }

   @Test
   @DisplayName("Test userID validation; should FAIL IF userId has less than 4 digits, or has any of the following letters !@\\/()[];.,")
   void variablesToCheckIDValidation() {
      boolean[] expected = {
         false, false, false, false,
         true, true, true, true,
         false, false, false, false,
         false, false, false, false,
         false, false, false, false,
         false, false, false, false,
         false, false, false, false,
         false, false, false, false,
      };
      String[] userIds = {
         "123", "abc", "a2c", "ab",
         "1234", "abcd", "asdf", "zxcv",
         "1", "(abcd", ")asdf", ":zxcv",
         "1234!", "&abcd", "*asdf", ".zxcv",
         "1234\\", "\"abcd", "<asdf", "#zxcv",
         "1234@", "%abcd", ">asdf", ",zxcv",
         "1234#", "$abcd", ";asdf", "]zxcv",
         "1234[", "[]abcd", "()asdf", "]zxcv",
      };

      for (int i=0; i<userIds.length; ++i) {
         testUserIDValidation(userIds[i],expected[i]);
      }
   }

   void testUserIDValidation(String userId, boolean expectedResult) {
      boolean result = validateInfo.checkId(userId);
      assertEquals(result, expectedResult);
   }

   void testPasswordValidation(String password, boolean expectedResult){
      boolean result = validateInfo.password(password);
      assertEquals(result, expectedResult);
   }

}