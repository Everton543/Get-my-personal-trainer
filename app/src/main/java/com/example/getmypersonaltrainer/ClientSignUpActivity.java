package com.example.getmypersonaltrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ClientSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sign_up);
    }

    public void ClientSignUp(View view){
        boolean result = false;

        EditText editText = (EditText) findViewById(R.id.edit_text_id_client_sign_up_activity);
        String id = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_text_password_client_sign_up_activity);
        String password = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_text_confirm_password_client_sign_up_activity);
        String confirmPassword = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_text_name_client_sign_up_activity);
        String name = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_text_phone_client_sign_up_activity);
        String phone = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_text_birth_date_client_sign_up_activity);
        String birthDate = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_text_weight_client_sign_up_activity);
        String textBodyMass = editText.getText().toString();
        float bodyMass = Float.parseFloat(textBodyMass);

        editText = (EditText) findViewById(R.id.edit_text_size_client_sign_up_activity);
        float size = Float.parseFloat(editText.getText().toString());

        if(MainActivity.presenter.getModel().checkIfPasswordAreEqual(password, confirmPassword)){
            Client client = new Client(UserTypes.CLIENT, password, name, id, phone, birthDate, bodyMass, size);
            result = MainActivity.presenter.getModel().addNewClient(client, this);

        }else{
            MainActivity.presenter.getModel().passwordNotEqualError(this);
        }

        if(result == true){
            signUpSuccessfully();
        }
    }

    private void signUpSuccessfully(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}