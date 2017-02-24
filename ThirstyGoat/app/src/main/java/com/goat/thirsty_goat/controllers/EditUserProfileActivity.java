package com.goat.thirsty_goat.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;
import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.application.App;
import com.goat.thirsty_goat.models.User;

import java.util.HashMap;
import java.util.Map;

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText mUserNameField;
    private EditText mEmailField;
    private Spinner mAccounTypeSpinner;
    private Button mSaveButton;
    private Button mCancelEditButton;
    private Auth0 mAuth0;
    private AuthenticationAPIClient mClient;
    private UserProfile mUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        /**
         * Set up Auth0 main object and client
         */
        mAuth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        mClient = new AuthenticationAPIClient(mAuth0);


        /**
         * Get dialog widgets
         */
        mUserNameField = (EditText) findViewById(R.id.user_name);
        mEmailField = (EditText) findViewById(R.id.email);
        mAccounTypeSpinner = (Spinner) findViewById(R.id.account_type_spinner);

        mSaveButton = (Button) findViewById(R.id.save_button);
        mCancelEditButton = (Button) findViewById(R.id.cancel_edit_button);

        /**
         * Set up adapter to select account type
         */
        ArrayAdapter<User.AccountType> accountTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, User.AccountType.values());
        accountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccounTypeSpinner.setAdapter(accountTypeAdapter);

        /**
         * Set default field values
         */
        User.updateUserSingleton(mClient);
        updateFields();

        /**
         * Save and cancel button functionalities
         */
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });



    }

    private void updateFields() {
        mUserNameField.setText(User.getInstance().getUserName());
        mEmailField.setText(User.getInstance().getEmail());
        mAccounTypeSpinner.setSelection(User.findAccountTypePosition());
    }
    private void saveProfile() {
        UsersAPIClient userClient =
                new UsersAPIClient(mAuth0, App.getInstance().getUserCredentials().getIdToken());
        Map<String, Object> userMetadata = new HashMap<>();
        userMetadata.put("account_type", mAccounTypeSpinner.getSelectedItem().toString());
        userMetadata.put("email", mEmailField.getText().toString());
        userMetadata.put("name", mUserNameField.getText().toString());
//        userClient.updateMetadata(userMetadata);

        User.setAccountType((User.AccountType) mAccounTypeSpinner.getSelectedItem());
        User.setUserName(mUserNameField.getText().toString());
        User.setEmail(mEmailField.getText().toString());
        saveClientFields();
    }
    private void saveClientFields() {

    }
}
