package com.example.getmypersonaltrainer;

import java.util.HashMap;

public interface User {
   UserTypes getUserType();
   HashMap<String,Exercise> getExerciseList();
   String getPassword();
   String getUserId();
   String getName();
   void setUserId(String userId);
   void setPassword(String password);
   void setName(String name);
   void setUserType(UserTypes type);
}
