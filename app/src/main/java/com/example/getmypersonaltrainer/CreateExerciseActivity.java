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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private Exercise sendingExercise = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        MainActivity.presenter.setActualActivity(this);

        changingExercise = false;
        Log.i(TAG, "On create Create Exercise");

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

        if(getIntent().hasExtra("exerciseId")){

            exerciseId = getIntent().getStringExtra("exerciseId");
            Log.i(TAG, "Exercise Id : " + exerciseId);
            changingExercise = true;
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

        if(changingExercise){
            fillGivenExerciseInfo();
            setLayoutToChangingExercise();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void fillGivenExerciseInfo(){
        //todo finish this function after test
        //todo fix bug
        if(MainActivity.presenter.getChangingClient() != null){
            Exercise exercise = MainActivity.presenter
                  .getChangingClient()
                  .getExerciseList()
                  .get(exerciseId);
            Log.i(TAG, "Created Exercise from the given exerciseId");

            Log.i(TAG, "Exercise info: ");

            Log.i(TAG, "Exercise Name: " + exercise.getName());
            Log.i(TAG, "Exercise Day: " + exercise.getDaysOfWeek());
            Log.i(TAG, "Exercise LInk: " +  exercise.getVideoLink());
            Log.i(TAG, "Exercise Series: " +  exercise.getSeries());
            Log.i(TAG, "Exercise Emphasis: " +  exercise.getEmphasis());
            Log.i(TAG, "Exercise Interval exercise: "  + exercise.getIntervalBetweenExercises());
            Log.i(TAG, "Exercise Interval series: " + exercise.getSeries());
            Log.i(TAG, "Exercise Repetition: " + exercise.getRepetitionTime());

            AutoCompleteTextView textExerciseName = findViewById(R.id.edit_text_exercise_name_create_exercise);
            textExerciseName.setText(exercise.getName());

            AutoCompleteTextView editTextDayOfWeek = findViewById(R.id.edit_text_day_of_week_create_exercise);
            int indexDayOfWeek = -1;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                indexDayOfWeek = exercise.getDaysOfWeek().getValue();
            }

            if(indexDayOfWeek > 0) {
                editTextDayOfWeek.setText(weekDays[indexDayOfWeek - 1]);
            }

            EditText editTextEmphasis = findViewById(R.id.edit_text_emphasis_create_exercise);
            editTextEmphasis.setText(exercise.getEmphasis());

            EditText editTextRepetition = findViewById(R.id.edit_text_repetition_create_exercise);
            editTextRepetition.setText(exercise.getRepetitionTime());

            EditText editTextSeries = findViewById(R.id.edit_text_series_create_exercise);
            String exerciseSeries = String.valueOf(exercise.getSeries());
            editTextSeries.setText(exerciseSeries);

            EditText editTextIntervalSeries = findViewById(R.id.edit_text_interval_series_create_exercise);
            editTextIntervalSeries.setText(exercise.getIntervalBetweenSeries());

            EditText editTextIntervalExercise = findViewById(R.id.edit_text_interval_exercise_create_exercise);
            editTextIntervalExercise.setText(exercise.getIntervalBetweenExercises());

            EditText editTextVideoLink = findViewById(R.id.edit_text_video_link_create_exercise);
            editTextVideoLink.setText(exercise.getVideoLink());
        }
    }

    private void setLayoutToChangingExercise(){
        TextView textView = findViewById(R.id.title_create_exercise_activity);
        String newTitle = getResources().getString(R.string.change_exercise_title);
        textView.setText(newTitle);

        Button sendExercise = findViewById(R.id.button_confirm_new_exercise);
        sendExercise.setVisibility(View.GONE);

        Button changeExercise = findViewById(R.id.button_change_exercise_create_exercise);
        changeExercise.setVisibility(View.VISIBLE);

        Button deleteExercise = findViewById(R.id.button_delete_exercise_create_exercise);
        deleteExercise.setVisibility(View.VISIBLE);
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
    private boolean setExerciseInfo(){
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

            if(changingExercise){
                sendingExercise = new Exercise(exerciseName, dayOfWeek, emphasis, repetition,
                      series, seriesInterval, exerciseInterval, videoLink);
                sendingExercise.setExerciseId(exerciseId);

            }else{
            sendingExercise = new Exercise(exerciseName, dayOfWeek, emphasis, repetition,
                  series, seriesInterval, exerciseInterval, videoLink);
            }
            return true;
        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendExercise(View view){
        Log.i(TAG, "Send Exercise Called");
        boolean successfullySetExerciseInfo = setExerciseInfo();
        if(successfullySetExerciseInfo){

            if (MainActivity.presenter.getUser() instanceof PersonalTrainer) {
                MainActivity.presenter.getModel().addClientExercise(
                      ((PersonalTrainer) MainActivity.presenter
                            .getUser())
                            .getClients()
                            .get(clientIndex), sendingExercise
                );
            }

        }else {
            MainActivity.presenter.getModel().getWarnings().invalidDayOfWeek();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendExerciseChanges(View view){
        Log.i(TAG, "Send Exercise Changes Called");
        boolean successfullySetExerciseInfo = setExerciseInfo();
        if(successfullySetExerciseInfo && MainActivity.presenter.getChangingClient() != null){
            MainActivity.presenter.getModel().updateClientExercise(sendingExercise,
                  MainActivity.presenter.getChangingClient().getUserId()
            );

            changingExercise = false;
            MainActivity.presenter.setChangingClient(null);
            finishedCharge();
        }else {
            MainActivity.presenter.getModel().getWarnings().invalidDayOfWeek();
        }
    }

    public void eraseClientExercise(View view){
        if(MainActivity.presenter.getChangingClient() != null) {
            MainActivity.presenter.getModel().eraseClientExercise(
                  MainActivity.presenter.getChangingClient(),
                  exerciseId
            );

            changingExercise = false;
            MainActivity.presenter.setChangingClient(null);
            finishedCharge();

        }else{
            MainActivity.presenter.getModel().getWarnings().strangeError();
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