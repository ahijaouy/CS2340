package com.goat.thirsty_goat.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.WaterPurityCondition;

/**
 * Created by Ben on 3/16/2017.
 *
 * This activity handles the user submitting a new water purity report
 */

public class WaterPurityReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mVirusPPMEditText, mContaminantPPMEditText;
    private Spinner mOverallConditionSpinner;
    private Button mCancelButton, mSubmitButton;

    private WaterPurityCondition mWaterOverallCondition;
    private double mVirusPPM;
    private double mContaminantPPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_water_purity_report);

        mSubmitButton = (Button) findViewById(R.id.submit_purity_report);
        mCancelButton = (Button) findViewById(R.id.cancel_purity_report);
        mVirusPPMEditText = (EditText) findViewById(R.id.virus_text);
        mContaminantPPMEditText = (EditText) findViewById(R.id.contaminant_text);
        mOverallConditionSpinner = (Spinner) findViewById(R.id.condition_spinner);

        ArrayAdapter<WaterPurityCondition> waterOverallConditionAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterPurityCondition.values());
        waterOverallConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOverallConditionSpinner.setAdapter(waterOverallConditionAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mWaterOverallCondition = (WaterPurityCondition) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mWaterOverallCondition = WaterPurityCondition.UNSAFE;
    }

    protected void onSubmitPressed() {
        mVirusPPM = Double.parseDouble(mVirusPPMEditText.getText().toString());
        mContaminantPPM = Double.parseDouble(mContaminantPPMEditText.getText().toString());
        mWaterOverallCondition = (WaterPurityCondition) mOverallConditionSpinner.getSelectedItem();

        ModelFacade mFacade = ModelFacade.getInstance();
        mFacade.addWaterPurityReport(mWaterOverallCondition, mVirusPPM, mContaminantPPM);

        finish();
    }

    protected void onCancelPressed() {
        finish();
    }
}
