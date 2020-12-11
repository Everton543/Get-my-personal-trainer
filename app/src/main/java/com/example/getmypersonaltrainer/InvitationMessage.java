package com.example.getmypersonaltrainer;

public class InvitationMessage {
   private String senderId = null;
   private String receiverId = null;
   private String senderName = null;
   private UserTypes senderUserType = null;
   private String phone = null;
   private String invitationMessage = null;

   public InvitationMessage(String senderId,
                            String receiverId,
                            String senderName,
                            UserTypes senderUserType) {
      this.senderId = senderId;
      this.receiverId = receiverId;
      this.senderName = senderName;
      this.senderUserType = senderUserType;

      createInvitationMessage();
   }

   public InvitationMessage(String senderId,
                            String receiverId,UserTypes senderUserType){
      this.senderId = senderId;
      this.receiverId = receiverId;
      this.senderUserType = senderUserType;

      createMessageReply();
   }

   private void createMessageReply(){
      invitationMessage = "The personal trainer";
      invitationMessage += " that has the ID: " + senderId;
      invitationMessage += " accepted your invitation for be a member of his group.";
      invitationMessage += " Do you want to change from your personal trainer to this one?";
   }


   public InvitationMessage() { }

   public InvitationMessage(String senderId, String receiverId, String senderName, UserTypes senderUserType, String phone) {
      this.senderId = senderId;
      this.receiverId = receiverId;
      this.senderName = senderName;
      this.senderUserType = senderUserType;
      this.phone = phone;

      createInvitationMessage();
   }

   public void setInvitationMessage(String invitationMessage) {
      this.invitationMessage = invitationMessage;
   }

   public void setSenderId(String senderId) {
      this.senderId = senderId;
   }

   public void setReceiverId(String receiverId) {
      this.receiverId = receiverId;
   }

   public void setSenderName(String senderName) {
      this.senderName = senderName;
   }

   public void setSenderUserType(UserTypes senderUserType) {
      this.senderUserType = senderUserType;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }


   public String getSenderId() {
      return senderId;
   }

   public String getReceiverId() {
      return receiverId;
   }

   public String getSenderName() {
      return senderName;
   }

   public UserTypes getSenderUserType() {
      return senderUserType;
   }

   public String getInvitationMessage(){
      return invitationMessage;
   }

   public String getPhone() {
      return phone;
   }

   public void createInvitationMessage(){
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
   }

}
