package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SendInvitationActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invitation);
        MainActivity.presenter.setActualActivity(this);
        MainActivity.presenter.setGoingTo(SendInvitationActivity.class);
        MainActivity.presenter.setGoBack(SendInvitationActivity.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.send_invitation_title);
    }

    public void sendInvitation(View view){
        EditText editText = findViewById(R.id.edit_text_client_id_send_invitation);
        String clientId = editText.getText().toString();
        boolean validClientId = MainActivity.presenter.getModel().getValidateInfo().checkId(clientId);

        if(validClientId) {
            InvitationMessage invitationMessage = new InvitationMessage(
                  MainActivity.presenter.getUser().getUserId(),
                  clientId,
                  MainActivity.presenter.getUser().getName(),
                  MainActivity.presenter.getUser().getUserType()
            );
            MainActivity.presenter.setGetInfoFromDatabase(true);
            MainActivity.presenter.getModel().sendInvitationMessage(invitationMessage);

            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);

        } else {
            MainActivity.presenter.getModel().getWarnings().errorClientDoesNotExists();
        }

    }
}