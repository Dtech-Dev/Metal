package com.dtech.posisi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by ADIST on 9/17/2015.
 */
public class InputCustomerActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_customer);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.barInputCust);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Spinner spinnerTarif = (Spinner) findViewById(R.id.spinnerTarif);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_tarif, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTarif.setAdapter(adapter);
    }
}
