package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class ReadInvitationMessageActivity extends AppCompatActivity {

   private RecyclerView invitationsView;
   private InvitationListViewAdapter invitationAdapter;
   private static final String TAG = "READ_INVITATION";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_read_invitation_message);

      MainActivity.presenter.getUser().setReceivedInvitation(false);

      if(MainActivity.presenter.getUser().getInvitationMessage() != null) {
         invitationsView = findViewById(R.id.recycler_view_invitation_messages);

         Map<String, InvitationMessage> invitationList =
               MainActivity.presenter.getUser().getInvitationMessage();

         invitationAdapter = new InvitationListViewAdapter(this, invitationList);

         invitationsView.setAdapter(invitationAdapter);
         invitationsView.setLayoutManager(new LinearLayoutManager(this));

      } else{
         TextView textAlert = findViewById(R.id.text_no_invitation_read_invitation);
         textAlert.setVisibility(View.VISIBLE);
      }
   }


   public void alertDialog(final int position) {
      AlertDialog.Builder dialog=new AlertDialog.Builder(this);
      dialog.setMessage("Continue will change your personal trainer. Continue?");
      dialog.setTitle("You have a personal trainer");
      dialog.setPositiveButton("YES",
            new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog,
                                   int which) {
                  Log.i(TAG, "Yes pressed");
                  MainActivity.presenter.getModel().acceptInvitation(invitationAdapter
                        .getInvitationList().get(position));
                  eraseInvitation(position);
               }
            });
      dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            Log.i(TAG, "cancel pressed");
         }
      });
      AlertDialog alertDialog=dialog.create();
      alertDialog.show();
   }

   public void eraseInvitation(final int position){
      InvitationMessage invitationMessage =  invitationAdapter.getInvitationList().get(position);
      MainActivity.presenter.getModel().declaimInvitation(invitationMessage);

      invitationAdapter.getInvitationList().remove(position);
      invitationAdapter.notifyItemRemoved(position);
   }
}