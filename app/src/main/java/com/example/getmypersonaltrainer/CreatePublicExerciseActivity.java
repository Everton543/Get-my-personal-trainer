package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class CreatePublicExerciseActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_public_exercise);

      String[] exerciseNames = new String[0];
      if(MainActivity.presenter.getUser() instanceof PersonalTrainer){
         exerciseNames = ((PersonalTrainer) MainActivity.presenter.getUser()).getExerciseNameList().toArray(new String[0]);
      }


      AutoCompleteTextView editTextExerciseName = findViewById(R.id.edit_text_exercise_name_create_public_exercise);
      ArrayAdapter<String> exerciseName = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1, exerciseNames);
      editTextExerciseName.setAdapter(exerciseName);
   }
}