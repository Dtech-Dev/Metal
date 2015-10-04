package com.dtech.posisi;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

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

public class CekMapsActivity extends FragmentActivity implements
        LocationListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

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

    String latHandled;
    String longHandled;


    public static final String TAG=CekMapsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_maps);

        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mLocationRequest=LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);


        setUpMapIfNeeded();
        intentFromAdapter();
        convertingStringFromAdapter();
    }

    private void intentFromAdapter() {
        latHandled=getIntent().getExtras().getString("intentLat");
        longHandled = getIntent().getExtras().getString("intentLong");
        //Toast.makeText(this,"lat= "+latHandled+", long= "+longHandled,Toast.LENGTH_SHORT).show();
    }

    private void convertingStringFromAdapter() {
        latFromAdapter = Double.parseDouble(latHandled);
        longFromAdapter = Double.parseDouble(longHandled);
        /*try {
            latFromAdapter = Double.parseDouble(latHandled);
            longFromAdapter = Double.parseDouble(longHandled);
            Toast.makeText(this,"success converting", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            Toast.makeText(this,"failed converting", Toast.LENGTH_SHORT).show();
        }*/
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
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
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
        /*lat=location.getLatitude();
        longi = location.getLongitude();*/

      // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        mMap.setMyLocationEnabled(true);



    }

    @Override
    public boolean onMyLocationButtonClick() {
        /*LocationManager locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria=new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        if (latitude==0 && longitude==0 ){
            Toast.makeText(CekMapsActivity.this,"Null", Toast.LENGTH_SHORT).show();
        }
        else{Toast.makeText(CekMapsActivity.this,
                "Lat = "+latitude+" long = "+longitude,Toast.LENGTH_SHORT).show();}*/
       // mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Here"));
       // LatLng latLng = new LatLng(latitude, longitude);

        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
       /* usrMarker=mMap.addMarker((new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude))
        .title("you're here")));

        if (usrMarker==null){
            handleNewLocation(location);
        } else{
            usrMarker.remove();
            handleNewLocation(location);
        }*/
    }

    @Override
    public void onConnected(Bundle bundle) {

        Log.i(TAG, "Location Service Connected");
        Location location=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if (location==null){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else{
            handleNewLocation(location);
        }


    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

         currentLatitude=location.getLatitude();
         currentLongitude=location.getLongitude();

        LatLng latLng=new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options=new MarkerOptions().position(latLng).title("You're Here");


        if (usrMarker==null){
            usrMarker=mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(11));
        } else{
            usrMarker.remove();
            usrMarker=mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(11));
        }

        CircleOptions circleOptions=new CircleOptions()
                .center(new LatLng(currentLatitude, currentLongitude))
                .radius(10000)
                .strokeColor(0xff009688)
                .strokeWidth(10)
                .fillColor(0x80B2DFDB);
        mMap.addCircle(circleOptions);

        mMap.addMarker(new MarkerOptions().position(new LatLng(latFromAdapter, longFromAdapter)).title("Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_black_24dp)));
       /* mMap.addMarker(new MarkerOptions().position(new LatLng((currentLatitude)+0.003, (currentLongitude)+0.003)).title("Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_black_24dp)));
        mMap.addMarker(new MarkerOptions().position(new LatLng((currentLatitude)+0.005, currentLongitude)).title("Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_black_24dp)));*/
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.i(TAG, "Suspended, please reconnect");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            try{
                connectionResult.startResolutionForResult(this,CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(TAG, "Location services connection failed with code "+connectionResult.getErrorCode());
        }
    }
}
