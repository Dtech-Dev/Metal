package com.dtech.posisi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.adapters.AdapterMtlPelanggan;
import com.dtech.orm.DefaultOps;
import com.dtech.orm.MtlPelanggan;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//re

public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener{

    private GoogleApiClient googleApiClient;
    private boolean mShouldResolve = false;
    private boolean mIsResolving = false;
    private TextView mStatus;
    public static String TAG = MainActivity.class.getSimpleName();
    private android.support.v7.widget.Toolbar tool;
    private RecyclerView recyclerView;
    //old Adapter
    private MainCustomerAdapter adaptCustomer;

    //Current Adapter
    private AdapterMtlPelanggan myadapter;

    private List<MtlPelanggan> listcustomer;


    MtlPelanggan dataCustomer;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mIsResolving = savedInstanceState.getBoolean(DefaultOps.KEY_IS_RESOLVING);
            mShouldResolve = savedInstanceState.getBoolean(DefaultOps.KEY_SHOULD_RESOLVE);
        }

        setupAPIs();

        new RequestHttp().execute();

        mStatus = (TextView) findViewById(R.id.textSignIn);

        setRecyleView();
        //==================================================
        //Untuk load data, ketika MainActivity create, manggil class HttpTask
        //namun, ketika selesai input customer baru dan balik k main activity, recyclerview blm ng'refresh data yg ada d server
        new HttpTask().execute(DefaultOps.URL_CUSTOMER);

        //==================================================
        tool= (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        tool.setTitle(getString(R.string.app_name));
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavDrawerFragment drawerFragment=(NavDrawerFragment)
               getSupportFragmentManager().findFragmentById(R.id.nav_drawer);
        drawerFragment.setUp(R.id.nav_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), tool);

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.ic_add);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, InputCustomerActivity.class);*/
                Intent intent = new Intent(MainActivity.this, CekInsertPhp.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    protected synchronized void setupAPIs() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        checkGPSSettings();
    }

    private void showSignedInUI() {
        updateUI(true);
    }

    public void showSignedOutUI() {

    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(googleApiClient);
            if (currentPerson != null) {
                String nameUser = currentPerson.getDisplayName();
                mStatus.setText(nameUser);
                if (checkAccountsPermission()) {
                    String currentAccount = Plus.AccountApi.getAccountName(googleApiClient);
                    ((TextView) findViewById(R.id.textEmail)).setText(currentAccount);

                }
            } else {
                Log.w(TAG, "Null Person");
                mStatus.setText("Sign In Error");
            }
        }
        else{
            mStatus.setText("Error");
        }
    }

    private boolean checkAccountsPermission() {
        final String perm = android.Manifest.permission.GET_ACCOUNTS;
        int permissioncheck = ContextCompat.checkSelfPermission(this, perm);
        if (permissioncheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
            Snackbar.make(findViewById(R.id.mainLayout),
                    R.string.contacts_permission_rationale,
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{perm}, DefaultOps.RC_PERM_GET_ACCOUNTS);
                }
            }).show();
            return false;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{perm}, DefaultOps.RC_PERM_GET_ACCOUNTS);
            return false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DefaultOps.KEY_IS_RESOLVING, mIsResolving);
        outState.putBoolean(DefaultOps.KEY_SHOULD_RESOLVE, mShouldResolve);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == DefaultOps.RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }
            mIsResolving = false;
            googleApiClient.connect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionResult:" + requestCode);
        if (requestCode == DefaultOps.RC_PERM_GET_ACCOUNTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showSignedInUI();
            } else {
                Log.d(TAG, "Permission Denied");
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected:" + bundle);

        mShouldResolve = false;
        showSignedInUI();

        Log.i(TAG, "Connected to GoogleApiClient");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, DefaultOps.RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Couldn't resolve Connection", e);
                    mIsResolving = false;
                    googleApiClient.connect();
                }

            } else {
                showErrorDialog(connectionResult);
            }

        } else {

        }
    }

    private void showErrorDialog(ConnectionResult connectionResult) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, DefaultOps.RC_SIGN_IN,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                mShouldResolve = false;
                                showSignedOutUI();
                            }
                        }).show();
            } else {
                Log.w(TAG, "Google Play Services Error:" + connectionResult);
                String errorString = apiAvailability.getErrorString(resultCode);
                Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();

                mShouldResolve = false;
                showSignedOutUI();
            }
        }
    }

    private void setRecyleView() {
        // look! actually we dont really need any adapter. ^_^
        /*adaptCustomer = new MainCustomerAdapter(Customer.listAll(Customer.class));
        recyclerView=(RecyclerView)findViewById(R.id.mList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptCustomer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        myadapter = new AdapterMtlPelanggan(this, listcustomer);
        recyclerView=(RecyclerView)findViewById(R.id.mList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void checkGPSSettings() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Dialog dialogs = new AlertDialog.Builder(this)
                    .setTitle("GPS Service In-Active")
                    .setMessage("Enabling GPS Services?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intentGPS);
                        }
                    }).create();
            dialogs.setCanceledOnTouchOutside(false);
            dialogs.show();
        }
    }

    private void onSignInClicked() {
        mShouldResolve = true;
        googleApiClient.connect();
        mStatus.setText("Success");
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public class RequestHttp extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://droidsense.web.id/metal/index.php");
                //httpPost.setEntity(new UrlEncodedFormEntity(values));
                HttpResponse response = httpClient.execute(httpPost);
                //HttpEntity entity = response.getEntity();
                response.getEntity();


            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
           // Toast.makeText(MainActivity.this, "Success Update Data", Toast.LENGTH_SHORT).show();
        }
    }

    public class HttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }



        @Override
        protected Integer doInBackground(String... params) {


            Integer result=0;
            HttpURLConnection urlConnection;

            try {
                URL url = new URL(params[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);

                    }
                    ambilData(response.toString());
                    result = 1;
                } else {
                    result = 0;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressDialog.dismiss();
            if (result == 1) {
                setRecyleView();
            } else {
                Toast.makeText(MainActivity.this, "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ambilData(String result) {
        try {
            JSONArray posts = new JSONArray(result);
            listcustomer = new ArrayList<>();
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                dataCustomer = new MtlPelanggan();
                dataCustomer.setName(post.getString("name"));
                dataCustomer.setAddress(post.getString("address"));
                listcustomer.add(dataCustomer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // RELOAD recyclerView
        //setRecyleView();
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
            Toast.makeText(MainActivity.this, "Menu Setting has been clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id==R.id.nav) {
            onSignInClicked();
            //startActivity(new Intent(this, InputCustomerActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setRecyleView();
        new RequestHttp().execute();

        //ambilData();
        //setRecyleView();
        //new HttpTask().execute(URL_CUSTOMER);
    }
}
