package com.example.getmypersonaltrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
        MainActivity.presenter.setActualActivity(this);

        if(MainActivity.presenter.getUser() instanceof Client){
            if(MainActivity.presenter.getUser().getInvitationMessage() != null){
                TextView textView = findViewById(R.id.text_invitation_message_client_main);
                textView.setText(MainActivity.presenter.getUser().getInvitationMessage());
                textView.setVisibility(View.VISIBLE);

                Button confirmButton = findViewById(R.id.button_confirm_client_main);
                confirmButton.setVisibility(View.VISIBLE);
                confirmButton.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        MainActivity.presenter.getUser().setReceivedInvitation(true);
                        MainActivity.presenter.getUser().setInvitationMessage(null);

                        MainActivity.presenter.getModel().updateClient((Client) MainActivity.presenter.getUser());
                    }
                });

                Button declineButton = findViewById(R.id.button_decline_client_main);
                declineButton.setVisibility(View.VISIBLE);
                declineButton.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        MainActivity.presenter.getUser().setReceivedInvitation(false);
                        MainActivity.presenter.getUser().setInvitationMessage(null);
                        ((Client) MainActivity.presenter.getUser()).setPersonalTrainerId(null);
                        MainActivity.presenter.getModel().updateClient((Client) MainActivity.presenter.getUser());
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.client_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.client_menu_item_see_personal_trainer:
                MainActivity.presenter.getModel().checkMyPersonalTrainer();
                MainActivity.presenter.setGoingTo(PersonalTrainerInfoActivity.class);
                MainActivity.presenter.setGoBack(ClientMainActivity.class);
                intent = new Intent(this, LoadingActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}