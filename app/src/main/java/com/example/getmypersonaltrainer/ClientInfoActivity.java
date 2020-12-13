package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClientInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        MainActivity.presenter.setActualActivity(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.client_info_activity_title);

        if(MainActivity.presenter.getChangingClient() != null){
            putClientInfo(MainActivity.presenter.getChangingClient());
        }
    }

    private void putClientInfo(Client client){
        String name = client.getName();
        String age = String.valueOf(client.takeAge());
        String bodyMass = MainActivity.decimalFormat.format(client.getBodyMass());
        String height = MainActivity.decimalFormat.format(client.getSize());
        String phone = client.getPhone();

        TextView textViewName = findViewById(R.id.text_client_name_client_info_activity);
        textViewName.setText(name);

        TextView textViewAge = findViewById(R.id.text_age_client_info_activity);
        textViewAge.setText(age);

        TextView textViewBodyMass = findViewById(R.id.text_weight_client_info_activity);
        textViewBodyMass.setText(bodyMass);

        TextView textViewHeight = findViewById(R.id.text_size_client_info_activity);
        textViewHeight.setText(height);

        TextView textViewPhone = findViewById(R.id.text_phone_client_info_activity);
        textViewPhone.setText(phone);
    }

    public void addExercise(View view){
        Intent intent = new Intent(this, CreateExerciseActivity.class);
        startActivity(intent);
    }

    public void removeClient(View view){
        if(MainActivity.presenter.getChangingClient() != null) {
            MainActivity.presenter.getModel().clientUnsubscribeFromPersonalTrainer(
                    MainActivity.presenter.getChangingClient()
            );

            Intent intent = new Intent(this, PersonalTrainerMainActivity.class);
            startActivity(intent);
        }
    }

    public void changeExercise(View view){
        Intent intent = new Intent(this, ClientExerciseListActivity.class);
        startActivity(intent);
    }

}