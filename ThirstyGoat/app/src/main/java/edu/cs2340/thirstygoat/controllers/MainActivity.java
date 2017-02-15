package edu.cs2340.thirstygoat.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.cs2340.thirstygoat.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void logout(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        System.out.print("pull this");
    }
}
