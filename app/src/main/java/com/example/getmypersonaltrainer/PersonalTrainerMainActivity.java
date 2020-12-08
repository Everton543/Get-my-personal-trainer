package com.example.getmypersonaltrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class PersonalTrainerMainActivity extends AppCompatActivity {
    private static final String TAG = "TrainerMainActivity";
    private ClientListViewAdapter clientListViewAdapter = null;
    private RecyclerView personalTrainerRecyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trainer_main);
        MainActivity.presenter.setActualActivity(this);
        Log.i(TAG, "Started Personal Trainer main Activity");
        MainActivity.presenter.setChangingClient(null);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.personal_trainer_main_title);

        boolean receivedInvitation = MainActivity.presenter.getUser().getReceivedInvitation();
        if(receivedInvitation){
            receivedInvitation();
        }

        boolean personalTrainerHasClients = false;
        if(MainActivity.presenter.getClientList() != null){
            personalTrainerHasClients = MainActivity.presenter.getClientList().size() > 0;
        }

        if(personalTrainerHasClients){
            personalTrainerRecyclerView =  findViewById(R.id.recycler_view_clients);

            clientListViewAdapter = new ClientListViewAdapter(MainActivity.presenter
                    .getModel()
                    .getClientListRecyclerOptions(),this);

            personalTrainerRecyclerView.setAdapter(clientListViewAdapter);
            personalTrainerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else{
            noClient();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(clientListViewAdapter != null) {
            clientListViewAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(clientListViewAdapter != null) {
            clientListViewAdapter.stopListening();
        }
    }

    public void receivedInvitation(){
        TextView textView = findViewById(R.id.text_received_invitation_alert_personal_main);
        String invitationText = getString(R.string.personal_trainer_received_invitation);
        invitationText += " " + getString(R.string.item_read_invitation);
        textView.setText(invitationText);
        textView.setVisibility(View.VISIBLE);
    }

    public void noClient() {
        String inviteClient = getString(R.string.item_invite_client);
        String message = "You don't have Clients to find clients go to the menu at the top right" +
              " conner of the screen and click in " + inviteClient;

        TextView text = findViewById(R.id.text_no_clients_alert_personal_main);
        text.setText(message);
        text.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trainer_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.item_create_public_exercise: {
                intent = new Intent(this, CreatePublicExerciseActivity.class);
                startActivity(intent);
                return true;
            }

            case R.id.item_invite_client: {
                intent = new Intent(this, SendInvitationActivity.class);
                startActivity(intent);
                return true;
            }

            case R.id.trainer_menu_item_read_invitation:{
                intent = new Intent(this, ReadInvitationMessageActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

