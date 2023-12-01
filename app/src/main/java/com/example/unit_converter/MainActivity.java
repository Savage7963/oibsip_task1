package com.example.unit_converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText input;
    Spinner unitCategorySpinner;
    Spinner conversionSpinner;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        unitCategorySpinner = findViewById(R.id.unitCategorySpinner);
        conversionSpinner = findViewById(R.id.conversionSpinner);
        resultTextView = findViewById(R.id.resultTextView);

        // Populate the unit category spinner with data from string-array
        ArrayAdapter<CharSequence> unitCategoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.unit_categories, android.R.layout.simple_spinner_item);
        unitCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitCategorySpinner.setAdapter(unitCategoryAdapter);

        // Set a listener for the unit category spinner
        unitCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Load the corresponding conversion spinner based on the selected category
                loadConversionSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        // Set a listener for the conversion spinner
        conversionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Update the result when a conversion is selected
                updateResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        // Set a listener for the input field
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the result whenever the input text changes
                updateResult();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });
    }

    // Load the conversion spinner based on the selected category
    private void loadConversionSpinner(int categoryPosition) {
        int conversionArrayResourceId = 0;

        // Determine which conversion string array to load based on the selected category
        switch (categoryPosition) {
            case 0:
                conversionArrayResourceId = R.array.length_conversions;
                break;
            case 1:
                conversionArrayResourceId = R.array.time_conversions;
                break;
            case 2: // Index 2 corresponds to the "Weight" category
                conversionArrayResourceId = R.array.weight_conversions;
                break;
            // Add more cases for other categories if needed
        }

        if (conversionArrayResourceId != 0) {
            // Populate the conversion spinner with data from the selected conversion string array
            ArrayAdapter<CharSequence> conversionAdapter = ArrayAdapter.createFromResource(this,
                    conversionArrayResourceId, android.R.layout.simple_spinner_item);
            conversionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            conversionSpinner.setAdapter(conversionAdapter);
        }
    };

    // Update the result based on the selected conversion and input value
    private void updateResult() {
        // Get the selected conversion and input value
        String selectedConversion = (String) conversionSpinner.getSelectedItem();
        String inputValueStr = input.getText().toString();

        if (!inputValueStr.isEmpty()) {
            double inputValue = Double.parseDouble(inputValueStr);
            double result = 0.0; // Initialize the result

            // Perform the appropriate conversion based on the selected conversion
            if (selectedConversion != null) {
                switch (selectedConversion) {
                    // Perform conversions based on selected conversion
                    case "Meters to Feet":
                        result = inputValue * 3.28084;
                        break;
                    case "Feet to Meters":
                        result = inputValue * 0.3048;
                        break;
                    case "Seconds to Minutes":
                        result = inputValue / 60;
                        break;
                    case "Minutes to Seconds":
                        result = inputValue * 60;
                        break;
                    case "Kilo to Gram":
                        // Conversion logic: Kilograms to Grams
                        result = inputValue * 1000;
                        break;
                    case "Gram to Kilo":
                        // Conversion logic: Grams to Kilograms
                        result = inputValue / 1000;
                        break;
                    // Add more cases for other conversions as needed
                }
            }

            // Update the result TextView with the calculated result
            resultTextView.setText(String.valueOf(result));
        } else {
            // Clear the result TextView when the input is empty
            resultTextView.setText("");
        }
    }
}
