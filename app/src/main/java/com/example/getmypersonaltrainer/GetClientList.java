package com.example.getmypersonaltrainer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.getmypersonaltrainer.MainActivity.presenter;

public class GetClientList implements Runnable {
    private final PersonalTrainer personalTrainer;
    private final List<Client> clients = new ArrayList<>();
    private static final String TAG = "GetClientList";
    GetClientList(PersonalTrainer personalTrainer){
        this.personalTrainer = personalTrainer;
    }

    @Override
    public void run() {
        Log.i(TAG, "getClientList function called");
        Query query = presenter.getModel().getDatabase().getReference("Users")
                .orderByChild("personalTrainerId")
                .equalTo(personalTrainer.getUserId());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(TAG, "getClientList onDataChangeCalled");

                boolean userNotLogged = !presenter.isLogged();
                if(snapshot.exists() && userNotLogged){
//                        || presenter.getUser() instanceof PersonalTrainer){
                    Log.i(TAG, "Found some clients");
                    clients.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Client client = dataSnapshot.getValue(Client.class);
                        clients.add(client);
                    }
                    presenter.setClientList(clients);
                    //personalTrainer.setClients(clients);
                }
                else if (userNotLogged){
                    Log.i(TAG, "Found 0 client");
                }
                if(presenter.getActualActivity() instanceof FastError && userNotLogged){
                    presenter.setLogged(true);
                    ((FastError) presenter.getActualActivity()).finishedCharge();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
