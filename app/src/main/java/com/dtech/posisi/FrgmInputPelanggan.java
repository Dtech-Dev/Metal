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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by ADIST on 10/27/2015.
 */
public class FrgmInputPelanggan extends Fragment implements LocationListener
        , GoogleMap.OnMyLocationButtonClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private EditText etCode;
    private EditText etName;
    private EditText etAddress;
//    private EditText lastXPosition;
//    private EditText lastYPosition;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //private Location location;
    private GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST=9000;
    private LocationRequest mLocationRequest;
    private Marker usrMarker;
    private double currentLatitude;
    private double currentLongitude;
    private double latFromAdapter;
    private double longFromAdapter;
    private double locthr=currentLongitude+0.3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_input_pelanggan, container, false);
        setEtCode((EditText) rootView.findViewById(R.id.textCustID));
        setEtName((EditText) rootView.findViewById(R.id.textCustName));
        setEtAddress((EditText) rootView.findViewById(R.id.textCustAddress));
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mLocationRequest=LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
        setUpMapIfNeeded();
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

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager()
//                    .findFragmentById(R.id.mapCustomersInput))
//                    .getMap();
            SupportMapFragment mapFragment =
                    ((SupportMapFragment) getFragmentManager()
                            .findFragmentById(R.id.mapCustomersInput));
            if (mapFragment == null){
                Toast.makeText(getContext(), "Can't load Maps! "
                        , Toast.LENGTH_SHORT).show();
            } else {
                mMap = mapFragment.getMap();
            }
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            } else {

            }
        }
    }

    private void setUpMap() {
        /*lat=location.getLatitude();
        longi = location.getLongitude();*/
        // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
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
