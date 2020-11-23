package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Model {
   private FirebaseDatabase database = null;
   private DatabaseReference databaseReference = null;
   private ChildEventListener childEventListener;
   private static final String TAG = "Model";
   private Warnings warnings = new Warnings();
   private ValidateInfo validateInfo = new ValidateInfo();
   private List<Client> clientToInvite = new ArrayList<Client>();
   private List<PersonalTrainer> personalTrainers = new ArrayList<PersonalTrainer>();
   List<User> userList = new ArrayList<User>();


   public Model(){
      database = FirebaseDatabase.getInstance();
      databaseReference = database.getReference();
   }

   ValidateInfo getValidateInfo(){
      return validateInfo;
   }

   Warnings getWarnings(){
      return warnings;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   private boolean addUserToDatabase(final User user){
      databaseReference = database.getReference("Users");

      if(validateInfo.password(user.getPassword()) == true) {
         try {
            Encrypt.hashUserPassword(user);
         } catch (Exception e) {
            e.printStackTrace();
         }
         databaseReference.child(user.getUserId()).setValue(user);
         warnings.signUpSuccessfully();
         return true;

      } else{
         warnings.invalidPassword();
      }

      return false;
   }


   public void savePublicExercise(final Exercise exercise){
      Log.i(TAG, "Call savePublicExercise with the id = " + exercise.getExerciseId());
      if(validateInfo.checkId(exercise.getExerciseId())) {
         Query query = database.getReference("Exercise")
               .orderByChild("exerciseId")
               .equalTo(exercise.getExerciseId());

         query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               userList.clear();
               Log.i(TAG, "onDataChange from savePublicExercise");
               if (snapshot.exists()) {
                  Log.i(TAG, "Exercise " + exercise.getExerciseId() + " already exist");
                  warnings.errorExerciseAlreadyExists();
                  if (MainActivity.presenter.getActualActivity() instanceof LoadingActivity) {
                     ((LoadingActivity) MainActivity.presenter.getActualActivity()).loadingError();
                  }

               } else if(addPublicExerciseToDatabase(exercise)){
                  if (MainActivity.presenter.getActualActivity() instanceof LoadingActivity) {
                     ((LoadingActivity) MainActivity.presenter.getActualActivity()).finishedCharge();
                  }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
         });
      }
   }

   private boolean addPublicExerciseToDatabase(final Exercise exercise){
      databaseReference = database.getReference("Exercise");
      if(validateInfo.checkId(exercise.getExerciseId()) == true) {
            databaseReference.child(exercise.getExerciseId()).setValue(exercise);
            warnings.createdNewExerciseSuccessfully();
            return true;
      } else{
         warnings.invalidExerciseName();
      }

      return false;
   }

   public void updateClient(final Client client){
      databaseReference = database.getReference("Users");
      databaseReference.child(client.getUserId()).setValue(client);
   }

   public void saveUser(final User user){
      Log.i("Model", "Call getIdFromDatabase with the id = " + user.getUserId());
      Query query = database.getReference("Users")
            .orderByChild("userId")
            .equalTo(user.getUserId());

      query.addValueEventListener(new ValueEventListener() {
         @RequiresApi(api = Build.VERSION_CODES.O)
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            userList.clear();
            Log.i(TAG, "onDataChange from getIdFromDatabase called");
           if(snapshot.exists()){
               Log.i(TAG, "User " + user.getUserId() + " already exist");
               warnings.errorUserIdAlreadyExists();
              if (MainActivity.presenter.getActualActivity() instanceof LoadingActivity) {
                 ((LoadingActivity) MainActivity.presenter.getActualActivity()).loadingError();
              }
            }
            else{
               if(validateInfo.checkId(user.getUserId())) {
                  if(addUserToDatabase(user)){
                     if (MainActivity.presenter.getActualActivity() instanceof LoadingActivity) {
                        ((LoadingActivity) MainActivity.presenter.getActualActivity()).finishedCharge();
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

   public void sendInvitationToClient(final String clientId){
      Log.i(TAG, "Call sendInvitationToClient with the id = " + clientId);
      if(validateInfo.checkId(clientId)) {
         Query query = database.getReference("Users")
               .orderByChild("userId")
               .equalTo(clientId);

         query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               userList.clear();
               Log.i(TAG, "onDataChange from sendInvitationToClient");
               if (snapshot.exists()) {
                  for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                     Client client = dataSnapshot.getValue(Client.class);
                     clientToInvite.add(client);
                  }

                  if(clientToInvite.get(0).getReceivedInvitation() == false) {

                     if (MainActivity.presenter.getUser() instanceof PersonalTrainer) {
                        String invitationMessage = "The personal trainer " + MainActivity.presenter.getUser().getName();
                        invitationMessage += " that has the ID: " + MainActivity.presenter.getUser().getUserId();
                        invitationMessage += " wants to be your personal trainer. Will you accept?";

                        clientToInvite.get(0).setPersonalTrainerId(MainActivity.presenter.getUser().getUserId());
                        clientToInvite.get(0).setReceivedInvitation(true);
                        clientToInvite.get(0).setInvitationMessage(invitationMessage);

                        updateClient(clientToInvite.get(0));

                        warnings.invitationGotSend();
                     }
                  }
                  else{
                     warnings.errorClientHasAlreadyReceivedAnInvitation();
                  }
               }
               else{
                  warnings.errorClientDoesNotExists();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
         });
      }
   }

   public void checkMyPersonalTrainer(){
      if(MainActivity.presenter.getUser() instanceof Client) {
         Query query = database.getReference("Users")
               .orderByChild("userId")
               .equalTo(((Client) MainActivity.presenter.getUser()).getPersonalTrainerId());

         query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               personalTrainers.clear();
               if (snapshot.exists()) {
                  for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                     PersonalTrainer personalTrainer = dataSnapshot.getValue(PersonalTrainer.class);
                     personalTrainers.add(personalTrainer);
                  }

                  MainActivity.presenter.setMyPersonalTrainer(personalTrainers.get(0));

                  if(MainActivity.presenter.getActualActivity() instanceof LoadingActivity){
                     ((LoadingActivity) MainActivity.presenter.getActualActivity()).finishedCharge();
                  }
               }else{
                  warnings.errorClientWithOutPersonalTrainer();

                  if(MainActivity.presenter.getActualActivity() instanceof LoadingActivity){
                     ((LoadingActivity) MainActivity.presenter.getActualActivity()).loadingError();
                  }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
         });
      }

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
            userList.clear();
            if(snapshot.exists()){
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                  User user = dataSnapshot.getValue(User.class);
                  userList.add(user);
               }

               Log.v(TAG, "Number of users with the id " + userId + " = " + userList.size());
               userList.get(0).setPassword(password);

               boolean verifyPasswordResult = false;
               try {
                  verifyPasswordResult = Encrypt.verifyPassword(userList.get(0));
               } catch (Exception e) {
                  Log.e(TAG, "Was not able to decrypt password");
                  e.printStackTrace();
               }

               if(verifyPasswordResult){
                  warnings.signUpSuccessfully();
                  if(activity instanceof LoginInterface){
                     ((LoginInterface) activity).setPresenterUser(userList.get(0));

                     if(userList.get(0).getUserType() == UserTypes.PERSONAL_TRAINER){
                        ((LoginInterface) activity).setPersonalTrainerExerciseNameList();
                     }

                     ((LoginInterface) activity).loginUserType(
                           userList.get(0).getUserType(),
                           true);
                  }
               }else {
                  warnings.wrongPasswordOrUserId();
               }
            }
            else {

               if (activity instanceof LoginInterface) {
                  ((LoginInterface) activity).loginUserType(
                        UserTypes.NONE,
                        false);
               }

               warnings.wrongPasswordOrUserId();
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   public void getExerciseNameList(final PersonalTrainer personalTrainer){
      //TO-do: Correct bug here
      Log.i(TAG, "getExerciseNameList function called");
      Query query = database.getReference("Exercise")
            .orderByChild("free")
            .equalTo(true);

      query.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.i(TAG, "Reading from database now");
            userList.clear();
            if(snapshot.exists()){
               Log.i(TAG, "Found some exercises");

               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                  personalTrainer.getExerciseNameList().add(dataSnapshot.child("name").getValue(String.class));
               }
            }
            else {
               Log.i(TAG, "Didn't found any exercise");
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   public void updateUser(User user){

   }

   public void updateExercise(Exercise exercise){

   }

   public void addNewExercise(Exercise exercise){

   }


   public boolean logout(){
      return false;
   }

}
