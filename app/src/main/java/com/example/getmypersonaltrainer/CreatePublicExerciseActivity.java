package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

public class CreatePublicExerciseActivity extends AppCompatActivity {
   private Exercise publicExercise = null;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_public_exercise);
      ActionBar actionBar = getSupportActionBar();
      assert actionBar != null;
      actionBar.setTitle(R.string.create_exercise_title);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   }

   private int setExerciseInfo(){
      AutoCompleteTextView autoCompleteTextView = findViewById(R.id.edit_text_exercise_name_create_public_exercise);
      String exerciseName = autoCompleteTextView.getText().toString();
      boolean emptyText = !MainActivity.presenter.getModel().getValidateInfo().checkId(exerciseName);
      if(emptyText){
         return MainActivity.invalidID;
      }

      EditText editText = findViewById(R.id.edit_text_emphasis_create_public_exercise);
      String emphasis = editText.getText().toString();
      emptyText = MainActivity.presenter.getModel().getValidateInfo().isEmptyString(emphasis);
      if(emptyText){
         return MainActivity.emptyInfo;
      }

      editText = findViewById(R.id.edit_text_video_link_create_public_exercise);
      String videoLink = editText.getText().toString();
      emptyText = MainActivity.presenter.getModel().getValidateInfo().isEmptyString(videoLink);
      if(emptyText){
         return MainActivity.emptyInfo;
      }

      editText = findViewById(R.id.edit_text_observations_create_public_exercise);
      String observations = editText.getText().toString();
      emptyText = MainActivity.presenter.getModel().getValidateInfo().isEmptyString(observations);
      if(emptyText){
         return MainActivity.emptyInfo;
      }

      String exerciseId = exerciseName + "Free";
      publicExercise = new Exercise(exerciseName, exerciseId, emphasis, videoLink,observations, true);
      return MainActivity.allGood;
   }


   public void sendExercise(View view){
      int situationCase = setExerciseInfo();
      switch (situationCase) {
         case MainActivity.allGood: {
            MainActivity.presenter.setGetInfoFromDatabase(true);
            MainActivity.presenter.getModel().savePublicExercise(publicExercise);
            MainActivity.presenter.setGoingTo(PersonalTrainerMainActivity.class);
            MainActivity.presenter.setGoBack(CreatePublicExerciseActivity.class);
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
            break;
         }

         case MainActivity.emptyInfo: {
            MainActivity.presenter.getModel().getWarnings().emptyInfo();
            break;
         }

         case MainActivity.invalidID: {
            MainActivity.presenter.getModel().getWarnings().invalidId();
            break;
         }
      }
   }

}