package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Presenter {
   private static final String TAG = "Presenter";
   private Model model;
   private boolean logged;
   private UserInterface user;
   private PersonalTrainer myPersonalTrainer = null;
   private Class<?> goingTo = null;
   private Class<?> goBack = null;
   private Activity actualActivity = null;
   private Client changingClient = null;
   private Exercise selectedExercise = null;
   private List<PersonalTrainer> allPersonalTrainers = new ArrayList<>();
   private boolean getInfoFromDatabase = false;
   private Map<String, Exercise> freeExerciseList = new HashMap<>();
   private List<Client> clientList = new ArrayList<>();


   public void  resetValues(){
      Log.i(TAG, "ResetValue");
      logged = false;
      user = null;
      myPersonalTrainer = null;
      goingTo = null;
      goBack = null;
      changingClient = null;
      selectedExercise = null;
      allPersonalTrainers = new ArrayList<>();
      getInfoFromDatabase = false;
      freeExerciseList = new HashMap<>();
      clientList = new ArrayList<>();
      model.resetValues();
   }


   public List<Client> getClientList() {
      return clientList;
   }

   public void setClientList(List<Client> clientList) {
      this.clientList = clientList;
   }

   public Map<String, Exercise> getFreeExerciseList() {
      return freeExerciseList;
   }

   public void setFreeExerciseList(Map<String, Exercise> freeExerciseList) {
      this.freeExerciseList = freeExerciseList;
   }

   public void goToMyHome(View view){
      Intent intent = null;
      if(user instanceof  Client){
         intent = new Intent(actualActivity, ClientMainActivity.class);
      } else if(user instanceof PersonalTrainer){
         intent = new Intent(actualActivity, PersonalTrainerMainActivity.class);
      }

      actualActivity.startActivity(intent);
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
         personalTrainer.setReceivedInvitation(
                 getUser().getReceivedInvitation()
         );
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

   public Exercise getSelectedExercise() {
      return selectedExercise;
   }

   public void setSelectedExercise(Exercise selectedExercise) {
      this.selectedExercise = selectedExercise;
   }
}
