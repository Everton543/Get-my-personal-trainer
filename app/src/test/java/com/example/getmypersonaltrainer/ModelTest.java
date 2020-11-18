package com.example.getmypersonaltrainer;

import android.app.Activity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

   //Password must have 8 digits, at least 1 number, and 1 capital letter and 1 lowercase letter
   private Model model = new Model("test");

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
      boolean result = model.checkIfLoginIdIsValid(userId);
      assertEquals(result, expectedResult);
   }


   void testPasswordValidation(String password, boolean expectedResult){
      boolean result = model.validatePassword(password);
      assertEquals(result, expectedResult);
   }

}