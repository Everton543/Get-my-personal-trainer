package com.example.getmypersonaltrainer;

import java.util.HashMap;

public interface User {
   UserTypes getUserType();
   HashMap<String,Exercise> getExerciseList();
   String getPassword();
   String getUserId();
   String getName();
   String getSalt();
   String getHashedPassword();

   void setSalt(String salt);
   void setHashedPassword(String hashedPassword);
   void setUserId(String userId);
   void setPassword(String password);
   void setName(String name);
   void setUserType(UserTypes type);
}
