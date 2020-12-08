package com.example.getmypersonaltrainer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ReadInvitationMessageActivity extends AppCompatActivity {

   private RecyclerView invitationsView;
   private InvitationListViewAdapter invitationAdapter;
   private static final String TAG = "READ_INVITATION";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_read_invitation_message);
      MainActivity.presenter.setActualActivity(this);

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      ActionBar actionBar = getSupportActionBar();
      assert actionBar != null;
      actionBar.setTitle(R.string.read_invitation_message_title);

      MainActivity.presenter.getUser().setReceivedInvitation(false);

      if(MainActivity.presenter.getUser().getInvitationMessage() != null &&
      MainActivity.presenter.getUser().getInvitationMessage().size() > 0) {
         invitationsView = findViewById(R.id.recycler_view_invitation_messages);

         invitationAdapter = new InvitationListViewAdapter(
                 MainActivity.presenter.getModel().getInvitationList()
         );

         invitationsView.setAdapter(invitationAdapter);
         invitationsView.setLayoutManager(new LinearLayoutManager(this));

      } else{
         TextView textAlert = findViewById(R.id.text_no_invitation_read_invitation);
         textAlert.setVisibility(View.VISIBLE);
      }
   }

   @Nullable
   @Override
   public Intent getParentActivityIntent() {
      Intent intent = null;
      if(MainActivity.presenter.getUser() instanceof Client){
         intent = new Intent(this, ClientMainActivity.class);
      }else if(MainActivity.presenter.getUser() instanceof PersonalTrainer){
         intent = new Intent(this, PersonalTrainerMainActivity.class);
      }
      return intent;
   }

   @Override
   protected void onStart() {
      super.onStart();
      if(invitationAdapter != null) {
         invitationAdapter.startListening();
      }
   }

   @Override
   protected void onStop() {
      super.onStop();
      if(invitationAdapter != null) {
         invitationAdapter.stopListening();
      }
   }

   public void alertDialog(final InvitationMessage invitationMessage) {
      AlertDialog.Builder dialog=new AlertDialog.Builder(this);
      dialog.setMessage("Continue will change your personal trainer. Continue?");
      dialog.setTitle("You have a personal trainer");
      dialog.setPositiveButton("YES",
            new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog,
                                   int which) {
                  Log.i(TAG, "Yes pressed");
                  MainActivity.presenter.getModel().acceptInvitation(invitationMessage);
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


   public void declaimInvitation(InvitationMessage invitationMessage){
      MainActivity.presenter.getModel().declaimInvitation(invitationMessage);
   }
}