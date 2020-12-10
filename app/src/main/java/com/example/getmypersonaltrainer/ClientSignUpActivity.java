package com.example.getmypersonaltrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.NumberFormat;
import java.util.GregorianCalendar;

public class ClientSignUpActivity extends AppCompatActivity{
    public static String TAG = "ClientSignUpActivity";
    private User client = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sign_up);
        MainActivity.presenter.setActualActivity(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.client_sign_up_activity_title);

        // Setup spinners for the birthdate picker...
        // Setup arrays to pick from...
        String[] days = {
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31"
        };
        String[] months = {
                "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"
        };
        GregorianCalendar gc = new GregorianCalendar();
        String[] years = new String[gc.get(GregorianCalendar.YEAR)-1900+1];
        for (int i=0;i<years.length;++i)
            years[i] = Integer.toString(1900 + i);

        // Get our spinners and setup with arrays
        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        Spinner spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        Spinner spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        // Put the arrays into the spinners...
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> adapterDay = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        // Specify the layout to use when the list of choices appears
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerYear.setAdapter(adapterYear);
        spinnerMonth.setAdapter(adapterMonth);
        spinnerDay.setAdapter(adapterDay);

        // Set the selection to 20 years ago...
        spinnerYear.setSelection(gc.get(GregorianCalendar.YEAR)-1920);
        spinnerMonth.setSelection(gc.get(GregorianCalendar.MONTH));
        spinnerDay.setSelection(gc.get(GregorianCalendar.DAY_OF_MONTH)-1);
    }

    private int setClientInfo(){
        EditText editText = (EditText) findViewById(R.id.edit_text_id_client_sign_up_activity);
        String id = editText.getText().toString();

        boolean emptyText = !MainActivity.presenter.getModel().getValidateInfo().checkId(id);
        if(emptyText){
            return MainActivity.invalidID;
        }

        editText = (EditText) findViewById(R.id.edit_text_password_client_sign_up_activity);
        String password = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_text_confirm_password_client_sign_up_activity);
        String confirmPassword = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_text_name_client_sign_up_activity);
        String name = editText.getText().toString();
        emptyText = MainActivity.presenter.getModel().getValidateInfo().isEmptyString(name);
        if(emptyText){
            return MainActivity.emptyInfo;
        }

        editText = (EditText) findViewById(R.id.edit_text_phone_client_sign_up_activity);
        String phone = editText.getText().toString();
        emptyText = MainActivity.presenter.getModel().getValidateInfo().isEmptyString(phone);
        if(emptyText){
            return MainActivity.emptyInfo;
        }

        // Setup CalendarView object...
        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        Spinner spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        Spinner spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        String YYYY = (String)spinnerYear.getSelectedItem();
        String MM = (String)spinnerMonth.getSelectedItem();
        String DD = (String)spinnerDay.getSelectedItem();
        int yyyy = Integer.parseInt(YYYY);
        int mm = Integer.parseInt(MM);
        int dd = Integer.parseInt(DD);
        // Verify day of the month...
        // Check specific month lengths...
        boolean changed = false;
        switch (mm) {
            case 2: // Feb
                if (((yyyy % 4) == 0) && (dd > 29)) {
                    dd = 29;
                    changed = true;
                }
                if (((yyyy % 4) > 0) && (dd > 28)) {
                    dd = 28;
                    changed = true;
                }
                break;
            case 4: // Mar
            case 6: // Jun
            case 9: // Sep
            case 11: // Nov
                if (dd > 30) {
                    dd = 30;
                    changed = true;
                }
            default:
                break;
        }
        if (changed) {
            NumberFormat nf2 = NumberFormat.getInstance();
            nf2.setMinimumIntegerDigits(2);
            spinnerDay.setSelection(dd-1);
            DD = nf2.format(dd);
        }
        // Set our global birthDate field...
        String birthDate = YYYY + "/" + MM + "/" + DD;
        Log.i(TAG,"Setting birthDate to: " + birthDate);

        editText = (EditText) findViewById(R.id.edit_text_weight_client_sign_up_activity);
        String textBodyMass = editText.getText().toString();
        emptyText = MainActivity.presenter.getModel().getValidateInfo().isEmptyString(textBodyMass);
        if(emptyText){
            return MainActivity.emptyInfo;
        }

        float bodyMass = Float.parseFloat(textBodyMass);


        editText = (EditText) findViewById(R.id.edit_text_size_client_sign_up_activity);
        String textSize = editText.getText().toString();
        emptyText = MainActivity.presenter.getModel().getValidateInfo().isEmptyString(textSize);
        if(emptyText){
            return MainActivity.emptyInfo;
        }
        float size = Float.parseFloat(textSize);

        boolean passwordsNotEqual = !MainActivity.presenter
                .getModel()
                .getValidateInfo()
                .checkIfPasswordAreEqual(password, confirmPassword);
        if(passwordsNotEqual){
            return MainActivity.passwordNotEqual;
        }

        boolean validPassword = MainActivity.presenter
                .getModel()
                .getValidateInfo()
                .password(password);
        if(validPassword) {
            client = new User(UserTypes.CLIENT, password, name, id, phone, birthDate, bodyMass, size);
            return MainActivity.allGood;
        }

        return MainActivity.invalidPassword;
    }

    public void ClientSignUp(View view){
        int situationCase = setClientInfo();
        switch (situationCase){
            case MainActivity.allGood: {
                MainActivity.presenter.setGetInfoFromDatabase(true);
                MainActivity.presenter.getModel().saveUser(client);
                MainActivity.presenter.setGoingTo(MainActivity.class);
                MainActivity.presenter.setGoBack(ClientSignUpActivity.class);
                Intent intent = new Intent(this, LoadingActivity.class);
                startActivity(intent);
                break;
            }

            case MainActivity.invalidPassword: {
                MainActivity.presenter.getModel().getWarnings().invalidPassword();
                break;
            }

            case MainActivity.passwordNotEqual: {
                MainActivity.presenter.getModel().getWarnings().passwordNotEqualError();
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
