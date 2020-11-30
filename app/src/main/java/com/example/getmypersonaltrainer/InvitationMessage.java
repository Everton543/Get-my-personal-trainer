package com.example.getmypersonaltrainer;

import static com.example.getmypersonaltrainer.MainActivity.presenter;

public class InvitationMessage {
   private String senderId = null;
   private String receiverId = null;
   private String senderName = null;
   private UserTypes senderUserType = null;
   private String phone = null;



   public InvitationMessage(String senderId,
                            String receiverId,
                            String senderName,
                            UserTypes senderUserType) {
      this.senderId = senderId;
      this.receiverId = receiverId;
      this.senderName = senderName;
      this.senderUserType = senderUserType;
   }

   public InvitationMessage() {
   }

   public InvitationMessage(String senderId, String receiverId, String senderName, UserTypes senderUserType, String phone) {
      this.senderId = senderId;
      this.receiverId = receiverId;
      this.senderName = senderName;
      this.senderUserType = senderUserType;
      this.phone = phone;
   }

   public String getSenderId() {
      return senderId;
   }

   public void setSenderId(String senderId) {
      this.senderId = senderId;
   }

   public String getReceiverId() {
      return receiverId;
   }

   public void setReceiverId(String receiverId) {
      this.receiverId = receiverId;
   }

   public String getSenderName() {
      return senderName;
   }

   public void setSenderName(String senderName) {
      this.senderName = senderName;
   }

   public UserTypes getSenderUserType() {
      return senderUserType;
   }

   public void setSenderUserType(UserTypes senderUserType) {
      this.senderUserType = senderUserType;
   }

   public String getInvitationMessage(){
      String invitationMessage = null;
      if(senderUserType == UserTypes.PERSONAL_TRAINER){
         invitationMessage =  senderName;
         invitationMessage += " that has the ID: " + senderId;
         invitationMessage += " wants to be your personal trainer.";

      } else if(senderUserType == UserTypes.CLIENT){
         invitationMessage =  senderName;
         invitationMessage += " that has the ID: " + senderId;
         invitationMessage += " wants you as a personal trainer.";
         invitationMessage += " clients phone: " + phone;
      }

      return invitationMessage;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }
}
