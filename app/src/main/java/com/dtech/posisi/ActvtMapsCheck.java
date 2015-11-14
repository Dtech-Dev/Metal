package com.dtech.posisi;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.dtech.orm.DefaultOps;
import com.dtech.orm.MtlPelanggan;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActvtMapsCheck extends FragmentActivity implements
        LocationListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //private Location location;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Marker usrMarker;

    public static final String TAG = ActvtMapsCheck.class.getSimpleName();

    private Map<String, MarkerOptions> markerColections;
    private int circleRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_maps_customer);

        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mLocationRequest=LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1000);
        circleRadius = DefaultOps.DEFAULT_RADIUS;
        setMarkers();
        setUpMapIfNeeded();

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapCustomers))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location Service Connected");
        Location location=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location==null){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient
                    , mLocationRequest, this);
        } else{
            handleNewLocation(location);
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng currentLatLang = new LatLng(currentLatitude, currentLongitude);
        MarkerOptions options = new MarkerOptions().position(currentLatLang).title("Anda disini.");

        if (usrMarker==null){
            usrMarker=mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLang));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(DefaultOps.DEFAULT_MAPS_ZOOM));
        } else{
            usrMarker.remove();
            usrMarker=mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLang));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(DefaultOps.DEFAULT_MAPS_ZOOM));
        }

        CircleOptions circleOptions = new CircleOptions()
                .center(currentLatLang)
                .radius(circleRadius)
                .strokeColor(0xff009688)
                .strokeWidth(DefaultOps.DEFAULT_MAPS_STROKE_WIDTH)
                .fillColor(0x80B2DFDB);
        mMap.addCircle(circleOptions);

        // set Markers
        for (String code : markerColections.keySet()){
            MarkerOptions mc = markerColections.get(code);
            float[] distance = new float[2];
            double[] endCoordinat = new double[]{
                    mc.getPosition().latitude,
                    mc.getPosition().longitude
            };
            Location.distanceBetween(currentLatitude, currentLongitude
                    , endCoordinat[0], endCoordinat[1]
                    , distance);
            if (distance[0] > circleOptions.getRadius())
                continue;
            mMap.addMarker(markerColections.get(code));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Suspended, please reconnect");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            try{
                connectionResult.startResolutionForResult(this
                        , DefaultOps.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(TAG, "Location services connection failed with code "
                    + connectionResult.getErrorCode());
        }
    }
}
