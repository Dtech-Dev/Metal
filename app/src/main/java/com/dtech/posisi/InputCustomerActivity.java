package com.dtech.posisi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.orm.Customer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ADIST on 9/17/2015.
 */
public class InputCustomerActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int TAKE_PHOTO_CODE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final String IMAGE_DIRECTORY_NAME = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";

    private Toolbar toolbar;
    private EditText etCode, etName, etAddress, etFoulType;
    private Spinner spinnerTarif;
    private Button btnTakeImg;
    private Button btnSave;
    private Button btnUploadImg;
    private TextView tLat;
    private TextView tLong;

    private ImageView imagePelanggan;

    private GoogleApiClient mGoogleApiClient;

    private int count = 0;
    Uri outputFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_customer);


        setToolBar();
        setSpinnerTarif();
        setEditTextCustInfo();
        setButtonSave();
        //setButtonUploadImg();
        //setButtonTakeImage();
        setImageView(null, null);
        buildGoogleApiClient();
    }

    private void setImageView(Uri selectedImageUri, Bitmap bm) {
        if (imagePelanggan == null)
            imagePelanggan = (ImageView) findViewById(R.id.imageView);
        if (selectedImageUri != null)
            imagePelanggan.setImageURI(selectedImageUri);
        if (bm != null)
            imagePelanggan.setImageBitmap(bm);
    }
    private void setButtonTakeImage() {
        // MENU TAKE IMAGE

                // start camera
                setImageDir();
                setImageNameFile(IMAGE_DIRECTORY_NAME);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);

    }

    private Uri setImageNameFile(String dir) {
        count++;
        String file = dir+count+".jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {
            Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                    + IMAGE_DIRECTORY_NAME + " directory");
        }

        outputFileUri = Uri.fromFile(newfile);
        return outputFileUri;
    }

    @NonNull
    private String setImageDir() {
        File newdir = new File(IMAGE_DIRECTORY_NAME);
        if (!newdir.exists())
            if (!newdir.mkdir())
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
        return IMAGE_DIRECTORY_NAME;
    }

    private void setButtonUploadImg() {
        // MENU UPLOAD IMAGE

                Intent intentUpload = new Intent();
                intentUpload.setType("image/*");
                intentUpload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentUpload, "Select Picture"), SELECT_PICTURE);

    }

    private void setButtonSave() {
        // BUTTON SAVE
        btnSave = (Button) findViewById(R.id.btnSaveInputCust);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = "select * from customer where code = ?";
//                List<Customer> customer = Customer.findWithQuery(Customer.class, sql, etCode.getText().toString());
                List<Customer> customer = Customer.find(Customer.class, "code = ? ", etCode.getText().toString());
                if (customer.size() == 0) {
                    Customer newCustomer = new Customer(
                            etCode.getText().toString(),
                            etName.getText().toString(),
                            etAddress.getText().toString(),
                            etFoulType.getText().toString(),
                            spinnerTarif.getSelectedItem().toString(),
                            tLat.getText().toString(),
                            tLong.getText().toString()
                    );
                    newCustomer.save();
                } else {
                    for (Customer cust : customer) {
                        cust.setcode(etCode.getText().toString());
                        cust.setname(etName.getText().toString());
                        cust.setaddress(etAddress.getText().toString());
                        cust.setfoultype(etFoulType.getText().toString());
                        cust.settarifdaya(spinnerTarif.getSelectedItem().toString());
                        cust.setLatTude(tLat.getText().toString());
                        cust.setLongTude(tLong.getText().toString());
                        cust.save();
                    }
                }
                Toast.makeText(InputCustomerActivity.this, "Data Anda telah disimpan. ", Toast.LENGTH_SHORT).show();

                etCode.setText("");
                etName.setText("");
                etAddress.setText("");
                etFoulType.setText("");
                spinnerTarif.setSelection(0);
                tLat.setText("");
                tLong.setText("");

            }
        });
    }

    private void setEditTextCustInfo() {
        // ALL EDIT TEXT
        etCode = (EditText) findViewById(R.id.textCustID);
        etName = (EditText) findViewById(R.id.textCustName);
        etAddress = (EditText) findViewById(R.id.textCustAddress);
        etFoulType = (EditText) findViewById(R.id.textJenisPelanggaran);
        tLat = (TextView) findViewById(R.id.tLat);
        tLong = (TextView) findViewById(R.id.tLong);
    }

    private void setSpinnerTarif() {
        // SPINNER
        spinnerTarif = (Spinner) findViewById(R.id.spinnerTarif);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_tarif, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTarif.setAdapter(adapter);
    }

    private void setToolBar() {
        // TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.barInputCust);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (resultCode == Activity.RESULT_OK && requestCode==SELECT_PICTURE){
//            Uri selectedImageUri = data.getData();
//            filemanagerString = selectedImageUri.getPath();
//            selectedImagePath = getPath(selectedImageUri);
//            imagePelanggan.setImageURI(selectedImageUri);
//            /*cek bitmap
//            imagePath.getBytes();
//            Bitmap bm = BitmapFactory.decodeFile(imagePath);
//            */

//        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PICTURE:
                    setImageFromUpload(data);
                    break;
                case TAKE_PHOTO_CODE:
                    previewCapturedImage();
                    break;
            }
        }
    }

    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 2;

            final Bitmap bitmap = BitmapFactory.decodeFile(outputFileUri.getPath(),
                    options);

            setImageView(null, bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setImageFromUpload(Intent data) {
        Uri selectedImageUri = data.getData();
        setImageView(selectedImageUri, null);
    }

    public void addCustomer(View view) {
        String code, name, address, foul, tarif;

        //declaration

        setEditTextCustInfo();
        code = etCode.getText().toString();
        name = etName.getText().toString();
        address = etAddress.getText().toString();
        foul = etFoulType.getText().toString();
        tarif = spinnerTarif.getSelectedItem().toString();
//        long id = dbaseHelper.insertData(code, name, address, foul, tarif);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if (id == R.id.gal) {
            setButtonUploadImg();
        }
        if (id == R.id.take) {
            setButtonTakeImage();
        }

        return super.onOptionsItemSelected(item);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_input_cust, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            tLat.setText(String.valueOf(lastLocation.getLatitude()));
            tLong.setText(String.valueOf(lastLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Failed to find location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}
