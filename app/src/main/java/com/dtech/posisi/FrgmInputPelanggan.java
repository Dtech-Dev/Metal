package com.dtech.posisi;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dtech.orm.MapsHandler;
import com.dtech.orm.MtlPelanggan;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * Created by ADIST on 10/27/2015.
 */
public class FrgmInputPelanggan extends Fragment
        implements LocationListener
        , GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener {

//    private static final String TAG = FrgmInputPelanggan.class.getSimpleName();
    private EditText etCode;
    private EditText etName;
    private EditText etAddress;
    private View rootView;
    private MapsHandler mapsHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_input_pelanggan, container, false);
        setEtCode((EditText) rootView.findViewById(R.id.textCustID));
        setEtName((EditText) rootView.findViewById(R.id.textCustName));
        setEtAddress((EditText) rootView.findViewById(R.id.textCustAddress));
        mapsHandler = new MapsHandler(getContext()
                , this, this, this, R.id.mapCustomersInput);
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

    @Override
    public void onConnected(Bundle bundle) {
        mapsHandler.onConnected(bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mapsHandler.onConnectionSuspended(i);
    }

    @Override
    public void onLocationChanged(Location location) {
        mapsHandler.onLocationChanged(location);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mapsHandler.onConnectionFailed(connectionResult);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapsHandler = new MapsHandler(getContext()
                , this, this, this, R.id.mapCustomersInput);
        mapsHandler.getGoogleApiClient().connect();
    }

    @Override
    public void onPause(){
        super.onPause();
        if (mapsHandler.getGoogleApiClient().isConnected()){
            LocationServices
                    .FusedLocationApi
                    .removeLocationUpdates(mapsHandler.getGoogleApiClient(), this);
            mapsHandler.getGoogleApiClient().disconnect();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        getEtCode().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        ((ActvtMainInput) getActivity()).onTextChanged("code", s);
                        List<MtlPelanggan> custs = MtlPelanggan.find(MtlPelanggan.class
                                , "code = ?", s.toString());
                        for (MtlPelanggan cust : custs){
                            getEtName().setText(cust.getName());
                            getEtAddress().setText(cust.getAddress());
                            String[] lastLatLong = cust.getLastLatLong();
                            if (lastLatLong == null || lastLatLong.length <= 0)
                                continue;
                            Location custLocation = new Location("");
                            custLocation.setLatitude(Double.parseDouble(lastLatLong[0]));
                            custLocation.setLongitude(Double.parseDouble(lastLatLong[1]));
                            mapsHandler.handleNewLocation(custLocation, cust.getCode() + ":" + cust.getName());
                        }
                        if (custs.size() <= 0){
                            getEtName().setText(null);
                            getEtAddress().setText(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );

        getEtName().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        ((ActvtMainInput) getActivity()).onTextChanged("name", s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );

        getEtAddress().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        ((ActvtMainInput) getActivity()).onTextChanged("address", s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );
    }
}
