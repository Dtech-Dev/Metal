package com.dtech.posisi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by ADIST on 9/17/2015.
 */
public class SpinnerTarifActivity extends Activity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spinner spinnerTarif = (Spinner) findViewById(R.id.spinnerTarifInput);
        spinnerTarif.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(adapterView.getContext(), "Its your choice" + adapterView.getItemAtPosition(i).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
