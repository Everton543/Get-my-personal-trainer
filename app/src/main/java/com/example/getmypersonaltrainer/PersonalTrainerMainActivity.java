package com.example.getmypersonaltrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PersonalTrainerMainActivity extends AppCompatActivity {
    private static final String TAG = "TrainerMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trainer_main);
        Log.i(TAG, "Started Personal Trainer main Activity");

        boolean personalTrainerHasClients = ((PersonalTrainer) MainActivity.presenter
              .getUser()).getClients().size() > 0;
        //saved in MainActivity trying to solve the BUG
        if(MainActivity.personalTrainerRecyclerView == null) {
            MainActivity.personalTrainerRecyclerView = findViewById(R.id.recycler_view_client_1);

            if (MainActivity.presenter.getUser() instanceof PersonalTrainer
                && personalTrainerHasClients)
            {
                MainActivity.clientListViewAdapter =
                      new ClientListViewAdapter(this,
                            (PersonalTrainer) MainActivity
                                  .presenter
                                  .getUser()
                      );
                MainActivity.personalTrainerRecyclerView.setAdapter(MainActivity.clientListViewAdapter);
                MainActivity.personalTrainerRecyclerView.setLayoutManager(new GridLayoutManager(this,
                      ((PersonalTrainer) MainActivity.presenter.getUser()).getClients().size()));
            }
        } else if(personalTrainerHasClients){
            MainActivity.clientListViewAdapter.setContext(this);
            MainActivity.personalTrainerRecyclerView.setAdapter(MainActivity.clientListViewAdapter);
            MainActivity.personalTrainerRecyclerView.setLayoutManager(new GridLayoutManager(this,
                  ((PersonalTrainer) MainActivity.presenter.getUser()).getClients().size()));
        }



    }

    public void adapterChanged(){
        if(MainActivity.presenter.getUser() instanceof PersonalTrainer) {
            MainActivity.clientListViewAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "On resume");
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
            case R.id.item_create_public_exercise:
                intent = new Intent(this, CreatePublicExerciseActivity.class);
                startActivity(intent);
                return true;

            case R.id.item_invite_client:
                intent = new Intent(this, SendInvitationActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

