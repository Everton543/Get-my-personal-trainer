package com.example.getmypersonaltrainer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

   //Password must have 8 digits, at least 1 number, and 1 capital letter and 1 lowercase letter


   @DisplayName("Add new client")
   void addNewClientTest(){
      Model model = new Model();
      model.addNewClient();
   }

   @Test
   @DisplayName("Test password validation; should FAIL IF password has less than 8 digits, doesn't has an lowercase letter, doesn't has an uppercase letter ")
   void variablesToCheckPasswordValidation(){
      testPasswordValidation("123", false);
      testPasswordValidation("1aG", false);
      testPasswordValidation("12345678", false);
      testPasswordValidation("1234567a", false);
      testPasswordValidation("asdfghjk", false);
      testPasswordValidation("adfhjkkJ", false);
      testPasswordValidation("asdfght8K", true);
   }


   void testPasswordValidation(String password, boolean expectedResult){
      Model model = new Model();
      boolean result = model.validatePassword(password);

      assertEquals(result, expectedResult);
   }

}