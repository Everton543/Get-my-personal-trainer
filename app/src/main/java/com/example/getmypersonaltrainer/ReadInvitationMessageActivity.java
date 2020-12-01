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

   RecyclerView invitationsView;
   InvitationListViewAdapter invitationAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_read_invitation_message);

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


   public void alertDialog() {
      AlertDialog.Builder dialog=new AlertDialog.Builder(this);
      dialog.setMessage("Continue will change your personal trainer. Continue?");
      dialog.setTitle("You have a personal trainer");
      dialog.setPositiveButton("YES",
            new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog,
                                   int which) {
                  Log.i("READ_INVITATION", "Yes pressed");
                  //CREATE MODEL FUNCTION TO ADD THE PERSONAL TRAINER TO THE CLIENT
               }
            });
      dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            Log.i("READ_INVITATION", "cancel pressed");
         }
      });
      AlertDialog alertDialog=dialog.create();
      alertDialog.show();
   }

   public void declaimInvitation(){

   }
}