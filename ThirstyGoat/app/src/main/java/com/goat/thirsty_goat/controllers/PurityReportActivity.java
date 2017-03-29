package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.PurityCondition;

/**
 * Created by Ben on 3/16/2017.
 *
 * This activity handles the user submitting a new water purity report
 */

public class PurityReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mVirusPPMEditText, mContaminantPPMEditText;
    private Spinner mOverallConditionSpinner;
    private Button mCancelButton, mSubmitButton;

    private PurityCondition mWaterOverallCondition;
    private double mVirusPPM;
    private double mContaminantPPM;

    private Location loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_water_purity_report);

        mSubmitButton = (Button) findViewById(R.id.submit_purity_report);
        mCancelButton = (Button) findViewById(R.id.cancel_purity_report);
        mVirusPPMEditText = (EditText) findViewById(R.id.virus_text);
        mContaminantPPMEditText = (EditText) findViewById(R.id.contaminant_text);
        mOverallConditionSpinner = (Spinner) findViewById(R.id.condition_spinner);

        ArrayAdapter<PurityCondition> waterOverallConditionAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, PurityCondition.values());
        waterOverallConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOverallConditionSpinner.setAdapter(waterOverallConditionAdapter);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        loc = new Location((double) extras.get("lat"), (double) extras.get("long"));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mWaterOverallCondition = (PurityCondition) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mWaterOverallCondition = PurityCondition.UNSAFE;
    }

    protected void onSubmitPressed(View view) {
        mVirusPPM = Double.parseDouble(mVirusPPMEditText.getText().toString());
        mContaminantPPM = Double.parseDouble(mContaminantPPMEditText.getText().toString());
        mWaterOverallCondition = (PurityCondition) mOverallConditionSpinner.getSelectedItem();

        ModelFacade mFacade = ModelFacade.getInstance();
        mFacade.addWaterPurityReport(mWaterOverallCondition, mVirusPPM, mContaminantPPM, loc);

        finish();
    }

    protected void onCancelPressed(View view) {
        finish();
    }
}
