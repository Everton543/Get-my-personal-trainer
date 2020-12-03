package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Presenter {
   private Model model;
   private boolean logged;
   private boolean recyclerViewLoadingError = false;
   private UserInterface user;
   private PersonalTrainer myPersonalTrainer = null;
   private Class<?> goingTo = null;
   private Class<?> goBack = null;
   private Activity actualActivity = null;
   private Client changingClient = null;
   private List<PersonalTrainer> allPersonalTrainers = new ArrayList<PersonalTrainer>();
   private boolean getInfoFromDatabase = false;
   private boolean getExercises = false;
   private Map<String, Exercise> freeExerciseList = new HashMap<String, Exercise>();

   public boolean isGetExercises() {
      return getExercises;
   }

   public void setGetExercises(boolean getExercises) {
      this.getExercises = getExercises;
   }

   public Map<String, Exercise> getFreeExerciseList() {
      return freeExerciseList;
   }

   public void setFreeExerciseList(Map<String, Exercise> freeExerciseList) {
      this.freeExerciseList = freeExerciseList;
   }

   Presenter(){
      model = new Model();
      logged = false;
   }

   public void setUserAsClient(){
      if(getUser() instanceof ClientInterface) {
         Client client = new Client(
               getUser().getUserType(),
               "",
               getUser().getName(),
               getUser().getUserId(),
               ((ClientInterface) getUser()).getPhone(),
               ((ClientInterface) getUser()).getBirthDate(),
               ((ClientInterface) getUser()).getBodyMass(),
               ((ClientInterface) getUser()).getSize(),
               ((ClientInterface) getUser()).getPersonalTrainerId(),
               getUser().getReceivedInvitation(),
               getUser().getInvitationMessage(),
               getUser().getHashedPassword(),
               getUser().getSalt(),
               getUser().getExerciseList()
         );

         setUser(client);
      }
   }


   public void setUserAsPersonalTrainer() {
      PersonalTrainer personalTrainer = null;
      if(getUser() instanceof  PersonalTrainerInterface) {
         personalTrainer = new PersonalTrainer(getUser().getUserType(),
               getUser().getHashedPassword(),
               getUser().getSalt(),
               getUser().getName(),
               getUser().getUserId(),
               ((PersonalTrainerInterface) getUser()).getAboutMyselfText(),
               getUser().getExerciseList());
      }
      setUser(personalTrainer);
   }

   public PersonalTrainer getMyPersonalTrainer() {
      return myPersonalTrainer;
   }

   public void setMyPersonalTrainer(PersonalTrainer myPersonalTrainer) {
      this.myPersonalTrainer = myPersonalTrainer;
   }

   public Model getModel() {
      return model;
   }

   public void setModel(Model model) {
      this.model = model;
   }

   public boolean isLogged() {
      return logged;
   }

   public void setLogged(boolean logged) {
      this.logged = logged;
   }

   public UserInterface getUser() {
      return user;
   }

   public void setUser(UserInterface user) {
      this.user = user;
   }

   public Class<?> getGoingTo() {
      return goingTo;
   }

   public void setGoingTo(Class<?> goingTo) {
      this.goingTo = goingTo;
   }

   public Activity getActualActivity() {
      return actualActivity;
   }

   public void setActualActivity(Activity actualActivity) {
      this.actualActivity = actualActivity;
   }

   public Class<?> getGoBack() {
      return goBack;
   }

   public void setGoBack(Class<?> goBack) {
      this.goBack = goBack;
   }

   public Client getChangingClient() {
      return changingClient;
   }

   public void setChangingClient(Client changingClient) {
      this.changingClient = changingClient;
   }

   public List<PersonalTrainer> getAllPersonalTrainers() {
      return allPersonalTrainers;
   }

   public void setAllPersonalTrainers(List<PersonalTrainer> allPersonalTrainers) {
      this.allPersonalTrainers = allPersonalTrainers;
   }

   public boolean isGetInfoFromDatabase() {
      return getInfoFromDatabase;
   }

   public void setGetInfoFromDatabase(boolean getInfoFromDatabase) {
      this.getInfoFromDatabase = getInfoFromDatabase;
   }

   public boolean isRecyclerViewLoadingError() {
      return recyclerViewLoadingError;
   }

   public void setRecyclerViewLoadingError(boolean recyclerViewLoadingError) {
      this.recyclerViewLoadingError = recyclerViewLoadingError;
   }
}
