package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.example.getmypersonaltrainer.MainActivity.presenter;

public class CheckLogin implements Runnable{
    private final static String TAG = "CheckLogin";
    private final List<User> userList = new ArrayList<>();
    private final String userId;
    private final String password;
    private final WeakReference<Activity> activity;

    public CheckLogin(String userId, String password, Activity activity) {
        this.userId = userId;
        this.password = password;
        this.activity = new WeakReference<>(activity);
    }


    @Override
    public void run() {
        Log.i(TAG, "checkLogin function called");
        Query query = presenter.getModel().getDatabase().getReference("Users")
                .orderByChild("userId")
                .equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(TAG, "onDataChange checkLogin called");
                userList.clear();
                boolean isNotLogged = !presenter.isLogged();
                if(snapshot.exists() && isNotLogged){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        User user = dataSnapshot.getValue(User.class);
                        userList.add(user);
                    }

                    Log.v(TAG, "Number of users with the id " + userId + " = " + userList.size());
                    userList.get(0).setPassword(password);

                    boolean correctPassword = false;
                    try {
                        correctPassword = Encrypt.verifyPassword(userList.get(0));
                    } catch (Exception e) {
                        Log.e(TAG, "Was not able to decrypt password");
                        e.printStackTrace();
                    }

                    Activity runActivity = activity.get();
                    if(correctPassword){
                        if(runActivity instanceof LoginInterface){
                            ((LoginInterface) runActivity).setPresenterUser(userList.get(0));

                            ((LoginInterface) runActivity).loginUserType(
                                    userList.get(0).getUserType(),
                                    true);
                        }
                    }else {

                        runActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                presenter.getModel().getWarnings().wrongPasswordOrUserId();
                            }
                        });
                    }
                }
                else if(isNotLogged){
                    Activity runActivity = activity.get();
                    if (runActivity  instanceof LoginInterface) {
                        ((LoginInterface) runActivity).loginUserType(
                                UserTypes.NONE,
                                false);
                    }
                    runActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            presenter.getModel().getWarnings().wrongPasswordOrUserId();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
