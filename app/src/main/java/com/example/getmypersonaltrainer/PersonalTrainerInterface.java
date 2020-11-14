package com.example.getmypersonaltrainer;

import java.util.HashMap;
import java.util.List;

public interface PersonalTrainerInterface {
   List<Client> getClients();
   void addNewClient(Client newClient);
   void removeClient(String clientId);
   String getAboutMyselfText();
   HashMap<String, Exercise> getExerciseList();
   void setAboutMyselfText(String aboutMyselfText);
   void setClients(List<Client> clientList);
   void setExerciseList(HashMap<String, Exercise> exerciseList);
}
