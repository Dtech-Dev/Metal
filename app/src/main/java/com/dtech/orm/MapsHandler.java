package com.dtech.orm;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

/**
 * Created by ADIST on 11/19/2015.
 */
public class MapsHandler implements LocationListener
        , GoogleMap.OnMyLocationButtonClickListener
        , GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Marker marker;
    private double currentLattitude;
    private double currentLongitude;

    public MapsHandler(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks
    , GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        setGoogleApiClient(new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .addApi(LocationServices.API)
                .build());
        setLocationRequest(LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * DefaultOps.DEFAULT_FASTEST_INTERVAL)
                .setFastestInterval(DefaultOps.DEFAULT_FASTEST_INTERVAL)
        );
    }

    public MapsHandler(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks
            , GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener
            , FragmentActivity fragmentActivity, int fragmentMapId) {
        this(context, connectionCallbacks, onConnectionFailedListener);
        if (getGoogleMap() == null){
            setGoogleMap(((SupportMapFragment) fragmentActivity
                                    .getSupportFragmentManager()
                                    .findFragmentById(fragmentMapId)
                    ).getMap()
            );
        }
        if (getGoogleMap() != null)
            getGoogleMap().setMyLocationEnabled(true);
    }

    public MapsHandler(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks
            , GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener
            , Fragment fragment, int fragmentMapId) {
        this(context, connectionCallbacks, onConnectionFailedListener);
        if (getGoogleMap() == null){
            setGoogleMap(((SupportMapFragment) fragment
                            .getChildFragmentManager()
                            .findFragmentById(fragmentMapId)
                    ).getMap()
            );
        }
        if (getGoogleMap() != null)
            getGoogleMap().setMyLocationEnabled(true);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location= LocationServices
                .FusedLocationApi.getLastLocation(getGoogleApiClient());
        if (location==null){
            LocationServices
                    .FusedLocationApi
                    .requestLocationUpdates(getGoogleApiClient(), getLocationRequest()
                            , this);
        } else{
            handleNewLocation(location, null);
        }
    }

    public void handleNewLocation(Location location, String title) {
        setCurrentLattitude(location.getLatitude());
        setCurrentLongitude(location.getLongitude());
        if (title == null || title.length() <=0)
            title = "Lokasi anda sekarang.";

        LatLng latLng = new LatLng(getCurrentLattitude(), getCurrentLongitude());
        MarkerOptions options = new MarkerOptions().position(latLng).title(title);

        if (getMarker() != null) {
            getMarker().remove();
        }
        setMarker(getGoogleMap().addMarker(options));
        getGoogleMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));
        getGoogleMap().moveCamera(CameraUpdateFactory.zoomTo(DefaultOps.DEFAULT_MAPS_ZOOM));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location, null);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            // TODO fly your imagine here
//            try{
//                connectionResult.startResolutionForResult(this
//                        , DefaultOps.CONNECTION_FAILURE_RESOLUTION_REQUEST);
//            } catch (IntentSender.SendIntentException e) {
//                e.printStackTrace();
//            }
        }else{
//            Log.i(TAG, "Location services connection failed with code "
//                    + connectionResult.getErrorCode());
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public LocationRequest getLocationRequest() {
        return locationRequest;
    }

    public void setLocationRequest(LocationRequest locationRequest) {
        this.locationRequest = locationRequest;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public double getCurrentLattitude() {
        return currentLattitude;
    }

    public void setCurrentLattitude(double currentLattitude) {
        this.currentLattitude = currentLattitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public void setMarkerPoints(Map<String, MarkerOptions> markerColections
            , CircleOptions circleOptions) {
        if (markerColections == null || markerColections.size() <= 0)
            return;
        // set Markers
        for (String code : markerColections.keySet()){
            MarkerOptions mc = markerColections.get(code);
            float[] distance = new float[2];
            double[] endCoordinat = new double[]{
                    mc.getPosition().latitude,
                    mc.getPosition().longitude
            };
            Location.distanceBetween(getCurrentLattitude(), getCurrentLongitude()
                    , endCoordinat[0], endCoordinat[1]
                    , distance);
            if (circleOptions != null && distance[0] > circleOptions.getRadius())
                continue;
            getGoogleMap().addMarker(markerColections.get(code));
        }
    }

    public LatLng getCurrentLatLng(){
        return new LatLng(getCurrentLattitude(), getCurrentLongitude());
    }
}
