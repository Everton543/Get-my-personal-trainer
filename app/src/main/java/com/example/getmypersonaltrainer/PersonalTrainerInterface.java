package com.example.getmypersonaltrainer;

import java.util.List;

public interface PersonalTrainerInterface {
   List<Client> getClients();
   void addNewClient(Client newClient);
   void removeClient(String clientId);
   String getAboutMyselfText();
   void setAboutMyselfText(String aboutMyselfText);
}
