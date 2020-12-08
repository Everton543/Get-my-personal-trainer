package com.example.getmypersonaltrainer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.getmypersonaltrainer.MainActivity.presenter;


/**
 * @author Everton Alves
 */
public class GetExerciseNameList implements Runnable {
    private final PersonalTrainer personalTrainer;
    private static final String TAG = "GetExercisesName";

    public GetExerciseNameList(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    @Override
    public void run() {
        Log.i(TAG, "getExerciseNameList function called");
        Query query = presenter.getModel().getDatabase().getReference("Exercise")
                .orderByChild("free")
                .equalTo(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(TAG, "onDataChange from getExerciseNameList called");
                if(snapshot.exists() && presenter.isGetInfoFromDatabase()){
                    Log.i(TAG, "Found some exercises");

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        personalTrainer.getExerciseNameList().add(dataSnapshot.child("name").getValue(String.class));
                        Exercise exercise = dataSnapshot.getValue(Exercise.class);
                        if(exercise != null) {
                            presenter.getFreeExerciseList().put(exercise.getExerciseId(), exercise);
                        }
                    }
                }
                else if(presenter.isGetInfoFromDatabase()){
                    Log.i(TAG, "Didn't found any exercise");
                }
                presenter.setGetInfoFromDatabase(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
