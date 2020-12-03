package com.example.getmypersonaltrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

public class PersonalTrainerMainActivity extends AppCompatActivity {
    private static final String TAG = "TrainerMainActivity";
    private ClientListViewAdapter clientListViewAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trainer_main);
        MainActivity.presenter.setActualActivity(this);
        Log.i(TAG, "Started Personal Trainer main Activity");

        boolean personalTrainerHasClients = false;
        if(MainActivity.presenter.getUser() instanceof PersonalTrainer){
            if(((PersonalTrainer) MainActivity.presenter.getUser()).getClients() != null) {
                personalTrainerHasClients = ((PersonalTrainer) MainActivity.presenter
                      .getUser()).getClients().size() > 0;
            }
        }

        if(personalTrainerHasClients){
            //todo make it back to normal after test
            RecyclerView personalTrainerRecyclerView =  findViewById(R.id.recycler_view_client_1);

            if (MainActivity.presenter.getUser() instanceof PersonalTrainer){

                clientListViewAdapter =
                      new ClientListViewAdapter(this,
                            (PersonalTrainer) MainActivity
                                  .presenter
                                  .getUser()
                      );
                personalTrainerRecyclerView.setAdapter(clientListViewAdapter);

                personalTrainerRecyclerView.setLayoutManager(new GridLayoutManager(this,
                      ((PersonalTrainer) MainActivity.presenter.getUser()).getClients().size()));
            }
        } else{
            noClient();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume Called");
        uploadAllAdapter();
    }

    public void uploadAllAdapter(){
        Log.i(TAG, "upload all adapter function called");
        if(MainActivity.presenter.getUser() instanceof PersonalTrainer &&
            clientListViewAdapter != null) {
            clientListViewAdapter.setPersonalTrainer((PersonalTrainer) MainActivity
                  .presenter.getUser());
            clientListViewAdapter.notifyDataSetChanged();
            setRecyclerViewLoadingError(false);
            PersonalTrainer personalTrainer = clientListViewAdapter.getPersonalTrainer();
            Log.i(TAG, "uploaded adapter");
        }
    }

    public void removeClient(int position){
        PersonalTrainer personalTrainer = clientListViewAdapter.getPersonalTrainer();
        personalTrainer.removeClient(position);
        clientListViewAdapter.notifyItemRemoved(position);
    }


    public void noClient() {
        String inviteClient = getString(R.string.item_invite_client);
        String message = "You don't have Clients to find clients go to the menu at the top right" +
              " conner of the screen and click in " + inviteClient;

        TextView text = findViewById(R.id.text_no_clients_alert);
        text.setText(message);
        text.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trainer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
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

    public boolean isRecyclerViewLoadingError() {
        return MainActivity.presenter.isRecyclerViewLoadingError();
    }

    public void setRecyclerViewLoadingError(boolean recyclerViewLoadingError) {
        MainActivity.presenter.setRecyclerViewLoadingError(recyclerViewLoadingError);
    }
}

