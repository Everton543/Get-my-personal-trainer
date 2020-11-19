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
   private static final String TAG = "Model";

   //To-do: get these values in database to use in the CreateExerciseActivity
   private String[] exerciseNameList;
   private Exercise exercise;

   List<User> clientList = new ArrayList<User>(); //test

   public Model(){
      database = FirebaseDatabase.getInstance();
      databaseReference = database.getReference();
   }

   public Model(String Test){

   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   private boolean addUserToDatabase(final User user, final Activity activity){
      databaseReference = database.getReference("Users");

      if(validatePassword(user.getPassword()) == true) {
         try {
            Encrypt.hashUserPassword(user);
         } catch (Exception e) {
            e.printStackTrace();
         }
         if (user.getUserId() != null) {
            databaseReference.child(user.getUserId()).setValue(user);
            signUpSuccessfully(activity);
            return true;
         }
      } else{
         invalidPassword(activity);
      }

      return false;
   }

   public void saveUser(final User user, final Activity activity){
      Log.i("Model", "Call getIdFromDatabase with the id = " + user.getUserId());
      Query query = database.getReference("Users")
            .orderByChild("userId")
            .equalTo(user.getUserId());

      query.addValueEventListener(new ValueEventListener() {
         @RequiresApi(api = Build.VERSION_CODES.O)
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            clientList.clear();
            Log.i(TAG, "onDataChange from getIdFromDatabase called");
           if(snapshot.exists()){
               Log.i(TAG, "User " + user.getUserId() + " already exist");
               errorUserIdAlreadyExists(activity);
            }
            else{
               if(checkIfLoginIdIsValid(user.getUserId())) {
                  if(addUserToDatabase(user, activity)){
                     if(activity instanceof SignUpInterface){
                        ((SignUpInterface) activity).signUpSuccessfully();
                     }
                  }
               }
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   public boolean checkIfLoginIdIsValid(String loginId){
      if (loginId.length() < 4) {
         return  false;
      }
      Pattern pattern = Pattern.compile("[\\\\.\\]\\[<\\s>\"@#$%&*!';:,()/]");
      Matcher matcher = pattern.matcher(loginId);
      boolean passwordHasBadSymbols = matcher.find();

      if(passwordHasBadSymbols){
         return false;
      }

      return true;
   }

   public void checkLogin(final String userId , final String password, final Activity activity){
      Log.i(TAG, "checkLogin function called");
      Query query = database.getReference("Users")
            .orderByChild("userId")
            .equalTo(userId);

      query.addValueEventListener(new ValueEventListener() {
         @RequiresApi(api = Build.VERSION_CODES.O)
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.i(TAG, "Reading from database now");
            clientList.clear();
            if(snapshot.exists()){
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                  Client client = dataSnapshot.getValue(Client.class);
                  clientList.add(client);
               }

               Log.v(TAG, "Number of users with the id " + userId + " = " + clientList.size());
               clientList.get(0).setPassword(password);

               boolean verifyPasswordResult = false;
               try {
                  verifyPasswordResult = Encrypt.verifyPassword(clientList.get(0));
               } catch (Exception e) {
                  Log.e(TAG, "Was not able to decrypt password");
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

   public void getExerciseNameList(final Activity activity){
      //TO_DO: create logic
   }


   public boolean checkIfPasswordAreEqual(String databasePassword, String password){
      Log.i(TAG, "Checking if password are equal");
      return databasePassword.equals(password);
   }

   public void invalidPassword(Activity activity){
      Log.e(TAG, "Invalid password");
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
      Log.e(TAG, "Password not equal");
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

      pattern = Pattern.compile("[\\\\.\\]\\[<\\s>\"';:,()/]");
      matcher = pattern.matcher(password);
      boolean passwordHasBadSymbols = matcher.find();

      return ((passwordHasNumber == true)
            && (passwordHasLowercaseLetter == true)
            && (passwordHasUppercaseLetter == true )
            && (passwordHasBadSymbols == false));
   }

   public void wrongPasswordOrUserId(Activity activity){
      Context context = activity.getApplicationContext();
      CharSequence text = "Wrong Password or User Id";
      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
   }

   public void errorUserIdAlreadyExists(Activity activity){
      Context context = activity.getApplicationContext();
      CharSequence text = "User Id Already exists";
      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
   }


}
