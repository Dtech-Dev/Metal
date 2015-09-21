package com.dtech.posisi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.Databases.MetalDbaseAdapter;
import com.dtech.cam.MetalCamera;
import com.dtech.orm.Customer;
import com.dtech.orm.DatabaseHandler;

import java.util.List;

/**
 * Created by ADIST on 9/17/2015.
 */
 
public class InputCustomerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etCode, etName, etAddress, etFoulType;
    private Spinner spinnerTarif;
    private Button btnSave;
    private Button btnUploadImg;

    MetalDbaseAdapter dbaseHelper;
    
    //update imageview from sdcard
    private static final int SELECT_PICTURE=1;
    private ImageView imagePelanggan;
    int column_index;
    Cursor cursor;
    String imagePath, logo, Logo;
    String selectedImagePath;
    String filemanagerString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_customer);

        final DatabaseHandler dbHandler = new DatabaseHandler(this);

        //dbaseHelper = new MetalDbaseAdapter(this);

        // TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.barInputCust);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // SPINNER
        spinnerTarif = (Spinner) findViewById(R.id.spinnerTarif);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_tarif, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTarif.setAdapter(adapter);

        // ALL EDIT TEXT
        etCode = (EditText) findViewById(R.id.textCustID);
        etName = (EditText) findViewById(R.id.textCustName);
        etAddress = (EditText) findViewById(R.id.textCustAddress);
        etFoulType = (EditText) findViewById(R.id.textJenisPelanggaran);

        // BUTTON SAVE
        btnSave = (Button) findViewById(R.id.btnSaveInputCust);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer newCustomer = new Customer(
                        etCode.getText().toString(),
                        etName.getText().toString(),
                        etAddress.getText().toString(),
                        etFoulType.getText().toString(),
                        spinnerTarif.getSelectedItem().toString()
                );
                dbHandler.addCustomer(newCustomer);
                Toast.makeText(InputCustomerActivity.this, "Data Anda telah disimpan. ", Toast.LENGTH_SHORT).show();

                etCode.setText("");
                etName.setText("");
                etAddress.setText("");
                etFoulType.setText("");
                spinnerTarif.setSelection(0);
            }
        });
        
        // BUTTON UPLOAD IMAGE
        btnUploadImg = (Button) findViewById(R.id.btnUploadImg);
        btnUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpload = new Intent();
                 intentUpload.setType("image/*");
                intentUpload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentUpload, "Select Picture"), SELECT_PICTURE);
                /*
                // Reading all contacts
                Log.d("Reading: ", "Reading all contacts..");
                List<Customer> Customers = dbHandler.getAllCustomer();

                for (Customer cn : Customers) {
                    String log = "Id: " + cn.get_id() + " ,Name: " + cn.get_name() + " ,Phone: " + cn.get_tarif_daya();
                    // Writing Customers to log
                    Log.d("Name: ", log);
                    Toast.makeText(InputCustomerActivity.this, "See your logs!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        // BUTTON TAKE IMAGE
        btnSave = (Button) findViewById(R.id.btnTakeImg);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start activity containts camera buttons
                // TODO, make any change to MetalCamera Class, so that they can call it directly
                Intent camIntent = new Intent(InputCustomerActivity.this, MetalCamera.class);
                startActivity(camIntent);
                finish();
            }
        });
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode== Activity.RESULT_OK){
            if (requestCode==SELECT_PICTURE){
                Uri selectedImageUri=data.getData();
                filemanagerString=selectedImageUri.getPath();
                selectedImagePath = getPath(selectedImageUri);
                imagePelanggan.setImageURI(selectedImageUri);
                /*cek bitmap
                imagePath.getBytes();
                Bitmap bm = BitmapFactory.decodeFile(imagePath);
                */

            }
        }
    }

    public String getPath(Uri uri) {

        String[] projection = {MediaStore.MediaColumns.DATA};
        cursor = managedQuery(uri, projection, null, null, null);
        column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    }

    /*public void addCustomer(View view) {
        TextView code, name, address, foul, tarif;

        //declaration

        long id = dbaseHelper.insertData(code, name, address, foul, tarif);
    }*/
}
