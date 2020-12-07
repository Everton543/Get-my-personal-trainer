package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvitationListViewAdapter extends FirebaseRecyclerAdapter<InvitationMessage,
      InvitationListViewAdapter.InvitationListViewHolder> {

   /**
    * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
    * {@link FirebaseRecyclerOptions} for configuration options.
    *
    * @param options
    */
   public InvitationListViewAdapter(@NonNull FirebaseRecyclerOptions<InvitationMessage> options) {
      super(options);
   }

   public static class InvitationListViewHolder extends RecyclerView.ViewHolder {
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

   @NonNull
   @Override
   public InvitationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      View view = inflater.inflate(R.layout.invitation_message, parent, false);
      return new InvitationListViewHolder(view);
   }

   @Override
   protected void onBindViewHolder(@NonNull InvitationListViewHolder holder,
                                   final int position,
                                   @NonNull final InvitationMessage model) {

      String invitationMessage = model.getInvitationMessage();
      holder.invitationText.setText(invitationMessage);

      holder.acceptInvitation.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Activity activity = MainActivity.presenter.getActualActivity();
            if(MainActivity.presenter.getUser() instanceof Client){
               boolean clientAlreadyHasPersonalTrainer =
                       ((Client) MainActivity
                               .presenter
                               .getUser()).getPersonalTrainerId() != null;

               if(clientAlreadyHasPersonalTrainer){
                  if(activity instanceof ReadInvitationMessageActivity){
                     ((ReadInvitationMessageActivity)
                             activity).alertDialog(model);
                  }
               }else{
                  MainActivity.presenter.getModel().acceptInvitation(model);
               }

            }else{
               MainActivity.presenter.getModel().acceptInvitation(model);
            }
         }
      });

      holder.declaimInvitation.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if(MainActivity.presenter.getActualActivity() instanceof ReadInvitationMessageActivity){
               ((ReadInvitationMessageActivity)
                       MainActivity.presenter.getActualActivity()).declaimInvitation(model);
            }
         }
      });
   }

}
