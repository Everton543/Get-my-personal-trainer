package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Model implements Serializable {
   private FirebaseDatabase database = null;
   private DatabaseReference databaseReference = null;
   final static String mainTable = "get-my-personal-trainer";
   private ChildEventListener childEventListener;

   List<User> clientList = new ArrayList<User>(); //test

   public Model(){
      database = FirebaseDatabase.getInstance();
      databaseReference = database.getReference();
   }

   private void getIdFromDatabase(String logId){
      Log.i("Model", "Call getIdFromDatabase with the id = " + logId);
      Query query = database.getReference("Users")
            .orderByChild("userId")
            .equalTo(logId);

      query.addValueEventListener(new ValueEventListener() {
         @RequiresApi(api = Build.VERSION_CODES.O)
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            clientList.clear();
            Log.i("Model", "onDataChange from getIdFromDatabase called");
            if(snapshot.exists()){
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                  Client client = dataSnapshot.getValue(Client.class);
                  clientList.add(client);
               }

               System.out.println("NUMBER OF QUERY RESULTS: " + clientList.size());
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });

   }

   public boolean checkIfLoginIdExists(String logId){
      //Invalid values #, $, . , /, \, |
      getIdFromDatabase(logId);

      //check if there's already exists the user Id.
      return clientList.size() > 0;
   }

   public void checkLogin(String userId , final String password, final Activity activity){
      //databaseReference = database.getReference("Users");
      Query query = database.getReference("Users")
            .orderByChild("userId")
            .equalTo(userId);

      query.addValueEventListener(new ValueEventListener() {
         @RequiresApi(api = Build.VERSION_CODES.O)
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            clientList.clear();
            if(snapshot.exists()){
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                  Client client = dataSnapshot.getValue(Client.class);
                  clientList.add(client);
               }

               System.out.println("NUMBER OF QUERY RESULTS: " + clientList.size());
               clientList.get(0).setPassword(password);

               //if(checkIfPasswordAreEqual(clientList.get(0).getPassword(), password)){
               boolean verifyPasswordResult = false;
               try {
                  verifyPasswordResult = Encrypt.verifyPassword(clientList.get(0));
               } catch (Exception e) {
                  e.printStackTrace();
               }

               if(verifyPasswordResult){
                  signUpSuccessfully(activity);
                  if(activity instanceof LoginInterface){
                     ((LoginInterface) activity).loginUserType(
                           clientList.get(0).getUserType(),
                           true);
                  }
               }else {
                  passwordNotEqualError(activity);
               }
            }
            else {

               writeInfo(snapshot.child("Over").child("password").getValue(String.class));
               if (activity instanceof LoginInterface) {
                  ((LoginInterface) activity).loginUserType(
                        UserTypes.NONE,
                        false);
               }

               wrongPasswordOrUserId(activity);
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }


   public boolean checkIfPasswordAreEqual(String databasePassword, String password){
      return databasePassword.equals(password);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public boolean addNewClient(Client client, Activity activity) {
      databaseReference = database.getReference("Users");

      if(validatePassword(client.getPassword()) == true) {
         try {
            Encrypt.hashUserPassword(client);
         } catch (Exception e) {
            e.printStackTrace();
         }
         if (client.getUserId() != null ) {
            databaseReference.child(client.getUserId()).setValue(client);
            signUpSuccessfully(activity);
            return true;
         }
      } else{
         invalidPassword(activity);
      }
      return false;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public boolean addNewPersonalTrainer(PersonalTrainer personalTrainer, Activity activity){
      databaseReference = database.getReference("Users");

      if(validatePassword(personalTrainer.getPassword()) == true) {
         try {
            Encrypt.hashUserPassword(personalTrainer);
         } catch (Exception e) {
            e.printStackTrace();
         }
         if (personalTrainer.getUserId() != null) {
            databaseReference.child(personalTrainer.getUserId()).setValue(personalTrainer);
            signUpSuccessfully(activity);
            return true;
         }
      } else{
         invalidPassword(activity);
      }

      return false;
   }

   public void invalidPassword(Activity activity){
      Context context = activity.getApplicationContext();
      CharSequence text = "Invalid password, Must have 8 digits, 1 capital letter, 1 lower case letter, 1 number";
      int duration = Toast.LENGTH_LONG;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
   }

   public void signUpSuccessfully(Activity activity){
      Context context = activity.getApplicationContext();
      CharSequence text = "Sign Up Successfully";
      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
   }

   public void passwordNotEqualError(Activity activity){
      Context context = activity.getApplicationContext();
      CharSequence text = "Password not equal";
      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
   }

   public void updateUser(User user){

   }

   public void updateExercise(Exercise exercise){

   }

   public void addNewExercise(Exercise exercise){

   }

   public boolean login(String logId, String password){



      return false;
   }

   public boolean logout(){
      return false;
   }

   private String encryptPassword(String password){
      return null;
   }

   public boolean validatePassword(String password) {
      if(password.length() < 8){
         return false;
      }

      Pattern pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(password);
      boolean passwordHasNumber = matcher.find();

      pattern = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
      matcher = pattern.matcher(password);
      boolean passwordHasLowercaseLetter = matcher.find();

      pattern = Pattern.compile("[A-Z]", Pattern.UNICODE_CASE);
      matcher = pattern.matcher(password);
      boolean passwordHasUppercaseLetter = matcher.find();

      return ((passwordHasNumber == true)
            && (passwordHasLowercaseLetter == true)
            && (passwordHasUppercaseLetter == true ));
   }

   public void wrongPasswordOrUserId(Activity activity){
      Context context = activity.getApplicationContext();
      CharSequence text = "Wrong Password or User Id";
      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
   }

   public void writeInfo(String info){
      System.out.println("HEEEEEEREEEEEE " + info);
   }
}
