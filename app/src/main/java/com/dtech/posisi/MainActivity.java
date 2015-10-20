package com.dtech.posisi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dtech.orm2.MtlPelanggan;
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

public class MainActivity extends ActionBarActivity {

    public static String TAG = MainActivity.class.getSimpleName();
    private android.support.v7.widget.Toolbar tool;
    private RecyclerView recyclerView;
    //old Adapter
    private MainCustomerAdapter adaptCustomer;

    //Current Adapter
    private MainDataCustomerAdapter myadapter;

    //URL untuk ambil data dari json file
    public static final String URL_CUSTOMER = "http://droidsense.web.id/metal/customer.json";
    private List<MtlPelanggan> listcustomer;


    MtlPelanggan dataCustomer;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkingGPS();
        new RequestHttp().execute();


        //setRecyleView();
        //==================================================
        //Untuk load data, ketika MainActivity create, manggil class HttpTask
        //namun, ketika selesai input customer baru dan balik k main activity, recyclerview blm ng'refresh data yg ada d server
        new HttpTask().execute(URL_CUSTOMER);

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


       // icon.setBackgroundResource(R.drawable.custom_fab);
       // FloatingActionButton fab=new FloatingActionButton.Builder(this).setContentView(icon).build();

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

    private void setRecyleView() {
        // look! actually we dont really need any adapter. ^_^
        /*adaptCustomer = new MainCustomerAdapter(Customer.listAll(Customer.class));
        recyclerView=(RecyclerView)findViewById(R.id.mList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptCustomer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        myadapter = new MainDataCustomerAdapter(this, listcustomer);
        recyclerView=(RecyclerView)findViewById(R.id.mList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void checkingGPS() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Location Services Not Active");
            alert.setMessage("Please Enable Location Service and GPS");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intentGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intentGPS);
                }
            });
            Dialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
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
            Toast.makeText(MainActivity.this, "Success Update Data", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
        if(id==R.id.nav){
            startActivity(new Intent(this, InputCustomerActivity.class));
        }

        return super.onOptionsItemSelected(item);
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
