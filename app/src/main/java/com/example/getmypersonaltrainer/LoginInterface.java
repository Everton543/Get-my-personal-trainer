package com.example.getmypersonaltrainer;

public interface LoginInterface {
   void loginUserType(UserTypes userType, boolean goodLoginResult);
   void setPresenterUser(User user);
   void setPersonalTrainerExerciseNameList();
}
