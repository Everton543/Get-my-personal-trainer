package com.example.getmypersonaltrainer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class ClientMainActivity extends AppCompatActivity {

    private Client client;
    private final List<ExerciseListViewAdapter> exerciseListViewAdapters = new ArrayList<>();
    private final List<RecyclerView> exerciseListRecyclerView = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
        MainActivity.presenter.setActualActivity(this);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.client_main_activity_title);

        if(MainActivity.presenter.getUser() instanceof Client){
            client = (Client) MainActivity.presenter.getUser();
            if(MainActivity.presenter.getUser().getReceivedInvitation()){
                TextView textView = findViewById(R.id.text_invitation_message_client_main);
                String invitationText = getString(R.string.client_received_invitation);
                invitationText += " " + getString(R.string.item_read_invitation);
                textView.setText(invitationText);
                textView.setVisibility(View.VISIBLE);
            }
        }

        if(exerciseListRecyclerView.size() < 1) {
            RecyclerView mondayView = findViewById(R.id.recycler_view_client_main_monday);
            RecyclerView tuesdayView = findViewById(R.id.recycler_view_client_main_tuesday);
            RecyclerView wednesdayView = findViewById(R.id.recycler_view_client_main_wednesday);
            RecyclerView thursdayView = findViewById(R.id.recycler_view_client_main_thursday);
            RecyclerView fridayView = findViewById(R.id.recycler_view_client_main_friday);
            RecyclerView saturdayView = findViewById(R.id.recycler_view_client_main_saturday);


            setRecyclerViewAdapter(mondayView, DayOfWeek.MONDAY);
            setRecyclerViewAdapter(tuesdayView, DayOfWeek.TUESDAY);
            setRecyclerViewAdapter(wednesdayView, DayOfWeek.WEDNESDAY);
            setRecyclerViewAdapter(thursdayView, DayOfWeek.THURSDAY);
            setRecyclerViewAdapter(fridayView, DayOfWeek.FRIDAY);
            setRecyclerViewAdapter(saturdayView, DayOfWeek.SATURDAY);

            exerciseListRecyclerView.add(mondayView);
            exerciseListRecyclerView.add(tuesdayView);
            exerciseListRecyclerView.add(wednesdayView);
            exerciseListRecyclerView.add(thursdayView);
            exerciseListRecyclerView.add(fridayView);
            exerciseListRecyclerView.add(saturdayView);
        }
    }

    private void setRecyclerViewAdapter(RecyclerView recyclerView, DayOfWeek dayOfWeek){
        if(MainActivity.presenter.getUser() instanceof Client) {
            ExerciseListViewAdapter exerciseListViewAdapter =
                  new ExerciseListViewAdapter(
                          MainActivity.presenter.getModel().getClientExerciseList(client, dayOfWeek),
                        false
                  );

            recyclerView.setAdapter(exerciseListViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            exerciseListViewAdapters.add(exerciseListViewAdapter);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        for(int i = 0 ; i < exerciseListViewAdapters.size(); i++) {
            exerciseListViewAdapters.get(i).startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for(int i = 0 ; i < exerciseListViewAdapters.size(); i++) {
            exerciseListViewAdapters.get(i).stopListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.client_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.client_menu_item_see_personal_trainer: {
                if (MainActivity.presenter.getMyPersonalTrainer() == null) {
                    MainActivity.presenter.setGetInfoFromDatabase(true);
                    MainActivity.presenter.getModel().checkMyPersonalTrainer();
                    MainActivity.presenter.setGoingTo(PersonalTrainerInfoActivity.class);
                    MainActivity.presenter.setGoBack(ClientMainActivity.class);
                    intent = new Intent(this, LoadingActivity.class);
                } else {
                    intent = new Intent(this, PersonalTrainerInfoActivity.class);
                }
                startActivity(intent);
                return true;
            }

            case R.id.client_menu_item_read_invitation:{
                intent = new Intent(this, ReadInvitationMessageActivity.class);
                startActivity(intent);
                return true;
            }

            case R.id.client_menu_item_change_client_info:{
                intent = new Intent(this, ChangeClientInfoActivity.class);
                startActivity(intent);
                return true;
            }

            case R.id.client_menu_item_find_all_personal_trainer:{
                intent = new Intent(this, AllPersonalTrainerListResultActivity.class);
                startActivity(intent);
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}