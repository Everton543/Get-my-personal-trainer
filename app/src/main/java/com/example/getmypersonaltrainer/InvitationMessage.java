package com.example.getmypersonaltrainer;

public class InvitationMessage {
   private String senderId = null;
   private String receiverId = null;
   private String senderName = null;
   private UserTypes senderUserType = null;

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

}
