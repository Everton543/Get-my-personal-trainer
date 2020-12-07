package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class CreatePublicExerciseActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_public_exercise);

      ActionBar actionBar = getSupportActionBar();
      assert actionBar != null;
      actionBar.setTitle(R.string.create_exercise_title);
   }

   public void sendExercise(View view){
      //todo BUG, it stays forever in the loading Activity
      AutoCompleteTextView autoCompleteTextView = findViewById(R.id.edit_text_exercise_name_create_public_exercise);
      String exerciseName = autoCompleteTextView.getText().toString();

      EditText editText = findViewById(R.id.edit_text_emphasis_create_public_exercise);
      String emphasis = editText.getText().toString();

      editText = findViewById(R.id.edit_text_video_link_create_public_exercise);
      String videoLink = editText.getText().toString();

      editText = findViewById(R.id.edit_text_observations_create_public_exercise);
      String observations = editText.getText().toString();

      String exerciseId = exerciseName + "Free";
      Exercise publicExercise = new Exercise(exerciseName, exerciseId, emphasis, videoLink,observations, true);

      MainActivity.presenter.getModel().savePublicExercise(publicExercise);
      MainActivity.presenter.setGoingTo(PersonalTrainerMainActivity.class);
      MainActivity.presenter.setGoBack(PersonalTrainerMainActivity.class);

      //todo put the warning message appear only after go Back or go Forward to the next activity
      Intent intent = new Intent(this, LoadingActivity.class);
      startActivity(intent);
   }

}