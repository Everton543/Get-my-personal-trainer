package com.example.getmypersonaltrainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalTrainer implements UserInterface, PersonalTrainerInterface{
   private UserTypes userType;
   private String password;
   private String name;
   private String userId;
   private String aboutMyselfText;
   private List<Client> clientList = new ArrayList<Client>();
   private Map<String, Exercise> exerciseList = new HashMap<String, Exercise>();
   private String hashedPassword;
   private String salt;
   private List<String> exerciseNameList = new ArrayList<String>();
   private String invitationMessage;
   private boolean receivedInvitation = false;
   private int score = 0;
   private int voteQuantity = 0;

   public PersonalTrainer(){
   }

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
                          Map<String, Exercise> exerciseList){
      this.aboutMyselfText = aboutMyselfText;
      this.name = name;
      this.userId = userId;
      this.salt = salt;
      this.hashedPassword = hashedPassword;
      this.userType = userType;
      this.exerciseList = exerciseList;

      if(this.exerciseList != null){
         for (Map.Entry mapEntry : exerciseList.entrySet()) {
            exerciseNameList.add((String) mapEntry.getKey());
         }
      }
   }


   @Override
   public UserTypes getUserType() {
      return userType;
   }

   @Override
   public Map<String, Exercise> getExerciseList() {
      return exerciseList;
   }

   @Override
   public String getInvitationMessage() {
      return invitationMessage;
   }

   @Override
   public boolean getReceivedInvitation() {
      return receivedInvitation;
   }

   @Override
   public void setReceivedInvitation(boolean invitation) {
      this.receivedInvitation = invitation;
   }

   @Override
   public void setInvitationMessage(String invitationMessage) {
      this.invitationMessage = invitationMessage;
   }

   @Override
   public void setExerciseList(Map<String, Exercise> exerciseList) {
      this.exerciseList = exerciseList;
      if(exerciseList != null) {
         for (Map.Entry mapEntry : exerciseList.entrySet()) {
            exerciseNameList.add((String) mapEntry.getKey());
         }
      }
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
   public List<String> getExerciseNameList() {
      return exerciseNameList;
   }

   @Override
   public int getScore() {
      return score;
   }

   @Override
   public void setScore(int score) {
      this.score = score;
   }

   public void addNewExerciseName(String exerciseName){
      exerciseNameList.add(exerciseName);
   }

   @Override
   public void setAboutMyselfText(String aboutMyselfText) {
      this.aboutMyselfText = aboutMyselfText;
   }

   @Override
   public void setClients(List<Client> clientList) {
      this.clientList = clientList;
   }

   @Override
   public void setExerciseNameList(List<String> exerciseNameList){
      this.exerciseNameList = exerciseNameList;
   }

   @Override
   public int getVoteQuantity() {
      return voteQuantity;
   }

   @Override
   public void setVoteQuantity(int voteQuantity) {
      this.voteQuantity = voteQuantity;
   }

   public int getAverageScore(){
      return  score / voteQuantity;
   }

   public void newVote(int score){
      if(this.score + score < Integer.MAX_VALUE) {
         this.score += score;
         this.voteQuantity += 1;
      }else{
         this.score = getAverageScore();
         this.score += score;

         //Setting vote Quantity to 2 because 1 is the vote from the Average score
         //and the second is the given score.
         this.voteQuantity = 2;
      }
   }

   public String [] getClientsID(){
      List<String> clientsID= new ArrayList<String>();
      for(int i = 0; i < clientList.size(); i++){
         clientsID.add(clientList.get(i).getUserId());
      }

      return clientsID.toArray(new String[0]);
   }

}
