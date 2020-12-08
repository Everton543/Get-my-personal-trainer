package com.example.getmypersonaltrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
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
import android.widget.Spinner;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class CreateExerciseActivity extends AppCompatActivity implements AutoFillExerciseInfoInterface, CreateExerciseInterface, FastError{

    private static final String TAG = "CreateExercise";
    private String[] weekDays;
    private DayOfWeek dayOfWeek = null;
    private boolean changingExercise = false;
    private Exercise sendingExercise = null;
    private List<String> exerciseNameList;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        MainActivity.presenter.setActualActivity(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.create_exercise_title);

        weekDays = getResources().getStringArray(R.array.weekDays);

        changingExercise = false;
        Log.i(TAG, "On create Create Exercise");

        String[] exerciseNames = new String[0];
        if(MainActivity.presenter.getUser() instanceof PersonalTrainer){
            exerciseNameList = ((PersonalTrainer) MainActivity.presenter.getUser()).getExerciseNameList();
            exerciseNames = exerciseNameList.toArray(new String[0]);
        }

        //AutoCompleteTextView editTextWeekDays = findViewById(R.id.edit_text_day_of_week_create_exercise);
        Spinner spinnerWeekDay = (Spinner) findViewById(R.id.spinner_day_of_week_create_exercise);
        ArrayAdapter<String> weekDaysAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, weekDays);
        weekDaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeekDay.setAdapter(weekDaysAdapter);
        spinnerWeekDay.setSelection(0);

        final AutoCompleteTextView editTextExerciseName = findViewById(R.id.edit_text_exercise_name_create_exercise);
        ArrayAdapter<String> exerciseName = new ArrayAdapter<>(this,
              android.R.layout.simple_list_item_1, exerciseNames);
        editTextExerciseName.setAdapter(exerciseName);

        //function called when the user finish writing the exercise name
        editTextExerciseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    Log.i(TAG, "Clicked in an exercise name");
                    String exerciseName = String.valueOf(editTextExerciseName.getText());
                    String exerciseFreeId = exerciseName + "Free";

                    Map<String,Exercise> freeExercises = MainActivity.presenter.getFreeExerciseList();


                    if(exerciseNameList.contains(exerciseName)){
                        if(MainActivity.presenter.getUser() instanceof PersonalTrainer){
                            Exercise exercise =
                                  ((PersonalTrainer) MainActivity.presenter
                                        .getUser())
                                        .getExerciseFromExerciseName(exerciseName);

                            if(exercise != null) {
                                fillExerciseInfo(exercise);
                                return;
                            }
                        }
                    }

                    if(freeExercises.containsKey(exerciseFreeId)){
                        Exercise exercise = MainActivity.presenter.getFreeExerciseList().get(exerciseFreeId);
                        fillExerciseInfo(exercise);
                    }
                }
            }
        });

        if(MainActivity.presenter.getSelectedExercise() != null){
            changingExercise = true;
            setLayoutToChangingExercise();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void fillGivenExerciseInfo(){
        if(MainActivity.presenter.getChangingClient() != null){
            Exercise exercise = MainActivity.presenter
                  .getSelectedExercise();

            AutoCompleteTextView textExerciseName = findViewById(R.id.edit_text_exercise_name_create_exercise);
            textExerciseName.setText(exercise.getName());

            //AutoCompleteTextView editTextDayOfWeek = findViewById(R.id.edit_text_day_of_week_create_exercise);
            Spinner spinnerWeekDay = (Spinner) findViewById(R.id.spinner_day_of_week_create_exercise);

            int indexDayOfWeek = -1;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                indexDayOfWeek = exercise.getDaysOfWeek().getValue();
            }

            if(indexDayOfWeek > 0) {
                //editTextDayOfWeek.setText(weekDays[indexDayOfWeek - 1]);
                spinnerWeekDay.setSelection(indexDayOfWeek - 1);
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

            EditText editTextObservation = findViewById(R.id.edit_text_observations_exercise_create_exercise);
            editTextObservation.setText(exercise.getObservations());

            EditText editTextVideoLink = findViewById(R.id.edit_text_video_link_create_exercise);
            editTextVideoLink.setText(exercise.getVideoLink());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setLayoutToChangingExercise(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.change_exercise_title);

        Button sendExercise = findViewById(R.id.button_confirm_new_exercise);
        sendExercise.setVisibility(View.GONE);

        Button changeExercise = findViewById(R.id.button_change_exercise_create_exercise);
        changeExercise.setVisibility(View.VISIBLE);

        Button deleteExercise = findViewById(R.id.button_delete_exercise_create_exercise);
        deleteExercise.setVisibility(View.VISIBLE);

        fillGivenExerciseInfo();
    }

    @Override
    public void fillExerciseInfo(Exercise exercise) {
        //Get exercise info and put it in VideoLink and Emphasis EditTexts and observations
        Log.i(TAG, "Fill exercise info");
        EditText textEmphasis = findViewById(R.id.edit_text_emphasis_create_exercise);
        textEmphasis.setText(exercise.getEmphasis());
    }

    @Override
    public void setExerciseNameList(String[] exerciseNameList) {
        AutoCompleteTextView editTextExerciseName = findViewById(R.id.edit_text_exercise_name_create_exercise);
        ArrayAdapter<String> exerciseNameAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, exerciseNameList);
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
        //AutoCompleteTextView editTextDayOfWeek = findViewById(R.id.edit_text_day_of_week_create_exercise);
        Spinner spinnerWeekDay = (Spinner) findViewById(R.id.spinner_day_of_week_create_exercise);
        String textDayOfWeek = String.valueOf(spinnerWeekDay.getSelectedItem());
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

            EditText editTextObservations = findViewById(R.id.edit_text_observations_exercise_create_exercise);
            String observations = String.valueOf(editTextObservations.getText());

            EditText editTextVideoLink = findViewById(R.id.edit_text_video_link_create_exercise);
            String videoLink = String.valueOf(editTextVideoLink.getText());

            Log.i(TAG, "Video link : " + videoLink);

            if(changingExercise){
                sendingExercise = new Exercise(exerciseName, dayOfWeek, emphasis, repetition,
                      series, seriesInterval, exerciseInterval, videoLink, observations);
                sendingExercise.setExerciseId(
                        MainActivity.presenter.getSelectedExercise().getExerciseId()
                );
            }else{
            sendingExercise = new Exercise(exerciseName, dayOfWeek, emphasis, repetition,
                  series, seriesInterval, exerciseInterval, videoLink, observations);
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

            if (MainActivity.presenter.getChangingClient() != null) {
                MainActivity.presenter.setGetInfoFromDatabase(true);
                MainActivity.presenter.getModel().addClientExercise(
                      MainActivity.presenter.getChangingClient()
                      , sendingExercise
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
                  MainActivity.presenter.getSelectedExercise().getExerciseId()
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