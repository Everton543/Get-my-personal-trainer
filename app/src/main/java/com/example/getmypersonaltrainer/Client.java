package com.example.getmypersonaltrainer;

import java.util.HashMap;

public class Client implements User, ClientInterface{
   private UserTypes userType;
   private String password;
   private String name;
   private String userId;
   private String phone;
   private String birthDate;
   private float bodyMass;
   private float size;
   private HashMap<String, Exercise> exerciseList = null;

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


   @Override
   public UserTypes getUserType() {
      return userType;
   }

   @Override
   public HashMap<String, Exercise> getExerciseList() {
      return exerciseList;
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
}