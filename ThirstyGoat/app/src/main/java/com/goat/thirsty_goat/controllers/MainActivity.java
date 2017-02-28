package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.goat.thirsty_goat.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginAgainButton = (Button) findViewById(R.id.login_again);
        loginAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAgain();
            }
        });

        Button editProfileButton = (Button) findViewById(R.id.editprofile);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileHandler();
            }
        });

    }
    private void loginAgain() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void editProfileHandler() {
        startActivity(new Intent(this, EditUserProfileActivity.class));
        finish();
    }
}
