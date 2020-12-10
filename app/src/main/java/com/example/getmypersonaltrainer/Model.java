package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static com.example.getmypersonaltrainer.MainActivity.presenter;

public class Model {
   private final FirebaseDatabase database;
   private DatabaseReference databaseReference;
   private static final String TAG = "Model";
   private final Warnings warnings = new Warnings();
   private final ValidateInfo validateInfo = new ValidateInfo();
   private final List<Client> clients = new ArrayList<>();
   private final List<PersonalTrainer> personalTrainers = new ArrayList<>();
   List<User> userList = new ArrayList<>();

   public Model(){
      //This line is for FirebaseDatabase to work faster
      FirebaseDatabase.getInstance().setPersistenceEnabled(true);

      database = FirebaseDatabase.getInstance();
      databaseReference = database.getReference();
   }

   public FirebaseDatabase getDatabase() {
      return database;
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
      boolean userHasValidPassword = validateInfo.password(user.getPassword());
      if(userHasValidPassword) {
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

   public FirebaseRecyclerOptions<Client> getClientListRecyclerOptions(){
      if(presenter.getUser() instanceof PersonalTrainer) {
         String personalId = presenter.getUser().getUserId();
         Query query = database.getReference("Users")
                 .orderByChild("personalTrainerId")
                 .equalTo(personalId);

         return new FirebaseRecyclerOptions.Builder<Client>()
                 .setQuery(query, Client.class)
         .build();
      }

      return null;
   }

   public FirebaseRecyclerOptions<InvitationMessage>
      getInvitationList(){
      Query query = database.getReference("Users")
              .child(presenter.getUser().getUserId())
              .child("invitationMessage");

      return new FirebaseRecyclerOptions.Builder<InvitationMessage>()
      .setQuery(query, InvitationMessage.class)
      .build();
   }

   public FirebaseRecyclerOptions<Exercise>
      getClientExerciseList(Client client, DayOfWeek dayOfWeek)
   {
      Query query = database.getReference("Users").child(client.getUserId())
              .child("exerciseList")
              .orderByChild("daysOfWeek")
              .equalTo(String.valueOf(dayOfWeek));

      return new FirebaseRecyclerOptions.Builder<Exercise>()
      .setQuery(query, Exercise.class)
      .build();
   }

   public FirebaseRecyclerOptions<PersonalTrainer> getAllPersonalTrainers(){
      Query query = database.getReference("Users")
              .orderByChild("userType")
              .equalTo(String.valueOf(UserTypes.PERSONAL_TRAINER));

      return new FirebaseRecyclerOptions.Builder<PersonalTrainer>()
              .setQuery(query, PersonalTrainer.class)
              .build();
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
                  if (presenter.getActualActivity() instanceof  FastError) {
                     ((FastError) presenter.getActualActivity()).loadingError();
                  }
                  warnings.errorExerciseAlreadyExists();

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
      String trainerId = null;
      if(invitationMessage.getSenderUserType() == UserTypes.PERSONAL_TRAINER){
         trainerId = invitationMessage.getSenderId();
         databaseReference.child(invitationMessage.getReceiverId())
               .child("personalTrainerId")
               .setValue(trainerId);
      }
      else if (invitationMessage.getSenderUserType() == UserTypes.CLIENT){
         trainerId = invitationMessage.getReceiverId();
         databaseReference.child(invitationMessage.getSenderId())
               .child("personalTrainerId")
               .setValue(trainerId);
      }

      if(presenter.getUser() instanceof Client && trainerId != null){
         ((Client) presenter.getUser()).setPersonalTrainerId(trainerId);
      }

      warnings.acceptedInvitation();

      eraseInvitation(invitationMessage);
   }

   public void eraseInvitation(final InvitationMessage invitationMessage){
      presenter.getUser().getInvitationMessage().remove(invitationMessage.getSenderId());
      if(presenter.getUser() instanceof Client){
         presenter.getModel().updateClient((Client) presenter.getUser());
      } else if(presenter.getUser() instanceof PersonalTrainer){
         presenter.getModel().updatePersonalTrainer((PersonalTrainer) presenter.getUser());
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

      eraseInvitation(invitationMessage);
   }

   private boolean addPublicExerciseToDatabase(final Exercise exercise){
      databaseReference = database.getReference("Exercise");
      boolean validId = validateInfo.checkId(exercise.getExerciseId());

      if(validId) {
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
            boolean sendingInfo = presenter.isGetInfoFromDatabase();
            Log.i(TAG, "onDataChange from getIdFromDatabase called");
           if(snapshot.exists() && userNotLogged && sendingInfo){
               Log.i(TAG, "User " + user.getUserId() + " already exist");
              if (presenter.getActualActivity() instanceof FastError) {
                 ((FastError) presenter.getActualActivity()).loadingError();
              }
              warnings.errorUserIdAlreadyExists();
            }
            else if (userNotLogged && sendingInfo){
               if(validateInfo.checkId(user.getUserId())) {
                  if(addUserToDatabase(user)){
                     if (presenter.getActualActivity() instanceof FastError) {
                        ((FastError) presenter.getActualActivity()).finishedCharge();
                     }
                  }
               }
            }
           else if(presenter.getActualActivity() instanceof LoadingActivity){
              ((LoadingActivity) presenter.getActualActivity()).stuckAtLoadingActivity();
           }
            presenter.setGetInfoFromDatabase(false);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   /**
    *  @author Everton Alves
    *  @param client Client class
    */
   public void clientUnsubscribeFromPersonalTrainer(Client client){
      boolean clientHasPersonalTrainer = validateInfo.checkIfClientHasPersonalTrainer(client);
      if(clientHasPersonalTrainer){
         client.setPersonalTrainerId(null);
         updateClient(client);

         deleteClientExercises(client);
      }
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

                     if(clients.get(0).getUserType() == UserTypes.CLIENT){
                        clients.get(0).setReceivedInvitation(true);
                        clients.get(0).addNewInvitationMessage(invitationMessage);

                        updateClient(clients.get(0));

                        if(presenter.getActualActivity() instanceof FastError){
                           ((FastError) presenter.getActualActivity()).finishedCharge();
                        }
                           
                        warnings.invitationGotSend();
                     }else{
                        if(presenter.getActualActivity() instanceof FastError){
                           ((FastError) presenter.getActualActivity()).finishedCharge();
                        }                        
                        
                        warnings.errorUserDoesNotExists();
                     }

                  }
                  else if (invitationMessage.getSenderUserType() == UserTypes.CLIENT &&
                          presenter.isGetInfoFromDatabase())
                  {

                     personalTrainers.clear();
                     for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PersonalTrainer personalTrainer = dataSnapshot.getValue(PersonalTrainer.class);
                        personalTrainers.add(personalTrainer);
                     }

                     if (personalTrainers.get(0) != null &&
                             personalTrainers.get(0).getUserType() == UserTypes.PERSONAL_TRAINER) {
                        personalTrainers.get(0).setReceivedInvitation(true);
                        personalTrainers.get(0).addNewInvitationMessage(invitationMessage);

                        updatePersonalTrainer(personalTrainers.get(0));

                        if (presenter.getActualActivity() instanceof FastError) {
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

               else if(presenter.getActualActivity() instanceof LoadingActivity){
                  ((LoadingActivity) presenter.getActualActivity()).stuckAtLoadingActivity();
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

               else if(presenter.getActualActivity() instanceof LoadingActivity){
                  ((LoadingActivity) presenter.getActualActivity()).stuckAtLoadingActivity();
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
         presenter.setGetInfoFromDatabase(true);
         getExerciseNameList((PersonalTrainer) presenter.getUser());
         getClientList((PersonalTrainer) presenter.getUser());
      }
   }

   public void getClientList(final PersonalTrainer personalTrainer){
      Log.i(TAG, "getClientList function called");
      Query query = presenter.getModel().getDatabase().getReference("Users")
              .orderByChild("personalTrainerId")
              .equalTo(personalTrainer.getUserId());

      query.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.i(TAG, "getClientList onDataChangeCalled");

            boolean userNotLogged = !presenter.isLogged();
            if(snapshot.exists() && userNotLogged){
               Log.i(TAG, "Found some clients");
               clients.clear();
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                  Client client = dataSnapshot.getValue(Client.class);
                  clients.add(client);
               }
               presenter.setClientList(clients);
            }
            else if (userNotLogged){
               Log.i(TAG, "Found 0 client");
            }
            if(presenter.getActualActivity() instanceof FastError && userNotLogged){
               presenter.setLogged(true);
               ((FastError) presenter.getActualActivity()).finishedCharge();
            }
            else if(presenter.getActualActivity() instanceof LoadingActivity){
               ((LoadingActivity) presenter.getActualActivity()).stuckAtLoadingActivity();
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });
   }

   public void checkLogin(final String userId , final String password, final Activity activity){
      CheckLogin checkLogin = new CheckLogin(userId, password, activity);
      checkLogin.run();
   }

   public void getExerciseNameList(final PersonalTrainer personalTrainer){
      Query query = presenter.getModel().getDatabase().getReference("Exercise")
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
                  Exercise exercise = dataSnapshot.getValue(Exercise.class);
                  if(exercise != null) {
                     presenter.getFreeExerciseList().put(exercise.getExerciseId(), exercise);
                  }
               }
            }
            else if(presenter.isGetInfoFromDatabase()){
               Log.i(TAG, "Didn't found any exercise");
            }
            presenter.setGetInfoFromDatabase(false);
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
    * @param client Client class
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
         return true;
      } else{
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
         boolean doNotHasGivenExercise =
               !validateInfo.checkIfPersonalTrainerHasGivenExercise((PersonalTrainer) presenter.getUser(), exercise);
         if(doNotHasGivenExercise){
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

   public void resetValues() {
      clients.clear();
      personalTrainers.clear();
      userList.clear();
   }
}
