package com.example.getmypersonaltrainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements UserInterface, ClientInterface, PersonalTrainerInterface{
   private UserTypes userType;
   private String password;
   private String name;
   private String userId;
   private String phone;
   private String birthDate;
   private float bodyMass;
   private float size;
   private Map<String, Exercise> exerciseList = new HashMap<String, Exercise>();
   private String hashedPassword;
   private String salt;
   private String aboutMyselfText;
   private List<Client> clientList = new ArrayList<Client>();
   private String personalTrainerId;
   private String invitationMessage;
   private List<String> exerciseNameList = new ArrayList<String>();
   private boolean receivedInvitation;
   private int score;
   private int voteQuantity;
   private boolean voted;


   User(){}

   User(UserTypes userType, String password, String name, String userId, String phone, String birthDate, float bodyMass, float size){
      this.userType = userType;
      this.password = password;
      this.birthDate = birthDate;
      this.bodyMass = bodyMass;
      this.name = name;
      this.userId = userId;
      this.size = size;
      this.phone = phone;
   }

   User(UserTypes userType, String password, String name, String userId, String aboutMyselfText){
      this.aboutMyselfText = aboutMyselfText;
      this.name = name;
      this.userId = userId;
      this.password = password;
      this.userType = userType;
   }

   @Override
   public UserTypes getUserType() {
      return userType;
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
   public Map<String, Exercise> getExerciseList() {
      return exerciseList;
   }

   @Override
   public String getInvitationMessage() {
      return invitationMessage;
   }

   @Override
   public boolean getReceivedInvitation() {
      return false;
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
   public int getVoteQuantity() {
      return voteQuantity;
   }

   @Override
   public void setVoteQuantity(int voteQuantity) {
      this.voteQuantity = voteQuantity;
   }

   @Override
   public void setExerciseNameList(List<String> exerciseNameList) {
      this.exerciseNameList = exerciseNameList;
   }

   @Override
   public void setScore(int score) {
      this.score = score;
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
   public float getBodyMass() {
      return bodyMass;
   }

   @Override
   public String getBirthDate() {
      return birthDate;
   }

   @Override
   public float getSize() {
      return size;
   }

   @Override
   public void setExerciseList(Map<String, Exercise> exerciseList) {
      this.exerciseList = exerciseList;
   }

   @Override
   public String getPhone() {
      return phone;
   }

   @Override
   public String getPersonalTrainerId() {
      return personalTrainerId;
   }

   @Override
   public boolean getVoted() {
      return voted;
   }

   @Override
   public void setPersonalTrainerId(String trainerId) {
      personalTrainerId = trainerId;
   }

   @Override
   public void setBirthDate(String birthDate) {
      this.birthDate = birthDate;
   }

   @Override
   public void setSize(float size) {
      this.size = size;
   }

   @Override
   public void setBodyMass(float bodyMass) {
      this.bodyMass = bodyMass;
   }

   @Override
   public void setPhone(String phone) {
      this.phone = phone;
   }

   @Override
   public void setVoted(boolean voted) {
      this.voted = voted;
   }

}
