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
   @DisplayName("Test password validation; should FAIL IF password has less than 4 digits")
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
      String[] userids = {
         "123", "abc", "a2c", "ab",
         "1234", "abcd", "asdf", "zxcv",
         "1234", "(abcd", ")asdf", ":zxcv",
         "1234!", "&abcd", "*asdf", ".zxcv",
         "1234\\", "\"abcd", "<asdf", "#zxcv",
         "1234@", "%abcd", ">asdf", ",zxcv",
         "1234#", "$abcd", ";asdf", "]zxcv",
         "1234[", "[]abcd", "()asdf", "]zxcv",
      };

      for (int i=0; i<userids.length; ++i) {
         testUserIDValidation(userids[i],expected[i]);
      }
   }

   void testUserIDValidation(String userid, boolean expectedResult) {

      // Add model userid validation when created...
      boolean result = true;
      if (userid.length()>=4) {
         result = true;
      }

      if (result) {  // Let's do some more testing...
         // This should be added to validateUserid method...
         result = !userid.matches("!\\\\@#$%\"&\\(\\)\\[\\]\\*<>;:\\.,]");
      }
   }


   void testPasswordValidation(String password, boolean expectedResult){
      boolean result = model.validatePassword(password);
      assertEquals(result, expectedResult);
   }

}