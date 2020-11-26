package com.example.getmypersonaltrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.time.DayOfWeek;
import java.util.Objects;

public class CreateExerciseActivity extends AppCompatActivity implements AutoFillExerciseInfoInterface, CreateExerciseInterface{

    private String[] EXERCISE_NAMES = null;
    private int clientIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        MainActivity.presenter.setActualActivity(this);

        MainActivity.presenter.setGoBack(CreateExerciseActivity.class);
        MainActivity.presenter.setGoingTo(PersonalTrainerMainActivity.class);

        if(getIntent().hasExtra("index")){
            if(MainActivity.presenter.getUser() instanceof PersonalTrainer) {
                clientIndex = Integer.parseInt(getIntent().getStringExtra("index"));

                Log.i("CREATE", "Changing client " +
                      ((PersonalTrainer) MainActivity.presenter.getUser())
                            .getClients()
                            .get(clientIndex)
                            .getUserId()
                );
            }
        }

        String[] weekDays = getResources().getStringArray(R.array.weekDays);

        String[] exerciseNames = new String[0];
        if(MainActivity.presenter.getUser() instanceof PersonalTrainer){

            exerciseNames = ((PersonalTrainer) MainActivity.presenter.getUser()).getExerciseNameList().toArray(new String[0]);
        }

        AutoCompleteTextView editTextWeekDays = findViewById(R.id.edit_text_day_of_week_create_exercise);
        ArrayAdapter<String> weekDaysAdapter = new ArrayAdapter<String>(this,
              android.R.layout.simple_list_item_1, weekDays);
        editTextWeekDays.setAdapter(weekDaysAdapter);

        AutoCompleteTextView editTextExerciseName = findViewById(R.id.edit_text_exercise_name_create_exercise);
        ArrayAdapter<String> exerciseName = new ArrayAdapter<>(this,
              android.R.layout.simple_list_item_1, exerciseNames);
        editTextExerciseName.setAdapter(exerciseName);

        //function called when the user finish writing the exercise name
        editTextExerciseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    //Check if the exercise Exists in the database
                    //If it does put the information using the function fillExerciseInfo
                }
            }
        });
    }

    @Override
    public void fillExerciseInfo(Exercise exercise) {
        //Get exercise info and put it in VideoLink and Emphasis EditTexts
    }

    @Override
    public void setExerciseNameList(String[] exerciseNameList) {
        this.EXERCISE_NAMES = exerciseNameList;
        AutoCompleteTextView editTextExerciseName = findViewById(R.id.edit_text_exercise_name_create_exercise);
        ArrayAdapter<String> exerciseNameAdapter = new ArrayAdapter<String>(this,
              android.R.layout.simple_list_item_1, EXERCISE_NAMES);
        editTextExerciseName.setAdapter(exerciseNameAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendExercise(View view){
        //To-do: fix bug
        AutoCompleteTextView textExerciseName = findViewById(R.id.edit_text_exercise_name_create_exercise);
        String exerciseName = String.valueOf(textExerciseName.getText());

        AutoCompleteTextView textDayOfWeek = findViewById(R.id.edit_text_day_of_week_create_exercise);
        int id = textDayOfWeek.getId();
        id += 1;
        DayOfWeek dayOfWeek = DayOfWeek.of(id);

        EditText editTextEmphasis = findViewById(R.id.edit_text_emphasis_create_exercise);
        String emphasis = String.valueOf(editTextEmphasis.getText());

        EditText editTextRepetition = findViewById(R.id.edit_text_repetition_create_exercise);
        String repetition = String.valueOf(editTextRepetition.getText());

        EditText editTextSeries = findViewById(R.id.edit_text_series_create_exercise);
        int series = Integer.getInteger(String.valueOf(editTextSeries.getText()));

        EditText editTextIntervalSeries = findViewById(R.id.edit_text_interval_series_create_exercise);
        String seriesInterval = String.valueOf(editTextIntervalSeries.getText());

        EditText editTextIntervalExercise = findViewById(R.id.edit_text_interval_exercise_create_exercise);
        String exerciseInterval = String.valueOf(editTextIntervalExercise.getText());

        EditText editTextVideoLink = findViewById(R.id.edit_text_video_link_create_exercise);
        String videoLink = String.valueOf(editTextVideoLink.getText());

        Exercise exercise = new Exercise(exerciseName, dayOfWeek, emphasis,repetition,
            series, seriesInterval, exerciseInterval,videoLink);

        if(MainActivity.presenter.getUser() instanceof PersonalTrainer) {
            MainActivity.presenter.getModel().addClientExercise(
                        ((PersonalTrainer) MainActivity.presenter
                        .getUser())
                        .getClients()
                        .get(clientIndex), exercise
            );
        }

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }

}