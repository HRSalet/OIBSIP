package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner unitTypeSpinner;
    private Spinner subUnitSpinner;
    private EditText inputValueEditText;
    private TextView resultTextView;
    private androidx.appcompat.widget.AppCompatButton convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unitTypeSpinner = findViewById(R.id.unitTypeSpinner);
        subUnitSpinner = findViewById(R.id.subUnitSpinner);
        inputValueEditText = findViewById(R.id.inputValueEditText);
        resultTextView = findViewById(R.id.resultTextView);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(this,
                R.array.unit_types, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitTypeSpinner.setAdapter(unitAdapter);

        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                populateSubUnits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnit();
            }
        });
    }

    private void populateSubUnits() {
        String unitType = unitTypeSpinner.getSelectedItem().toString();
        ArrayAdapter<CharSequence> subUnitAdapter = null;

        switch (unitType) {
            case "Weight":
                subUnitAdapter = ArrayAdapter.createFromResource(this,
                        R.array.weight_units, android.R.layout.simple_spinner_item);
                break;
            case "Length":
                subUnitAdapter = ArrayAdapter.createFromResource(this,
                        R.array.length_units, android.R.layout.simple_spinner_item);
                break;
        }

        if (subUnitAdapter != null) {
            subUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subUnitSpinner.setAdapter(subUnitAdapter);
            subUnitSpinner.setVisibility(View.VISIBLE);
        } else {
            subUnitSpinner.setVisibility(View.GONE);
        }
    }

    public void convertUnit() {
        String unitType = unitTypeSpinner.getSelectedItem().toString();
        String subUnit = subUnitSpinner.getSelectedItem().toString();
        double inputValue = 0;

        try {
            inputValue = Double.parseDouble(inputValueEditText.getText().toString());
        } catch (NumberFormatException e) {
            resultTextView.setText("Invalid input value");
            return;
        }

        double result = 0;

        switch (unitType) {
            case "Weight":

                if (subUnit.equals("Grams")) {
                    result = inputValue;
                } else if (subUnit.equals("Kilograms")) {
                    result = inputValue * 1000;
                } else if (subUnit.equals("Pounds")) {
                    result = inputValue * 453.592;
                }
                break;
            case "Length":

                if (subUnit.equals("Meters")) {
                    result = inputValue;
                } else if (subUnit.equals("Kilometers")) {
                    result = inputValue * 1000;
                } else if (subUnit.equals("Miles")) {
                    result = inputValue * 1609.34;
                }
                break;
        }

        resultTextView.setText(String.format("%.2f", result));
    }
}
