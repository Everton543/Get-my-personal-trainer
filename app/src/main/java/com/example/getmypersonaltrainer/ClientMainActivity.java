package com.example.getmypersonaltrainer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.DayOfWeek;

public class ClientMainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
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
    }

    private void setRecyclerViewAdapter(RecyclerView recyclerView, DayOfWeek dayOfWeek){
        /* Todo After test check if List<Exercise> is not null*/
        if(MainActivity.presenter.getUser() instanceof Client) {
            ExerciseListViewAdapter exerciseListViewAdapter =
                  new ExerciseListViewAdapter(this,
                        ((Client) MainActivity.presenter.getUser()).getExercisesOfWeekDay(dayOfWeek),
                        false
                        );

            recyclerView.setAdapter(exerciseListViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                if(MainActivity.presenter.getMyPersonalTrainer() == null) {
                    MainActivity.presenter.getModel().checkMyPersonalTrainer();
                    MainActivity.presenter.setGoingTo(PersonalTrainerInfoActivity.class);
                    MainActivity.presenter.setGoBack(ClientMainActivity.class);
                    intent = new Intent(this, LoadingActivity.class);
                } else{
                    intent = new Intent(this, PersonalTrainerInfoActivity.class);
                }
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}