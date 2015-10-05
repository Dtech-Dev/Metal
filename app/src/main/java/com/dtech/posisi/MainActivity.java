package com.dtech.posisi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dtech.orm.Customer;

public class MainActivity extends ActionBarActivity {

    private android.support.v7.widget.Toolbar tool;
    private RecyclerView recyclerView;
    private MainCustomerAdapter adaptCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkingGPS();
        setRecyleView();

        tool= (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        tool.setTitle(getString(R.string.app_name));
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavDrawerFragment drawerFragment=(NavDrawerFragment)
               getSupportFragmentManager().findFragmentById(R.id.nav_drawer);
        drawerFragment.setUp(R.id.nav_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), tool);

    }

    private void setRecyleView() {
        // look! actually we dont really need any adapter. ^_^
        adaptCustomer = new MainCustomerAdapter(Customer.listAll(Customer.class));
        recyclerView=(RecyclerView)findViewById(R.id.mList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptCustomer);
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
        setRecyleView();
    }
}
