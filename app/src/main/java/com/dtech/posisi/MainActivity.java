package com.dtech.posisi;

;
import android.content.Intent;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.dtech.orm.Customer;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private android.support.v7.widget.Toolbar tool;

    private RecyclerView recyclerView;
    private MainListAdapter adapter;
    private MainCustomerAdapter adaptCustomer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adaptCustomer = new MainCustomerAdapter(getDataCust());
        recyclerView=(RecyclerView)findViewById(R.id.mList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptCustomer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        tool= (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        tool.setTitle(getString(R.string.app_name));
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavDrawerFragment drawerFragment=(NavDrawerFragment)
               getSupportFragmentManager().findFragmentById(R.id.nav_drawer);
        drawerFragment.setUp(R.id.nav_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), tool);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // RELOAD recyclerView
        adaptCustomer = new MainCustomerAdapter(getDataCust());
        recyclerView=(RecyclerView)findViewById(R.id.mList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptCustomer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return true;
    }


    public static List<Information> getDataCust() {

        Customer mCustomer = new Customer();
        List<Information> listCustomer = new ArrayList<>();
        List<Customer> customer = Customer.listAll(Customer.class);
        for (Customer cust : customer) {
            Information setCust = new Information();
            setCust.nama = cust.getcode() + "; " + cust.getname();
            setCust.address = cust.getaddress();
            setCust.latTude = cust.getLatTude();
            setCust.longTude = cust.getLongTude();
            listCustomer.add(setCust);
        }
        return listCustomer;
    }

    public static List<Information> getDatamain() {
        //Customer dataCustomer = new Customer();
        //List<Customer> dataCust = new ArrayList<Customer>();
        List<Information> mdata = new ArrayList<>();
        int[] icons = {R.drawable.ic_pict_icon, R.drawable.ic_pict_icon, R.drawable.ic_pict_icon,
                R.drawable.ic_pict_icon};
        String[] title = {"This is the info for Customer 1\n\nIncluding :\nName\nAddress\nGPS Location\nand other info that you want",
                "This is the info for Customer 2\n\nIncluding :\nName\nAddress\nGPS Location\nand other info that you want",
                "This is the info for Customer 3\n\nIncluding :\nName\nAddress\nGPS Location\nand other info that you want",
                "This is the info for Customer 4\n\nIncluding :\nName\nAddress\nGPS Location\nand other info that you want"
        };

        //String[] customer=  new String[] {/*ini nanti di isi dengan data yang ada di Customer.java*/}



        for (int i = 0; i < title.length && i < icons.length; i++) {
            Information current = new Information();
            current.mainTitle = title[i];
            current.mainIconId = icons[i];
            mdata.add(current);
        }
        return mdata;
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
            startActivity(new Intent(this, Main2Activity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
