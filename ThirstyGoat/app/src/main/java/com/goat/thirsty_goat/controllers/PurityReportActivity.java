package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    private Location mLocation;
//    PurityCondition mWaterOverallCondition;
    private ModelFacade mFacade = ModelFacade.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_water_purity_report);

        mFacade = ModelFacade.getInstance();

        mVirusPPMEditText = (EditText) findViewById(R.id.virus_text);
        mContaminantPPMEditText = (EditText) findViewById(R.id.contaminant_text);
        mOverallConditionSpinner = (Spinner) findViewById(R.id.condition_spinner);

        ArrayAdapter<PurityCondition> waterOverallConditionAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PurityCondition.values());
        waterOverallConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOverallConditionSpinner.setAdapter(waterOverallConditionAdapter);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mLocation = new Location((double) extras.get("lat"), (double) extras.get("long"));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        mWaterOverallCondition = (PurityCondition) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        mWaterOverallCondition = PurityCondition.UNSAFE;
    }

    public void onSubmitPressed(View view) {
        double virusPPM = Double.parseDouble(mVirusPPMEditText.getText().toString());
        double contaminantPPM = Double.parseDouble(mContaminantPPMEditText.getText().toString());
        PurityCondition purityCondition = (PurityCondition) mOverallConditionSpinner.getSelectedItem();

        mFacade.addPurityReport(purityCondition, virusPPM, contaminantPPM, mLocation);

        finish();
    }

    public void onCancelPressed(View view) {
        finish();
    }
}
