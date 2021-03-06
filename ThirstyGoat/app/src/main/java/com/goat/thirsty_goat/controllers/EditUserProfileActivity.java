package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;
import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.UserManager;

import java.util.HashMap;
import java.util.Map;

import static com.goat.thirsty_goat.controllers.MapsActivity.LATITUDE_MESSAGE;
import static com.goat.thirsty_goat.controllers.MapsActivity.LONGITUDE_MESSAGE;


/**
 * This Activity handles the main edit user profile screen and allows the user to
 * update their name and email address. It also currently functions as our main screen.
 */
//TODO: make this not our main screen
public class EditUserProfileActivity extends AppCompatActivity {

    private EditText mUserNameField;
    private EditText mEmailField;
    private Spinner mAccountTypeSpinner;
    private Button mHistoryButton;
    private Auth0 mAuth0;
    private AuthenticationAPIClient mClient;
    private UserProfile mUserProfile;
    private ModelFacade mFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        mFacade = ModelFacade.getInstance();

        // Set up Auth0 main object and client
        mAuth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        mClient = new AuthenticationAPIClient(mAuth0);
        mClient.tokenInfo(mFacade.getUserID())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(final UserProfile payload) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mUserProfile = payload;
                                mFacade.updateUserProfile(mClient);
                                updateFields();
                            }
                        });
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(EditUserProfileActivity.this,
                                        "Profile Request Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


        // Get dialog widgets
        mUserNameField = (EditText) findViewById(R.id.user_name);
        mEmailField = (EditText) findViewById(R.id.email);
        mAccountTypeSpinner = (Spinner) findViewById(R.id.account_type_spinner);

        // Set up adapter to select account type
        ArrayAdapter<UserManager.AccountType> accountTypeAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UserManager.AccountType.values());
        accountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccountTypeSpinner.setAdapter(accountTypeAdapter);

        mHistoryButton = (Button) findViewById(R.id.graph_button); //button id
        if (mAccountTypeSpinner.getSelectedItem().toString().equals("Manager")) {
            mHistoryButton.setVisibility(View.VISIBLE);
        } else {
            mHistoryButton.setVisibility(View.GONE);
        }
        Log.d("report*****",  mAccountTypeSpinner.getSelectedItem().toString());


        // Set default field values
        mFacade.updateUserProfile(mClient);
        updateFields();

    }
    public void onAddReportClicked(View view) {
        // this line clears any unsaved changes they may have made from appearing in the text fields
        updateFields();

        switchToAddReportView();
    }

    public void onCancelClicked(View view) {
        updateFields();
    }

    public void onMapClicked(View view) {
        // this line clears any unsaved changes they may have made from appearing in the text fields
        updateFields();

        switchToMapView();
    }

    public void onSaveClicked(View view) {
        saveProfile();

        // TODO: remove this method call; only for testing
//                mFacade.makeDummyReports();
    }

    public void onViewReportListClicked(View view) {
        // this line clears any unsaved changes they may have made from appearing in the text fields
        updateFields();
        switchToReportListView(view);
    }

    private void updateFields() {
        mUserNameField.setText(mFacade.getUserName());
        mEmailField.setText(mFacade.getUserEmail());
        mAccountTypeSpinner.setSelection(mFacade.getUserAccountTypePosition());

        if (mAccountTypeSpinner.getSelectedItem().toString().equals("Manager")) { //mAccountTypeSpinner.getSelectedItem().toString()
            mHistoryButton.setVisibility(View.VISIBLE);
        } else {
            mHistoryButton.setVisibility(View.GONE);
        }
    }
    private void saveProfile() {
        UsersAPIClient userClient =
                new UsersAPIClient(mAuth0, mFacade.getUserID());
        Map<String, Object> userMetadata = new HashMap<>();
        userMetadata.put("account_type", mAccountTypeSpinner.getSelectedItem().toString());
        userMetadata.put("email", mEmailField.getText().toString());
        userMetadata.put("name", mUserNameField.getText().toString());
        userClient.updateMetadata(mUserProfile.getId(), userMetadata)
                .start(new BaseCallback<UserProfile, ManagementException>() {
                    @Override
                    public void onSuccess(final UserProfile payload) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mUserProfile = payload;
                                mFacade.updateUserProfile(mClient);
                                if (mAccountTypeSpinner.getSelectedItem().toString().equals("Manager")) { //mAccountTypeSpinner.getSelectedItem().toString()
                                    mHistoryButton.setVisibility(View.VISIBLE);
                                } else {
                                    mHistoryButton.setVisibility(View.GONE);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(ManagementException error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(EditUserProfileActivity.this, "Profile Request Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }

    private void switchToMapView() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void switchToAddReportView() {
        Intent intent = new Intent(this, SourceReportActivity.class);
        intent.putExtra(LATITUDE_MESSAGE, 33.75);
        intent.putExtra(LONGITUDE_MESSAGE, 84.39);
        startActivity(intent);
    }

    private void switchToReportListView(View view) {
        Intent intent = new Intent(this, SourceReportListActivity.class);
        startActivity(intent);
    }

    public void onHistoryGraphButtonClicked(View view) {
        Intent intent = new Intent(this, ViewHistoricalReportActivity.class);
        startActivity(intent);
    }
}
