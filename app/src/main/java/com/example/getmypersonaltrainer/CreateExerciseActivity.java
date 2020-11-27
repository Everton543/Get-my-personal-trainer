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

import static java.util.Objects.*;

public class CreateExerciseActivity extends AppCompatActivity implements AutoFillExerciseInfoInterface, CreateExerciseInterface, FastError{

    private String[] EXERCISE_NAMES = null;
    private int clientIndex = -1;
    private static String TAG = "CreateExercise";
    private String[] weekDays;
    private DayOfWeek dayOfWeek = null;
    private boolean changingExercise = false;
    private String exerciseId = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        MainActivity.presenter.setActualActivity(this);

        Log.i(TAG, "On create Create Exercise");

        if(getIntent().hasExtra("exerciseId")){

            //todo fix bug
            exerciseId = getIntent().getStringExtra("exerciseId");
            Log.i(TAG, "Exercise Id : " + exerciseId);
            //fillGivenExerciseInfo();
        }

        if(getIntent().hasExtra("index")){
            if(MainActivity.presenter.getUser() instanceof PersonalTrainer) {
                clientIndex = Integer.parseInt(requireNonNull(getIntent().getStringExtra("index")));

                Log.i(TAG, "Changing client " +
                      ((PersonalTrainer) MainActivity.presenter.getUser())
                            .getClients()
                            .get(clientIndex)
                            .getUserId()
                );
            }
        }

        weekDays = getResources().getStringArray(R.array.weekDays);

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void fillGivenExerciseInfo(){
        //todo finish this function after test
        //todo fix bug
        if(MainActivity.presenter.getUser() instanceof PersonalTrainer) {
            AutoCompleteTextView textExerciseName = findViewById(R.id.edit_text_exercise_name_create_exercise);
            textExerciseName.setText(
                  requireNonNull(((PersonalTrainer) MainActivity.presenter.getUser())
                        .getClients()
                        .get(clientIndex)
                        .getExerciseList()
                        .get(exerciseId))
                  .getName()
            );
        }
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
    private boolean getDayOfWeek(String week){
        Log.i(TAG, "Called getDayOfWeek");
        for(int i = 0; i < weekDays.length; i++){
            if(week.equals(weekDays[i])){
                dayOfWeek = DayOfWeek.of(i + 1);
                Log.i(TAG, "DayOfWeek : " + dayOfWeek);
                return true;
            }
        }

        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendExercise(View view){
        Log.i(TAG, "Send Exercise Called");
        //To-do: fix bug
        AutoCompleteTextView textExerciseName = findViewById(R.id.edit_text_exercise_name_create_exercise);
        String exerciseName = String.valueOf(textExerciseName.getText());
        Log.i(TAG, "Exercise name: " + exerciseName);
        AutoCompleteTextView editTextDayOfWeek = findViewById(R.id.edit_text_day_of_week_create_exercise);
        String textDayOfWeek = String.valueOf(editTextDayOfWeek.getText());
        Log.i(TAG, "Week day string : " + textDayOfWeek);

        boolean validDayOfWeek = getDayOfWeek(textDayOfWeek);
        Log.i(TAG, "Valide day of week : " + validDayOfWeek);
        if (validDayOfWeek) {
            EditText editTextEmphasis = findViewById(R.id.edit_text_emphasis_create_exercise);
            String emphasis = String.valueOf(editTextEmphasis.getText());

            Log.i(TAG, "emphasis: " + emphasis);

            EditText editTextRepetition = findViewById(R.id.edit_text_repetition_create_exercise);
            String repetition = String.valueOf(editTextRepetition.getText());

            Log.i(TAG, "Repetition :" + repetition);

            EditText editTextSeries = findViewById(R.id.edit_text_series_create_exercise);
            String seriesText = String.valueOf(editTextSeries.getText());
            Log.i(TAG, "Series text: " + seriesText);
            int series = Integer.parseInt(seriesText);

            Log.i(TAG, "Series number: " + series);

            EditText editTextIntervalSeries = findViewById(R.id.edit_text_interval_series_create_exercise);
            String seriesInterval = String.valueOf(editTextIntervalSeries.getText());
            Log.i(TAG, "Series interval: " + seriesInterval);

            EditText editTextIntervalExercise = findViewById(R.id.edit_text_interval_exercise_create_exercise);
            String exerciseInterval = String.valueOf(editTextIntervalExercise.getText());

            Log.i(TAG, "Exercise interval: " + exerciseInterval);

            EditText editTextVideoLink = findViewById(R.id.edit_text_video_link_create_exercise);
            String videoLink = String.valueOf(editTextVideoLink.getText());

            Log.i(TAG, "Video link : " + videoLink);

            Exercise exercise = new Exercise(exerciseName, dayOfWeek, emphasis, repetition,
                series, seriesInterval, exerciseInterval, videoLink);

            if (MainActivity.presenter.getUser() instanceof PersonalTrainer) {
                MainActivity.presenter.getModel().addClientExercise(
                      ((PersonalTrainer) MainActivity.presenter
                            .getUser())
                            .getClients()
                            .get(clientIndex), exercise
                );
            }

        }else {
            MainActivity.presenter.getModel().getWarnings().invalidDayOfWeek();
        }
    }

    @Override
    public void loadingError() {

    }

    @Override
    public void finishedCharge() {
        Intent intent = new Intent(this, PersonalTrainerMainActivity.class);
        startActivity(intent);
    }
}