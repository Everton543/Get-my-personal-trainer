package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ChangePersonalTrainerInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_trainer_info);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.change_personal_trainer_info_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setPersonalInfoToTheView();
    }

    private void setPersonalInfoToTheView(){
        if(MainActivity.presenter.getUser() instanceof PersonalTrainer) {
            String textAboutMyself = ((PersonalTrainer) MainActivity.presenter.getUser())
                    .getAboutMyselfText();

            String textName =  MainActivity.presenter.getUser().getName();

            EditText aboutMyself =
                    findViewById(R.id.edit_text_about_myself_change_personal_trainer_activity);

            aboutMyself.setText(textAboutMyself);

            EditText name = findViewById(R.id.edit_text_name_change_personal_trainer_activity);
            name.setText(textName);
        }
    }

    private int setPersonalTrainerInfo() {
        EditText editText = (EditText) findViewById(
                R.id.edit_text_name_change_personal_trainer_activity
        );
        String name = editText.getText().toString();

        boolean emptyText = MainActivity.presenter.getModel().getValidateInfo()
                .isEmptyString(name);
        if(emptyText){
            return MainActivity.emptyInfo;
        }

        editText = findViewById(R.id.edit_text_about_myself_change_personal_trainer_activity);
        String textAboutMyself = String.valueOf(editText.getText());

        emptyText = MainActivity.presenter.getModel().getValidateInfo()
                .isEmptyString(textAboutMyself);
        if(emptyText){
            return MainActivity.emptyInfo;
        }

        if(MainActivity.presenter.getUser() instanceof PersonalTrainer) {
            MainActivity.presenter.getUser().setName(name);

            ((PersonalTrainer) MainActivity.presenter.getUser())
                    .setAboutMyselfText(textAboutMyself);
        }

        return MainActivity.allGood;
    }


    public void changeInfo(View view){
        int situationCase = setPersonalTrainerInfo();
        switch (situationCase) {
            case MainActivity.allGood: {
                if (MainActivity.presenter.getUser() instanceof PersonalTrainer) {
                    MainActivity.presenter.getModel().updatePersonalTrainer(
                            (PersonalTrainer) MainActivity.presenter.getUser()
                    );
                    Intent intent = new Intent(this,
                            PersonalTrainerMainActivity.class);
                    startActivity(intent);
                }
                break;
            }

            case MainActivity.emptyInfo: {
                MainActivity.presenter.getModel().getWarnings().emptyInfo();
                break;
            }
        }
    }
}