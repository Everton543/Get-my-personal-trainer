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

public class ChangeClientInfoActivity extends AppCompatActivity {
    private Spinner spinnerYear = null;
    private Spinner spinnerMonth = null;
    private Spinner spinnerDay = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_client_info);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.change_client_info_activity_title);

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
        spinnerYear = (Spinner) findViewById(R.id.spinner_year_change_client_info);
        spinnerMonth = (Spinner) findViewById(R.id.spinner_month_change_client_info);
        spinnerDay = (Spinner) findViewById(R.id.spinner_day_change_client_info);
        // Put the arrays into the spinners...
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> adapterDay = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
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

        putClientInfo();
    }

    public void changeClientInfo(View view){
        Spinner spinnerYear = (Spinner) findViewById(R.id.spinner_year_change_client_info);
        Spinner spinnerMonth = (Spinner) findViewById(R.id.spinner_month_change_client_info);
        Spinner spinnerDay = (Spinner) findViewById(R.id.spinner_day_change_client_info);
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

        EditText editTextName = findViewById(R.id.edit_text_name_change_client_info);
        String name = String.valueOf(editTextName.getText());

        EditText editTextPhone = findViewById(R.id.edit_text_phone_change_client_info);
        String phone = String.valueOf(editTextPhone.getText());

        EditText editTextHeight = findViewById(R.id.edit_text_size_change_client_info);
        String height = String.valueOf(editTextHeight.getText());

        EditText editTextBodyMass = findViewById(R.id.edit_text_weight_change_client_info);
        String bodyMass = String.valueOf(editTextBodyMass.getText());

        if(MainActivity.presenter.getUser() instanceof Client){
            ((Client) MainActivity.presenter.getUser()).
                    setBodyMass(Float.parseFloat(bodyMass));

            ((Client) MainActivity.presenter.getUser()).setPhone(phone);

            ((Client) MainActivity.presenter.getUser()).setSize(Float.parseFloat(height));

            ((Client) MainActivity.presenter.getUser()).setName(name);

            ((Client) MainActivity.presenter.getUser()).setBirthDate(birthDate);

            MainActivity.presenter.getModel().updateClient(((Client) MainActivity.presenter.getUser()));
        }

        Intent intent = new Intent(this, ClientMainActivity.class);
        startActivity(intent);

    }

    public void putClientInfo(){
        if(MainActivity.presenter.getUser() instanceof Client) {
            EditText editTextName = findViewById(R.id.edit_text_name_change_client_info);
            editTextName.setText(
                    ((Client) MainActivity.presenter.getUser()).getName()
            );

            EditText editTextPhone = findViewById(R.id.edit_text_phone_change_client_info);
            editTextPhone.setText(
                    ((Client) MainActivity.presenter.getUser()).getPhone()
            );

            EditText editTextHeight = findViewById(R.id.edit_text_size_change_client_info);
            String height = String.valueOf(((Client) MainActivity.presenter.getUser()).getSize());
            editTextHeight.setText(height);

            EditText editTextBodyMass = findViewById(R.id.edit_text_weight_change_client_info);
            String bodyMass = String.valueOf(((Client) MainActivity.presenter.getUser()).getBodyMass());
            editTextBodyMass.setText(bodyMass);

            String birthDateText = ((Client) MainActivity.presenter.getUser()).getBirthDate();
            String [] birthDate = birthDateText.split("/");
            String year = birthDate[0];
            String month = birthDate[1];
            String day = birthDate[2];

            spinnerMonth.setSelection(Integer.parseInt(month) - 1);
            spinnerDay.setSelection(Integer.parseInt(day) - 1);
            spinnerYear.setSelection(Integer.parseInt(year) - 1900);

        }
    }

}