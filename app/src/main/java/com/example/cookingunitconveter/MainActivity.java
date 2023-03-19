package com.example.cookingunitconveter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText fromEditText, toEditText;
    private Spinner unitSpinner, conversionSpinner;
    private Button convertButton;

    private String[] units = {"Milliliters (ml)", "Fluid ounces (fl oz)", "Grams (g)", "Cups (c)"};
    private String[] conversions = {"ml to fl oz", "fl oz to ml", "g to c", "c to g"};

    private double[][] conversionFactors = {
            {1, 0.033814, 0.00422675, 0.067628},
            {29.5735, 1, 0.125, 2},
            {236.588, 7.93664, 1, 16},
            {14.7868, 0.492127, 0.0625, 1}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromEditText = findViewById(R.id.from_edit);
        toEditText = findViewById(R.id.to_edit);
        unitSpinner = findViewById(R.id.unit_spinner);
        conversionSpinner = findViewById(R.id.conversion_spinner);
        convertButton = findViewById(R.id.convert_button);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);

        ArrayAdapter<String> conversionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, conversions);
        conversionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionSpinner.setAdapter(conversionAdapter);

        unitSpinner.setOnItemSelectedListener(this);
        conversionSpinner.setOnItemSelectedListener(this);

        fromEditText.addTextChangedListener(textWatcher);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convert();
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            convert();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void convert() {
        try {
            double value = Double.parseDouble(fromEditText.getText().toString());
            int fromIndex = unitSpinner.getSelectedItemPosition();
            int toIndex = conversionSpinner.getSelectedItemPosition();
            double result = value * conversionFactors[fromIndex][toIndex];
            toEditText.setText(String.format("%.2f", result));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        convert();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
