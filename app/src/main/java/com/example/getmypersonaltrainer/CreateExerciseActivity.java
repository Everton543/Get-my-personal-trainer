package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class CreateExerciseActivity extends AppCompatActivity implements AutoFillExerciseInfoInterface, CreateExerciseInterface{

    private String[] EXERCISE_NAMES = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);

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
}