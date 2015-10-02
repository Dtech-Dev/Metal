package com.dtech.posisi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.dtech.orm.ImageCustomer;
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

    private String cbLat;
    private String cbLong;

    private Toolbar toolbar;
    private EditText etCode, etName, etAddress, etFoulType;
    private Spinner spinnerTarif;
    private Button btnSave;
//    private Button btnTakeImg;
//    private Button btnUploadImg;
    private TextView tLat;
    private TextView tLong;

    private ImageView imagePelanggan;

    private GoogleApiClient mGoogleApiClient;

    private int count = 0;
    Uri outputFileUri;

    byte[] image;

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
        if (selectedImageUri != null || bm != null) {
            imagePelanggan.setDrawingCacheEnabled(true);
            imagePelanggan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
            imagePelanggan.buildDrawingCache();
        }
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
                boolean exist = Customer.custExist("code = ? ", etCode.getText().toString());
                Customer cust = null;
                if (!exist) {
                    cust = new Customer(
                            etCode.getText().toString(),
                            etName.getText().toString(),
                            etAddress.getText().toString(),
                            etFoulType.getText().toString(),
                            spinnerTarif.getSelectedItem().toString(),
                            cbLat, cbLong
                            //image
                    );
                    cust.save();
                } else {
                    List<Customer> custm = Customer.find(Customer.class, "code = ?",
                            etCode.getText().toString());
                    for (Customer cst : custm){
                        cst.setCode(etCode.getText().toString());
                        cst.setName(etName.getText().toString());
                        cst.setAddress(etAddress.getText().toString());
                        cst.setFoultype(etFoulType.getText().toString());
                        cst.setTarifdaya(spinnerTarif.getSelectedItem().toString());
                        cst.setLatTude(cbLat);
                        cst.setLongTude(cbLong);
                        cst.save();
                        cust = cst;
                    }
                }

                if (cust == null) {
                    Toast.makeText(InputCustomerActivity.this, "Penyimpanan gagal!! ", Toast.LENGTH_SHORT).show();
                    return;
                }

                String encodedImg = ImageCustomer.encodeImage(imagePelanggan.getDrawingCache(), null, 100);
                ImageCustomer cstImage = new ImageCustomer(cust, "test", cbLat, cbLong, new byte[]{}, encodedImg);
                cstImage.save();
                Toast.makeText(InputCustomerActivity.this, "Data Anda telah disimpan. ", Toast.LENGTH_SHORT).show();

                etCode.setText("");
                etName.setText("");
                etAddress.setText("");
                etFoulType.setText("");
                spinnerTarif.setSelection(0);
                tLat.setText("");
                tLong.setText("");
                imagePelanggan.setImageBitmap(null);
            }
        });
    }

    private void setEditTextCustInfo() {
        // ALL EDIT TEXT
        etName = (EditText) findViewById(R.id.textCustName);
        etAddress = (EditText) findViewById(R.id.textCustAddress);
        etFoulType = (EditText) findViewById(R.id.textJenisPelanggaran);
        tLat = (TextView) findViewById(R.id.tLat);
        tLong = (TextView) findViewById(R.id.tLong);

        etCode = (EditText) findViewById(R.id.textCustID);
        etCode.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")) {
                    boolean exist = Customer.custExist("code = ? ", s.toString());
                    if (exist) {
                        List<Customer> cust = Customer.find(Customer.class, "code = ? ",
                                s.toString());
                        if (cust.size() > 1) {
                            Toast.makeText(InputCustomerActivity.this, "Data redundan, code: " + s.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        for (Customer custx : cust){
                            etName.setText(custx.getName());
                            etAddress.setText(custx.getAddress());
                            etFoulType.setText(custx.getTarifdaya());
                            tLat.setText(custx.getLatTude());
                            tLong.setText(custx.getLongTude());
//                            spinnerTarif.getSelectedItem().toString(); // TODO : how to set from DB??
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // maybe we need it later
            }

            public void afterTextChanged(Editable s) {
                // maybe we need it later
            }
        });

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
            options.inSampleSize = 3;

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
            double latThis=lastLocation.getLatitude();
            double longThis=lastLocation.getLongitude();
            tLat.setText(String.valueOf(lastLocation.getLatitude()));
            tLong.setText(String.valueOf(lastLocation.getLongitude()));
            cbLat=tLat.getText().toString();
            cbLong = tLong.getText().toString();
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
