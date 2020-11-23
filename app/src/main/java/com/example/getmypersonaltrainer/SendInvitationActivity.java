package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SendInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invitation);
        MainActivity.presenter.setActualActivity(this);
    }

    public void sendInvitation(View view){
        EditText editText = findViewById(R.id.edit_text_client_id_send_invitation);
        String clientId = editText.getText().toString();

        if(clientId.length() > 3) {
            MainActivity.presenter.getModel().sendInvitationToClient(clientId);
        } else {
            MainActivity.presenter.getModel().getWarnings().errorClientDoesNotExists();
        }

    }
}