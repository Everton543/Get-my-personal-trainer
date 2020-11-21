package com.example.getmypersonaltrainer;

import java.util.HashMap;
import java.util.List;

public interface UserInterface {

   UserTypes getUserType();
   String getPassword();
   String getUserId();
   String getName();
   String getSalt();
   String getHashedPassword();
   HashMap<String, Exercise> getExerciseList();
   String getInvitationMessage();
   boolean getReceivedInvitation();

   void setReceivedInvitation(boolean invitation);
   void setInvitationMessage(String invitationMessage);
   void setExerciseList(HashMap<String, Exercise> exerciseList);
   void setSalt(String salt);
   void setHashedPassword(String hashedPassword);
   void setUserId(String userId);
   void setPassword(String password);
   void setName(String name);
   void setUserType(UserTypes type);
}
