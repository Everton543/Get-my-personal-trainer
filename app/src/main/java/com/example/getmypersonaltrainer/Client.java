package com.example.getmypersonaltrainer;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client implements UserInterface, ClientInterface{
   private UserTypes userType;
   private String password;
   private String name;
   private String userId;
   private String phone;
   private int[] birthDate;
   private float bodyMass;
   private float size;
   private Map<String, Exercise> exerciseList = new HashMap<String, Exercise>();
   private String hashedPassword;
   private String salt;
   private Map<String, InvitationMessage> invitationMessages;
   private boolean receivedInvitation = false;
   private String personalTrainerId;
   private boolean voted = false;

   public Client(){}

   public Client(UserTypes userType, String password, String name, String userId, String phone, int[] birthDate, float bodyMass, float size){
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
                 int[] birthDate,
                 float bodyMass,
                 float size,
                 String personalTrainerId,
                 boolean receivedInvitation,
                 Map<String, InvitationMessage> invitationMessages,
                 String hashedPassword,
                 String salt,
                 Map<String, Exercise> exerciseList){
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
      this.invitationMessages = invitationMessages;
      this.hashedPassword = hashedPassword;
      this.salt = salt;
      this.exerciseList = exerciseList;
   }


   @Override
   public Map<String, InvitationMessage> getInvitationMessage() {
      return invitationMessages;
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
   public void setInvitationMessage(Map<String, InvitationMessage> invitationMessage) {
      this.invitationMessages = invitationMessage;
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
   public void setExerciseList(Map<String,Exercise> exerciseList) {
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
   public void addNewInvitationMessage(InvitationMessage invitationMessage) {
      invitationMessages.put(invitationMessage.getSenderId(), invitationMessage);
   }

   @Override
   public float getBodyMass() {
      return bodyMass;
   }

   @Override
   public int[] getBirthDate() {
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
   public void setBirthDate(int[] birthDate) {
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

   public boolean UnsubscribeFromPersonalTrainer(){
      if(this.getPersonalTrainerId() == null) {
         return false;
      }
      Model temp = new Model();
      this.setPersonalTrainerId(null);
      temp.updateClient(this);
      return true;
   }

   public int getAge()
   {
      //TO-DO: FIX THIS
      return 100;

   }

   public List<Exercise> getExercisesOfWeekDay(DayOfWeek dayOfWeek){
      List<Exercise> exercises = new ArrayList<Exercise>();
      for (Map.Entry me : exerciseList.entrySet()) {
         if(me.getValue().equals(dayOfWeek)){
            exercises.add((Exercise) me.getValue());
         }
      }
      return exercises;
   }

}
