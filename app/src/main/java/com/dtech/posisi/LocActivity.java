package com.dtech.posisi;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.common.api.GoogleApiClient;

public class LocActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    public TextView mLat;
    public TextView mLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc);

        buildGoogleApiClient();
         mLat=(TextView)findViewById(R.id.lastXPosition);
         mLong=(TextView)findViewById(R.id.lastYPosition);

        Button bGet=(Button)findViewById(R.id.get);
        bGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if(lastLocation!=null){
                    mLat.setText(String.valueOf(lastLocation.getLatitude()));
                    mLong.setText(String.valueOf(lastLocation.getLongitude()));
                }*/
            }
        });
    }

    protected synchronized void buildGoogleApiClient(){
       mGoogleApiClient=new GoogleApiClient.Builder(this)
               .addConnectionCallbacks(this)
               .addOnConnectionFailedListener(this)
               .addApi(LocationServices.API)
               .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(lastLocation!=null){
            mLat.setText(String.valueOf(lastLocation.getLatitude()));
            mLong.setText(String.valueOf(lastLocation.getLongitude()));
        } else{
            Toast.makeText(this, "gagal nyari lokasi", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {


    }
}
