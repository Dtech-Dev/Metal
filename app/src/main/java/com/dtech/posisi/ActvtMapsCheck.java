package com.dtech.posisi;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.dtech.orm.DefaultOps;
import com.dtech.orm.MapsHandler;
import com.dtech.orm.MtlPelanggan;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActvtMapsCheck extends FragmentActivity
        implements LocationListener
        , GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener {

//    public static final String TAG = ActvtMapsCheck.class.getSimpleName();

    private Map<String, MarkerOptions> markerColections;
    private int circleRadius;
    private MapsHandler mapsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_maps_customer);
        mapsHandler = new MapsHandler(this, this, this, this, R.id.mapCustomers);
        setMarkers();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }

    private void showMenu() {

    }

    private void setMarkers() {
        Bundle passedIntent = getIntent().getExtras();
        List<MtlPelanggan> customers = new ArrayList<>();
        if (passedIntent != null) {
            MtlPelanggan selectedCustomer = (MtlPelanggan) getIntent()
                    .getExtras().get("selectedCustomer");
            customers.add(selectedCustomer);
        }

        if (customers.size() <= 0){
            customers.clear();
            customers = MtlPelanggan.find(MtlPelanggan.class, "active = ?", "1");
        }

        circleRadius = DefaultOps.DEFAULT_RADIUS;
        markerColections = new HashMap<>();
        for (MtlPelanggan cust : customers) {
            String[] lastLatLong = cust.getLastLatLong();
            if (lastLatLong == null)
                continue;
            double lat = Double.parseDouble(lastLatLong[0]);
            double lon = Double.parseDouble(lastLatLong[1]);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(cust.getCode() + ":" + cust.getName())
                    .icon(BitmapDescriptorFactory.
                            fromResource(R.drawable.ic_person_pin_black_24dp));
            markerColections.put(cust.getCode(), markerOptions);
        }

        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(mapsHandler.getCurrentLattitude()
                        , mapsHandler.getCurrentLongitude()))
                .radius(circleRadius)
                .strokeColor(0xff009688)
                .strokeWidth(DefaultOps.DEFAULT_MAPS_STROKE_WIDTH)
                .fillColor(0x80B2DFDB);
        mapsHandler.getGoogleMap().addCircle(circleOptions);
        mapsHandler.setMarkerPoints(markerColections, circleOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapsHandler = new MapsHandler(this, this, this, this, R.id.mapCustomers);
        mapsHandler.getGoogleApiClient().connect();
        setMarkers();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (mapsHandler.getGoogleApiClient().isConnected()){
            LocationServices
                    .FusedLocationApi
                    .removeLocationUpdates(mapsHandler.getGoogleApiClient(), this);
            mapsHandler.getGoogleApiClient().disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mapsHandler.onLocationChanged(location);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mapsHandler.onConnected(bundle);
        setMarkers();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mapsHandler.onConnectionSuspended(i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mapsHandler.onConnectionFailed(connectionResult);
    }
}
