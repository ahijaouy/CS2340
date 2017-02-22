package com.goat.thirsty_goat.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.User;

public class UserProfile extends AppCompatActivity {


    private EditText userNameField;
    private EditText emailField;
    private Spinner accountTypeSpinner;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        userNameField = (EditText) findViewById(R.id.user_name);
        emailField = (EditText) findViewById(R.id.email);
        accountTypeSpinner = (Spinner) findViewById(R.id.account_type_spinner);

        ArrayAdapter<User.AccountType> accountTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, User.AccountType.values());
        accountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(accountTypeAdapter);

        // Set field values


    }
}
