package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.content.Intent;
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
import java.util.Objects;

import static com.example.getmypersonaltrainer.MainActivity.presenter;

//

public class Model {
   private FirebaseDatabase database = null;
   private DatabaseReference databaseReference = null;
   private ChildEventListener childEventListener;
   private static final String TAG = "Model";
   private Warnings warnings = new Warnings();
   private ValidateInfo validateInfo = new ValidateInfo();
   private List<Client> clients = new ArrayList<Client>();
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
               if (snapshot.exists() && presenter.isGetInfoFromDatabase()) {
                  Log.i(TAG, "Exercise " + exercise.getExerciseId() + " already exist");
                  warnings.errorExerciseAlreadyExists();
                  if (presenter.getActualActivity() instanceof  FastError) {
                     ((FastError) presenter.getActualActivity()).loadingError();
                  }

               }
               else if(presenter.isGetInfoFromDatabase()){
                  if(addPublicExerciseToDatabase(exercise)) {
                     if (presenter.getActualActivity() instanceof FastError) {
                        ((FastError) presenter.getActualActivity()).finishedCharge();
                     }
                  }
               }
               presenter.setGetInfoFromDatabase(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
         });
      }
   }

   public void acceptInvitation(final InvitationMessage invitationMessage){
      databaseReference = database.getReference("Users");
      if(invitationMessage.getSenderUserType() == UserTypes.PERSONAL_TRAINER){
         databaseReference.child(invitationMessage.getReceiverId())
               .child("personalTrainerId")
               .setValue(invitationMessage.getSenderId());
      }
      else if (invitationMessage.getSenderUserType() == UserTypes.CLIENT){
         databaseReference.child(invitationMessage.getSenderId())
               .child("personalTrainerId")
               .setValue(invitationMessage.getReceiverId());
      }
   }

   public void declaimInvitation(final InvitationMessage invitationMessage){
      databaseReference = database.getReference("Users");
      presenter.getUser().getInvitationMessage().remove(invitationMessage.getSenderId());
      if(presenter.getUser() instanceof Client){
         updateClient((Client) presenter.getUser());
      } else if(presenter.getUser() instanceof PersonalTrainer){
         updatePersonalTrainer((PersonalTrainer) presenter.getUser());
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

   public void updatePersonalTrainer(final PersonalTrainer personalTrainer){
      databaseReference = database.getReference("Users");
      databaseReference.child(personalTrainer.getUserId()).setValue(personalTrainer);
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
            boolean userNotLogged = !presenter.isLogged();
            Log.i(TAG, "onDataChange from getIdFromDatabase called");
           if(snapshot.exists() && userNotLogged){
               Log.i(TAG, "User " + user.getUserId() + " already exist");
               warnings.errorUserIdAlreadyExists();
              if (presenter.getActualActivity() instanceof FastError) {
                 ((FastError) presenter.getActualActivity()).loadingError();
              }
            }
            else if (userNotLogged){
               if(validateInfo.checkId(user.getUserId())) {
                  if(addUserToDatabase(user)){
                     if (presenter.getActualActivity() instanceof FastError) {
                        ((FastError) presenter.getActualActivity()).finishedCharge();
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

   public void getAllPersonalTrainers(){
      if(presenter.getUser() instanceof Client) {
         Query query = database.getReference("Users")
               .orderByChild("userType")
               .equalTo(String.valueOf(UserTypes.PERSONAL_TRAINER));

         query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               presenter.getAllPersonalTrainers().clear();
               if (snapshot.exists() && presenter.isGetInfoFromDatabase()) {
                  if(presenter.getAllPersonalTrainers() == null) {
                     for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PersonalTrainer personalTrainer = dataSnapshot.getValue(PersonalTrainer.class);
                        presenter.getAllPersonalTrainers().add(personalTrainer);
                     }

                     if (presenter.getActualActivity() instanceof FastError) {
                        ((FastError) presenter.getActualActivity()).finishedCharge();
                     }
                  }
               }
               else if (presenter.isGetInfoFromDatabase()){
                  warnings.noPersonalTrainerAvailable();
                  if(presenter.getActualActivity() instanceof FastError){
                     ((FastError) presenter.getActualActivity()).loadingError();
                  }
               }

               presenter.setGetInfoFromDatabase(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
         });
      }


   }

   /**
    *  @author Everton Alves
    *  @param client
    *  @return boolean
    *  Check if the client has a personal trainer.
    *  If the client has a personal trainer, the personal trainer will be removed
    *  from the client and the function will return true, it returns false otherwise.
    */
   public boolean clientUnsubscribeFromPersonalTrainer(Client client){
      boolean clientHasPersonalTrainer = validateInfo.checkIfClientHasPersonalTrainer(client);
      if(clientHasPersonalTrainer){
         client.setPersonalTrainerId(null);
         updateClient(client);
         return true;
      }
      return false;
   }

   public boolean saveVoteInfo(int score){
      if(getValidateInfo().validScore(score)) {
         presenter.getMyPersonalTrainer().newVote(score);

         updatePersonalTrainer(presenter.getMyPersonalTrainer());

         if (presenter.getUser() instanceof Client) {
            ((Client) presenter.getUser()).setVoted(true);

            updateClient((Client) presenter.getUser());

            return true;
         }
      }

      warnings.invalidScoreRange();
      return false;
   }

   public void sendInvitationMessage(final InvitationMessage invitationMessage){
      Log.i(TAG, "Call sendInvitationMessage with the id = " + invitationMessage.getReceiverId());

      if(validateInfo.checkId(invitationMessage.getReceiverId())) {
         Query query = database.getReference("Users")
               .orderByChild("userId")
               .equalTo(invitationMessage.getReceiverId());

         query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Log.i(TAG, "onDataChange from sendInvitationToClient");
               if (snapshot.exists() && presenter.isGetInfoFromDatabase()) {
                  if(invitationMessage.getSenderUserType() == UserTypes.PERSONAL_TRAINER) {
                     clients.clear();
                     for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Client client = dataSnapshot.getValue(Client.class);
                        clients.add(client);
                     }

                     if(clients.get(0).getPersonalTrainerId() == null) {

                        clients.get(0).setReceivedInvitation(true);
                        clients.get(0).addNewInvitationMessage(invitationMessage);

                        updateClient(clients.get(0));

                        if(presenter.getActualActivity() instanceof FastError){
                           ((FastError) presenter.getActualActivity()).finishedCharge();
                        }
                           
                        warnings.invitationGotSend();

                     } else{
                        if(presenter.getActualActivity() instanceof FastError){
                           ((FastError) presenter.getActualActivity()).loadingError();
                        }

                        warnings.errorClientAlreadyHasAPersonalTrainer();
                     }

                  }
                  else if (invitationMessage.getSenderUserType() == UserTypes.CLIENT){
                     personalTrainers.clear();
                     for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PersonalTrainer personalTrainer = dataSnapshot.getValue(PersonalTrainer.class);
                        personalTrainers.add(personalTrainer);
                     }

                     if (presenter.getUser() instanceof Client) {

                        personalTrainers.get(0).setReceivedInvitation(true);
                        personalTrainers.get(0).addNewInvitationMessage(invitationMessage);

                        updatePersonalTrainer(personalTrainers.get(0));

                        if(presenter.getActualActivity() instanceof FastError){
                           ((FastError) presenter.getActualActivity()).finishedCharge();
                        }

                        warnings.invitationGotSend();
                     }
                  }
               }
               else if (presenter.isGetInfoFromDatabase()){
                  if(presenter.getActualActivity() instanceof FastError){
                     ((FastError) presenter.getActualActivity()).loadingError();
                  }
                  warnings.errorUserDoesNotExists();
               }
               presenter.setGetInfoFromDatabase(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
         });
      }
      else {
         if(presenter.getActualActivity() instanceof FastError){
            ((FastError) presenter.getActualActivity()).loadingError();
         }
         warnings.errorUserDoesNotExists();
      }
   }

   public void checkMyPersonalTrainer(){
      if(presenter.getUser() instanceof Client) {
         Query query = database.getReference("Users")
               .orderByChild("userId")
               .equalTo(((Client) presenter.getUser()).getPersonalTrainerId());

         query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Log.i(TAG, "checkMyPersonalTrainer on data change called");
               personalTrainers.clear();
               if (snapshot.exists() && presenter.isGetInfoFromDatabase()) {
                  for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                     PersonalTrainer personalTrainer = dataSnapshot.getValue(PersonalTrainer.class);
                     personalTrainers.add(personalTrainer);
                  }

                  presenter.setMyPersonalTrainer(personalTrainers.get(0));

                  if(presenter.getActualActivity() instanceof FastError){
                     ((FastError) presenter.getActualActivity()).finishedCharge();
                  }
               }
               else if (presenter.isGetInfoFromDatabase()){

                  if(presenter.getActualActivity() instanceof FastError){
                     ((FastError) presenter.getActualActivity()).loadingError();
                  }

                  warnings.errorClientWithOutPersonalTrainer();

               }
               presenter.setGetInfoFromDatabase(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
         });
      }

   }

   public void getPersonalTrainerInfo(){
      if(presenter.getUser() instanceof PersonalTrainerInterface){
         getExerciseNameList((PersonalTrainer) presenter.getUser());
         getClientList((PersonalTrainer) presenter.getUser());
      }
   }

   public void getClientList(final PersonalTrainer personalTrainer){
      Log.i(TAG, "getClientList function called");
      Query query = database.getReference("Users")
            .orderByChild("personalTrainerId")
            .equalTo(personalTrainer.getUserId());

      query.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.i(TAG, "getClientList onDataChangeCalled");

            boolean userNotLogged = !presenter.isLogged();
            if(snapshot.exists() && userNotLogged){
               Log.i(TAG, "Found some clients");
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                  personalTrainer.getClients().add(dataSnapshot.getValue(Client.class));
               }
            }
            else if (userNotLogged){
               Log.i(TAG, "Found 0 client");
            }
            if(presenter.getActualActivity() instanceof FastError && userNotLogged){
               presenter.setLogged(true);
               ((FastError) presenter.getActualActivity()).finishedCharge();
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
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
            Log.i(TAG, "onDataChange checkLogin called");
            userList.clear();
            boolean isNotLogged = !presenter.isLogged();
            if(presenter.getActualActivity() instanceof PersonalTrainerMainActivity){
               ((PersonalTrainerMainActivity) presenter.getActualActivity()).uploadAllAdapter();
            }

            if(snapshot.exists() && isNotLogged){
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

                     ((LoginInterface) activity).loginUserType(
                           userList.get(0).getUserType(),
                           true);
                  }
               }else {
                  warnings.wrongPasswordOrUserId();
               }
            }
            else if(isNotLogged){

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
      Log.i(TAG, "getExerciseNameList function called");
      Query query = database.getReference("Exercise")
            .orderByChild("free")
            .equalTo(true);

      query.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.i(TAG, "onDataChange from getExerciseNameList called");
            if(snapshot.exists() && presenter.isGetInfoFromDatabase()){
               Log.i(TAG, "Found some exercises");

               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                  personalTrainer.getExerciseNameList().add(dataSnapshot.child("name").getValue(String.class));
               }
            }
            else if(presenter.isGetInfoFromDatabase()){
               Log.i(TAG, "Didn't found any exercise");
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
   public void updateClientExercise(Exercise exercise, String clientId){
      updateExercise(exercise, clientId);

      if(presenter.getUser() instanceof PersonalTrainer){
         boolean personalTrainersDoNotHaveTheNewExercise =  !validateInfo
               .checkIfPersonalTrainerHasGivenExercise(
                     (PersonalTrainer) presenter.getUser(),
                     exercise
               );

         Exercise personalTrainerExercise = presenter.getUser().getExerciseList().get(exercise.getName());
         if(personalTrainerExercise != null) {
            boolean personalTrainerExerciseGotChanged = validateInfo.checkIfPersonalTrainerExerciseGotChanged(
                  personalTrainerExercise, exercise);

            if (personalTrainersDoNotHaveTheNewExercise || personalTrainerExerciseGotChanged) {
               savePersonalPrivateExercise(exercise);
            }
         }else{
            savePersonalPrivateExercise(exercise);
         }
      }
   }

   public void eraseClientExercise(Client client, String exerciseId){
      client.getExerciseList().remove(exerciseId);
      updateClient(client);
   }

   public void updateExercise(Exercise exercise, String userId){
      databaseReference = database.getReference("Users");
      databaseReference.child(userId)
            .child("exerciseList")
            .child(exercise.getExerciseId()).setValue(exercise);
   }

   /**
    * @author Everton Alves
    * @param client
    * Remove All Client Exercise from the database.
    */
   public void deleteClientExercises(Client client){
      client.getExerciseList().clear();
      updateClient(client);
   }

   public boolean saveClientExercise(Client client, Exercise exercise){
      Log.i(TAG, "save Client Exercise");
      databaseReference = database.getReference("Users");
      Log.i(TAG, "Creating Exercise ID");
      String newId = exercise.getName() + client.getExerciseList().size();
      exercise.setExerciseId(newId);
      client.getExerciseList().put(newId, exercise);
      boolean validId = validateInfo.checkId(exercise.getName());
      if(validId) {
         updateClient(client);
         Log.i(TAG, "Client Updated with new Exercise");
         //warnings.createdNewExerciseSuccessfully();
         return true;
      } else{
         //warnings.invalidExerciseName();
         Log.i(TAG, "Invalid Exercise Name");
         if(presenter.getActualActivity() instanceof FastError){
            ((FastError) presenter.getActualActivity()).loadingError();
         }
      }

      return false;
   }

   public void savePersonalPrivateExercise(Exercise exercise){
      Log.i(TAG, "save Personal Trainer Exercise");

      Exercise newExercise = new Exercise(exercise.getName(),
            exercise.getName(),
            exercise.getEmphasis(),
            exercise.getVideoLink(),
            exercise.getObservations(),
            false);
      if (presenter.getUser() instanceof PersonalTrainer) {
         presenter.getUser().getExerciseList().put(newExercise.getName(), newExercise);
         Log.i(TAG, "Personal trainer added exercises : " + presenter.getUser().getExerciseList());

         databaseReference = database.getReference("Users");
         databaseReference.child(presenter.getUser().getUserId())
               .child("exerciseList")
               .child(newExercise.getName()).setValue(newExercise);

      }
   }

   public void addClientExercise(final Client client, final Exercise exercise){
      Log.i(TAG, "Call addClientExercise with the name = " + exercise.getName());
      boolean goodResult = saveClientExercise(client, exercise);
      if(goodResult) {
         boolean hasGivenExercise =
               validateInfo.checkIfPersonalTrainerHasGivenExercise((PersonalTrainer) presenter.getUser(), exercise);
         if(hasGivenExercise == false){
            savePersonalPrivateExercise(exercise);
         }

         if(presenter.getActualActivity() instanceof FastError){
            ((FastError) presenter.getActualActivity()).finishedCharge();
         }
      }
      else  if(presenter.getActualActivity() instanceof FastError){
         ((FastError) presenter.getActualActivity()).loadingError();
      }
   }
}
