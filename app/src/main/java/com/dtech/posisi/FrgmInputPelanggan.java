package com.dtech.posisi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by ADIST on 10/27/2015.
 */
public class FrgmInputPelanggan extends Fragment {

    private EditText etCode;
    private EditText etName;
    private EditText etAddress;
//    private EditText lastXPosition;
//    private EditText lastYPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input_pelanggan, container, false);
        setEtCode((EditText) rootView.findViewById(R.id.textCustID));
        setEtName((EditText) rootView.findViewById(R.id.textCustName));
        setEtAddress((EditText) rootView.findViewById(R.id.textCustAddress));
        return rootView;
    }

    public EditText getEtCode() {
        return etCode;
    }

    public void setEtCode(EditText etCode) {
        this.etCode = etCode;
    }

    public EditText getEtName() {
        return etName;
    }

    public void setEtName(EditText etName) {
        this.etName = etName;
    }

    public EditText getEtAddress() {
        return etAddress;
    }

    public void setEtAddress(EditText etAddress) {
        this.etAddress = etAddress;
    }
}
