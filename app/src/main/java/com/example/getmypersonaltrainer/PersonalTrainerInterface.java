package com.example.getmypersonaltrainer;

import java.util.HashMap;
import java.util.List;

public interface PersonalTrainerInterface {
   List<Client> getClients();
   void addNewClient(Client newClient);
   void removeClient(String clientId);
   String getAboutMyselfText();
   List<String> getExerciseNameList();
   int getScore();

   void setScore(int score);
   void setAboutMyselfText(String aboutMyselfText);
   void setClients(List<Client> clientList);
}
