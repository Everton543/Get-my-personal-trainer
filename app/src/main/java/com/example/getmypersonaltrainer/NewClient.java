package com.example.getmypersonaltrainer;

public class NewClient {
   private String id;
   private String password;

   public NewClient() {
   }

   public NewClient(String id, String password){
      this.id = id;
      this.password = password;
   }


   String getId(){
      return id;
   }

   String getPassword(){
      return password;
   }

}
