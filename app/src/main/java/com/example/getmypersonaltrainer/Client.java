package com.example.getmypersonaltrainer;

import android.util.Log;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Everton Alves
 * All the information needed for a Client user.
 */
public class Client implements UserInterface, ClientInterface{
   private UserTypes userType;
   private String password;
   private String name;
   private String userId;
   private String phone;
   private String birthDate;
   private float bodyMass;
   private float size;
   private Map<String, Exercise> exerciseList = new HashMap<>();
   private String hashedPassword;
   private String salt;
   private Map<String, InvitationMessage> invitationMessages = new HashMap<>();
   private boolean receivedInvitation = false;
   private String personalTrainerId;
   private boolean voted = false;
   private static final String TAG = "Client";

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

   /**
    * @author Cheri Hansen
    * @return age
    */
   public int takeAge() {
      int age = 969; // Default to the age of Methusaleh

      // Validate birthDate...
      boolean error = false;
      String birthDate = getBirthDate();
      if ((birthDate==null)||(birthDate.length()<10)||
              (birthDate.charAt(4)!='/')||(birthDate.charAt(7)!='/')) {
         error = true;
         Log.i(TAG, "getAge() - birthDate null, insufficient length, or no slashes.");
      }

      // Validate that there are only numbers in the right places...
      String[] ymd = { null, null, null };
      if (!error) {
         // Need to validate that we only have numbers
         ymd[0] = birthDate.substring(0, 4);
         ymd[1] = birthDate.substring(5, 7);
         ymd[2] = birthDate.substring(8,10);
         String test = ymd[0] + ymd[1] + ymd[2];
         String legal = "0123456789";
         for (int i=0;i<test.length();++i) {
            boolean found = false;
            for (int j=0;j<legal.length();++j)
               if (test.charAt(i)==legal.charAt(j))
                  found = true;
            if (!found)
               error = true;
         }
         if (error)
            Log.i(TAG, "getAge() - birthDate doesn't have all numbers formatted correctly.");
      }

      // Parse the Strings into integers...
      int[] birthday = { 0, 0, 0 };
      if (!error) {
         try {
            birthday[0] = Integer.parseInt(ymd[0]);
            birthday[1] = Integer.parseInt(ymd[1]);
            birthday[2] = Integer.parseInt(ymd[2]);
         }
         catch(NumberFormatException e) {
            error = true;
            Log.i(TAG, "getAge() - parseInt error: " + e.getMessage());
         }
      }

      // Get today's date and test validity of parsed date.
      GregorianCalendar gc = new GregorianCalendar();
      int year = gc.get(GregorianCalendar.YEAR);
      int month = gc.get(GregorianCalendar.MONTH) + 1;
      int day = gc.get(GregorianCalendar.DAY_OF_MONTH);
      if (!error) {
         // Test valid dates...
         if (birthday[0] < 1900) error = true;
         if ((birthday[1] < 1) || (birthday[2] < 1)) error = true;
         if ((birthday[1] > 12) || (birthday[2] > 31)) error = true;
         if (birthday[0] > year) error = true;
         if ((birthday[0] == year) && (birthday[1] > month)) error = true;
         if ((birthday[0] == year) && (birthday[1] == month) && (birthday[2] >= day)) error = true;

         // Check specific month lengths...
         switch (birthday[1]) {
            case 2: // Feb
               if (((birthday[0] % 4) == 0) && (birthday[2] > 29)) error = true;
               if (((birthday[0] % 4) > 0) && (birthday[2] > 28)) error =true;
               break;
            case 4: // Mar
            case 6: // Jun
            case 9: // Sep
            case 11: // Nov
               if (birthday[2] > 30) error = true;
            default:
               break;
         }
         if (error)
            Log.i(TAG, "getAge() - birthDate is an illegal date!");
      }

      // If there are no errors, we can now calculate the age.
      if (!error) {
         age = year - birthday[0];
         if (month<birthday[1]) {
            --age;
         }
         if ((month==birthday[1])&&(day<birthday[2])) {
            --age;
         }
      }

      // Logcat error that the age will be 969!
      if (error)
         Log.i(TAG, "getAge() - He must be Methusaleh as his age is 969!");

      return age;
   }

}
