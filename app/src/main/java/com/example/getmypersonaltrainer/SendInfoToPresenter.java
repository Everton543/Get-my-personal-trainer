package com.example.getmypersonaltrainer;

public interface SendInfoToPresenter {
   void addNewClient(Client client);
   void removeClient(Client client);
   void addExercise(Exercise exercise);
   void changeExercise(Exercise exercise);
   void changeClientInfo(Client client);
   void changePersonalTrainerInfo(PersonalTrainer personalTrainer);
   void unfollowPersonalTrainer(PersonalTrainer personalTrainer);
   void login(String id, String password);
   void clientSingUp(Client client);
   void personalTrainerSingUp(PersonalTrainer personalTrainer);

}
