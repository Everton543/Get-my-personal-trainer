package com.example.getmypersonaltrainer;

import java.util.HashMap;
import java.util.List;

public class Client implements UserInterface, ClientInterface{
   private UserTypes userType;
   private String password;
   private String name;
   private String userId;
   private String phone;
   private String birthDate;
   private float bodyMass;
   private float size;
   private HashMap<String, Exercise> exerciseList = null;
   private String hashedPassword;
   private String salt;
   private String invitationMessage;
   private boolean receivedInvitation = false;
   private String personalTrainerId;
   private boolean voted = false;

   public Client(){}

   public Client(UserTypes userType, String password, String name, String userId, String phone, String birthDate, float bodyMass, float size){
      this.userType = userType;
      this.password = password;
      this.birthDate = birthDate;
      this.bodyMass = bodyMass;
      this.name = name;
      this.userId = userId;
      this.size = size;
      this.phone = phone;
   }

   public Client(UserTypes userType,
                 String password,
                 String name,
                 String userId,
                 String phone,
                 String birthDate,
                 float bodyMass,
                 float size,
                 String personalTrainerId,
                 boolean receivedInvitation,
                 String invitationMessage,
                 String hashedPassword,
                 String salt){
      this.userType = userType;
      this.password = password;
      this.birthDate = birthDate;
      this.bodyMass = bodyMass;
      this.name = name;
      this.userId = userId;
      this.size = size;
      this.phone = phone;
      this.personalTrainerId = personalTrainerId;
      this.receivedInvitation = receivedInvitation;
      this.invitationMessage = invitationMessage;
      this.hashedPassword = hashedPassword;
      this.salt = salt;
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
   public HashMap<String, Exercise> getExerciseList() {
      return exerciseList;
   }

   @Override
   public void setExerciseList(HashMap<String, Exercise> exerciseList) {
      this.exerciseList = exerciseList;
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
   public void setUserType(UserTypes userType) {
      this.userType = userType;
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
      this.personalTrainerId = trainerId;
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

   public int getAge(){
      return 100;
   }
}
