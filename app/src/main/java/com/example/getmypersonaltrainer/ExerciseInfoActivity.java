package com.example.getmypersonaltrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class ExerciseInfoActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.exercise_information_title);

        if(MainActivity.presenter.getUser() instanceof Client){
            Exercise exercise = MainActivity.presenter.getSelectedExercise();

            String emphasis = exercise.getEmphasis();
            String name = exercise.getName();
            String repetitionTime = exercise.getRepetitionTime();
            int numSeries = exercise.getSeries();
            String series = String.valueOf(numSeries);
            String intervalBetweenSeries = exercise.getIntervalBetweenSeries();
            String intervalBetweenExercises = exercise.getIntervalBetweenExercises();
            String videoLink = exercise.getVideoLink();
            String observations = exercise.getObservations();


            TextView textView;

            //name
            textView = findViewById(R.id.text_exercise_name_exercise_info_activity);
            textView.setText(name);
            //day
            textView = findViewById(R.id.text_observations_exercise_info_activity);
            textView.setText(observations);
            //objective
            textView = findViewById(R.id.text_exercise_emphasis_exercise_info_activity);
            textView.setText(emphasis);
            //repetitions
            textView = findViewById(R.id.text_exercise_repetition_exercise_info_activity);
            textView.setText(repetitionTime);
            //series
            textView = findViewById(R.id.text_exercise_series_exercise_info_activity);
            textView.setText(series);
            //interval
            textView = findViewById(R.id.text_series_interval_exercise_info_activity);
            textView.setText(intervalBetweenSeries);
            //interval for exercise
            textView = findViewById(R.id.text_exercise_interval_exercise_info_activity);
            textView.setText(intervalBetweenExercises);
            //videolink
            textView = findViewById(R.id.text_exercise_video_exercise_info_activity);
            textView.setText(videoLink);
        }

        }
    }
