package com.dtech.posisi;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.orm.DefaultOperation;
import com.dtech.orm.MtlPelanggan;
import com.dtech.orm.MtlPelanggaran;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ADIST on 9/17/2015.
 */
public class InputCustomerActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener {
    private static final int TAKE_PHOTO_CODE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final String IMAGE_DIRECTORY_NAME = Environment.
            getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";

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
    private TextClock textClock;
    private EditText etFoulDate;
    private ImageView imagePelanggan;
    private EditText textDaya;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private int count = 0;
    Uri outputFileUri;

    byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_customer);

        setToolBar();
        setSpinnerTarif("");
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
                MtlPelanggan cust = validatePelanggan();

                if (cust == null) {
                    Toast.makeText(InputCustomerActivity.this, "Penyimpanan gagal!! ",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO, we need to modif activity that handle many images not one
                String encodedImg = DefaultOperation.encodeImage(imagePelanggan.getDrawingCache(),
                        null, 100);
                String foulDate = etFoulDate.getText().toString(); // + " " +
//                        textClock.getText().toString(); // TODO : later we set a time for this
                MtlPelanggaran fouls = new MtlPelanggaran(cust, etFoulDate.getText().toString(),
                        etFoulType.getText().toString(),
                        spinnerTarif.getSelectedItem().toString(),
                        new BigDecimal(textDaya.getText().toString()));
                fouls.save();
                Toast.makeText(InputCustomerActivity.this, "Data Anda telah disimpan. ",
                        Toast.LENGTH_SHORT).show();

                resetFields();
            }
        });
    }

    @Nullable
    private MtlPelanggan validatePelanggan() {
        boolean exist = MtlPelanggan.custExist("code = ? ", etCode.getText().toString());
        MtlPelanggan cust = null;
        if (!exist) {
            cust = new MtlPelanggan(
                    etCode.getText().toString(),
                    etName.getText().toString(),
                    etAddress.getText().toString()
            );
//                            spinnerTarif.getSelectedItem().toString(),
//                            cbLat, cbLong
//                            //image
//                    );
            cust.save();
        } else {
            List<MtlPelanggan> custm = MtlPelanggan.find(MtlPelanggan.class, "code = ?",
                    etCode.getText().toString());
            for (MtlPelanggan cst : custm) {
                cst.setCode(etCode.getText().toString());
                cst.setName(etName.getText().toString());
                cst.setAddress(etAddress.getText().toString());
//                        cst.setTarifdaya(spinnerTarif.getSelectedItem().toString());
//                        cst.setLastXPosition(cbLat);
//                        cst.setLastYPosition(cbLong);
                cst.save();
                cust = cst;
            }
        }
        return cust;
    }

    private void resetFields() {
        etCode.setText("");
        etName.setText("");
        etAddress.setText("");
        etFoulType.setText("");
        spinnerTarif.setSelection(0);
        tLat.setText("");
        tLong.setText("");
        imagePelanggan.setImageBitmap(null);
        textDaya.setText("0");
    }

    private void setEditTextCustInfo() {
        // ALL EDIT TEXT
        etName = (EditText) findViewById(R.id.textCustName);
        etAddress = (EditText) findViewById(R.id.textCustAddress);
        etFoulType = (EditText) findViewById(R.id.textJenisPelanggaran);
        etFoulDate = (EditText) findViewById(R.id.textFoulDate);
        etFoulDate.setText(DefaultOperation.dateToString(Calendar.getInstance().getTime()
                , DefaultOperation.DEFAULT_DATE_FORMAT));
        tLat = (TextView) findViewById(R.id.tLat);
        tLong = (TextView) findViewById(R.id.tLong);
        textDaya = (EditText) findViewById(R.id.textDaya);
        textClock = (TextClock) findViewById(R.id.textClock);

        etCode = (EditText) findViewById(R.id.textCustID);
        etCode.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    boolean exist = MtlPelanggan.custExist("code = ? ", s.toString());
                    if (exist) {
                        List<MtlPelanggan> cust = MtlPelanggan.find(MtlPelanggan.class, "code = ? ",
                                s.toString());
                        if (cust.size() > 1) {
                            Toast.makeText(InputCustomerActivity.this, "Data redundan, code: " + s.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        for (MtlPelanggan custx : cust) {
                            etName.setText(custx.getName());
                            etAddress.setText(custx.getAddress());
//                            tLat.setText(custx.getLastXPosition());
//                            tLong.setText(custx.getLastYPosition());
//                            setSpinnerTarif(custx.getTarifdaya()); // TODO, maybe we can pass it from MtlPelanggan class
                        }
                    } else {
                        etName.setText("");
                        etAddress.setText("");
                        setSpinnerTarif("");
                        tLat.setText("");
                        tLong.setText("");
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

    private void setSpinnerTarif(String value) {
        // SPINNER
        spinnerTarif = (Spinner) findViewById(R.id.spinnerTarif);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_tarif, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTarif.setAdapter(adapter);
        int spinnerPosition = 0;
        if (!value.equals(null) || value.length() > 1)
            spinnerPosition = adapter.getPosition(value);
        spinnerTarif.setSelection(spinnerPosition);
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
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void previewCapturedImage() {
        try {



            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 3;

            final Bitmap bitmap = BitmapFactory.decodeFile(outputFileUri.getPath(),
                    options);
           /* bitmap.setWidth(100);
            bitmap.setHeight(150);*/
            /*Bitmap catching = Bitmap.createBitmap(100, 150, Bitmap.Config.RGB_565);
            catching.sameAs(bitmap);*/

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

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
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
            /*1st way to handling null Location
            Toast.makeText(this, "Failed to find location", Toast.LENGTH_SHORT).show();*/

            /*2nd way to handling null Location
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            /*3rd way to handling null Location
            Toast.makeText(this, "Failed to find location", Toast.LENGTH_SHORT).show();
            backToMain();*/

            /*4th way to handling null location*/
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Please Wait");
            alert.setMessage("Wait while we finding your locations");


            Dialog alertDialog = alert.create();
            alertDialog.show();
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

    public void backToMain(){
        Intent backToMain;
        backToMain = new Intent(InputCustomerActivity.this, MainActivity.class);
        startActivity(backToMain);

    }
}
