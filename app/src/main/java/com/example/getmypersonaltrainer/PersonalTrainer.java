package com.example.getmypersonaltrainer;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class PersonalTrainer implements UserInterface, PersonalTrainerInterface{
   private UserTypes userType;
   private String password;
   private String name;
   private String userId;
   private String aboutMyselfText;
   private List<Client> clientList = null;
   private HashMap<String, Exercise> exerciseList = null;
   private String hashedPassword;
   private String salt;
   private String[] exerciseNameList = null;

   public PersonalTrainer(){}

   public PersonalTrainer(UserTypes userType, String password, String name, String userId, String aboutMyselfText){
      this.aboutMyselfText = aboutMyselfText;
      this.name = name;
      this.userId = userId;
      this.password = password;
      this.userType = userType;
   }

   public PersonalTrainer(UserTypes userType,
                          String hashedPassword,
                          String salt,
                          String name,
                          String userId,
                          String aboutMyselfText,
                          HashMap<String,Exercise> exerciseList){
      this.aboutMyselfText = aboutMyselfText;
      this.name = name;
      this.userId = userId;
      this.salt = salt;
      this.hashedPassword = hashedPassword;
      this.userType = userType;
      this.exerciseList = exerciseList;
   }


   @Override
   public UserTypes getUserType() {
      return userType;
   }

   @Override
   public HashMap<String, Exercise> getExerciseList() {
      return exerciseList;
   }

   @Override
   public void setExerciseList(HashMap<String, Exercise> exerciseList) {
      this.exerciseList = exerciseList;
   }

   @Override
   public String getPassword() {
      return password;
   }

   @Override
   public String getUserId() {
      return userId;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public String getSalt() {
      return salt;
   }

   @Override
   public String getHashedPassword() {
      return hashedPassword;
   }

   @Override
   public void setSalt(String salt) {
      this.salt = salt;
   }

   @Override
   public void setHashedPassword(String hashedPassword) {
      this.hashedPassword = hashedPassword;
   }

   @Override
   public void setUserId(String userId) {
      this.userId = userId;
   }

   @Override
   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public void setName(String name) {
      this.name = name;
   }

   @Override
   public void setUserType(UserTypes type) {
      this.userType = type;
   }

   @Override
   public List<Client> getClients() {
      return clientList;
   }

   @Override
   public void addNewClient(Client newClient) {

   }

   @Override
   public void removeClient(String clientId) {

   }

   @Override
   public String getAboutMyselfText() {
      return aboutMyselfText;
   }

   @Override
   public String[] getExerciseNameList() {
      if(exerciseNameList.length == 0) {
         int i = 0;
         for (String exerciseName : exerciseList.keySet()) {
            exerciseNameList[i] = exerciseName;
            ++i;
         }
      }

      return exerciseNameList;
   }

   public void addNewExerciseName(String exerciseName){
      if(exerciseNameList.length > 0) {
         exerciseNameList[exerciseNameList.length] = exerciseName;
      } else{
         exerciseNameList[0] = exerciseName;
      }
   }

   @Override
   public void setAboutMyselfText(String aboutMyselfText) {
      this.aboutMyselfText = aboutMyselfText;
   }

   @Override
   public void setClients(List<Client> clientList) {
      this.clientList = clientList;
   }

   public void setExerciseNameList(String[] exerciseNameList){
      this.exerciseNameList = exerciseNameList;
   }
}
