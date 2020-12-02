package com.example.getmypersonaltrainer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvitationListViewAdapter extends RecyclerView.Adapter<
      InvitationListViewAdapter.InvitationListViewHolder> {

   List<InvitationMessage> invitationList = new ArrayList<InvitationMessage>();
   Context context;

   public class InvitationListViewHolder extends RecyclerView.ViewHolder {
      TextView invitationText;
      Button acceptInvitation;
      Button declaimInvitation;

      public InvitationListViewHolder(@NonNull View itemView) {
         super(itemView);
         invitationText = itemView.findViewById(R.id.text_invitation_message);
         acceptInvitation = itemView.findViewById(R.id.button_accept_invitation_message);
         declaimInvitation = itemView.findViewById(R.id.button_declaim_invitation_message);
      }
   }

   public InvitationListViewAdapter(Context context, Map<String, InvitationMessage> invitationList){
      this.context = context;

      for (Map.Entry me : invitationList.entrySet()) {
         this.invitationList.add((InvitationMessage) me.getValue());
      }
   }


   public List<InvitationMessage> getInvitationList() {
      return invitationList;
   }

   public void setInvitationList(List<InvitationMessage> invitationList) {
      this.invitationList = invitationList;
   }


   @NonNull
   @Override
   public InvitationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View view = inflater.inflate(R.layout.invitation_message, parent, false);
      return new InvitationListViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull final InvitationListViewHolder holder, final int position) {
      String invitationMessage = invitationList.get(position).getInvitationMessage();
      holder.invitationText.setText(invitationMessage);

      holder.acceptInvitation.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if(MainActivity.presenter.getUser() instanceof Client){
               boolean clientAlreadyHasPersonalTrainer =
                     ((Client) MainActivity.presenter.getUser()).getPersonalTrainerId() != null;

               if(clientAlreadyHasPersonalTrainer){
                  if(context instanceof ReadInvitationMessageActivity){
                     ((ReadInvitationMessageActivity) context).alertDialog(position);
                  }
               }else{
                  MainActivity.presenter.getModel().acceptInvitation(invitationList.get(position));
                  if(context instanceof ReadInvitationMessageActivity){
                     ((ReadInvitationMessageActivity) context).eraseInvitation(position);
                  }
               }

            }else{
               MainActivity.presenter.getModel().acceptInvitation(invitationList.get(position));
               if(context instanceof ReadInvitationMessageActivity){
                  ((ReadInvitationMessageActivity) context).eraseInvitation(position);
               }
            }
         }
      });

      holder.declaimInvitation.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if(context instanceof ReadInvitationMessageActivity){
               ((ReadInvitationMessageActivity) context).declaimInvitation(position);
            }
         }
      });
   }

   @Override
   public int getItemCount() {
      return invitationList.size();
   }

}
