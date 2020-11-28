package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class ExerciseInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);
        if(MainActivity.presenter.getUser() instanceof Client){
            Intent intent = getIntent();
            //String id = intent.getStringExtra("exerciseId");
            UserInterface client = MainActivity.presenter.getUser(); //Grab the client
            Map<String, Exercise> exercise = client.getExerciseList(); //Get the list of exercises
                                                                        // Iterate through the list
            String id = null;
            String day = null;
            String emphasis = null;
            String name;
            DayOfWeek daysOfWeek;
            String repetitionTime;
            int series;
            String intervalBetweenSeries;
            String intervalBetweenExercises;
            String videoLink;
            String exerciseId;
            boolean free = false;
            TextView textView = null;

            for (Map.Entry<String, Exercise> entry : exercise.entrySet()) {
                //logging
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
               //name
                textView = findViewById(R.id.text_exercise_name_exercise_info_activity);
                name = String.valueOf(entry.getValue().getName());
                textView.setText(name);
                //day
                textView = findViewById(R.id.tag_week_day_exercise_info_activity);
                day = String.valueOf(entry.getValue().getDaysOfWeek());
                textView.setText(day);
                //objective
                textView = findViewById(R.id.text_exercise_emphasis_exercise_info_activity);
                emphasis = entry.getValue().getEmphasis();
                textView.setText(emphasis);
                //repitions
                textView = findViewById(R.id.text_exercise_repetition_exercise_info_activity);
                repetitionTime = entry.getValue().getRepetitionTime();
                textView.setText(repetitionTime);
                //series
                textView = findViewById(R.id.text_exercise_series_exercise_info_activity);
                series = entry.getValue().getSeries();
                textView.setText(series);
                //interval
                textView = findViewById(R.id.text_series_interval_exercise_info_activity);
                intervalBetweenSeries = entry.getValue().getIntervalBetweenSeries();
                textView.setText(intervalBetweenSeries);
                //intervalforex
                textView = findViewById(R.id.text_exercise_interval_exercise_info_activity);
                intervalBetweenExercises = entry.getValue().getIntervalBetweenExercises();
                textView.setText(intervalBetweenExercises);
                //videolink
                textView = findViewById(R.id.background_exercise_info_activity);
                videoLink = entry.getValue().getVideoLink();
                textView.setText(videoLink);
            }
        }
            //inefficient id names, can be approved upon
        }
    }
