package com.example.getmypersonaltrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class ExerciseInfoActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        if(MainActivity.presenter.getUser() instanceof Client){
            Intent intent = getIntent();
            String exerciseId = intent.getStringExtra("exerciseId");

            Exercise exercise = MainActivity.presenter
                  .getUser().getExerciseList().get(exerciseId);

            String day = String.valueOf(exercise.getDaysOfWeek());
            String emphasis = exercise.getEmphasis();
            String name = exercise.getName();
            String repetitionTime = exercise.getRepetitionTime();
            int numSeries = exercise.getSeries();
            String series = String.valueOf(numSeries);
            String intervalBetweenSeries = exercise.getIntervalBetweenSeries();
            String intervalBetweenExercises = exercise.getIntervalBetweenSeries();
            String videoLink = exercise.getVideoLink();


            TextView textView = null;

            //name
            textView = findViewById(R.id.text_exercise_name_exercise_info_activity);
            textView.setText(name);
            //day
            textView = findViewById(R.id.text_week_day_exercise_info_activity);
            textView.setText(day);
            //objective
            textView = findViewById(R.id.text_exercise_emphasis_exercise_info_activity);
            textView.setText(emphasis);
            //repitions
            textView = findViewById(R.id.text_exercise_repetition_exercise_info_activity);
            textView.setText(repetitionTime);
            //series
            textView = findViewById(R.id.text_exercise_series_exercise_info_activity);
            textView.setText(series);
            //interval
            textView = findViewById(R.id.text_series_interval_exercise_info_activity);
            textView.setText(intervalBetweenSeries);
            //intervalforex
            textView = findViewById(R.id.text_exercise_interval_exercise_info_activity);
            textView.setText(intervalBetweenExercises);
            //videolink
            textView = findViewById(R.id.text_exercise_video_exercise_info_activity);
            textView.setText(videoLink);
        }

        }
    }
