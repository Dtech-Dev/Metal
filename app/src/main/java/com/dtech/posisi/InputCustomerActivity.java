package com.dtech.posisi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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

import com.dtech.orm.Customer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ADIST on 9/17/2015.
 */
public class InputCustomerActivity extends AppCompatActivity {
    private static final int TAKE_PHOTO_CODE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final String IMAGE_DIRECTORY_NAME = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";

    private Toolbar toolbar;
    private EditText etCode, etName, etAddress, etFoulType;
    private Spinner spinnerTarif;
    private Button btnTakeImg;
    private Button btnSave;
    private Button btnUploadImg;

    private ImageView imagePelanggan;

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
        setButtonUploadImg();
        setButtonTakeImage();
        setImageView(null, null);
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
        // BUTTON TAKE IMAGE
        btnTakeImg = (Button) findViewById(R.id.btnTakeImg);
        btnTakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start camera
                setImageDir();
                setImageNameFile(IMAGE_DIRECTORY_NAME);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });
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
        // BUTTON UPLOAD IMAGE
        btnUploadImg = (Button) findViewById(R.id.btnUploadImg);
        btnUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpload = new Intent();
                intentUpload.setType("image/*");
                intentUpload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentUpload, "Select Picture"), SELECT_PICTURE);
            }
        });
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
                            spinnerTarif.getSelectedItem().toString()
                    );
                    newCustomer.save();
                } else {
                    for (Customer cust : customer){
                        cust.setcode(etCode.getText().toString());
                        cust.setname(etName.getText().toString());
                        cust.setaddress(etAddress.getText().toString());
                        cust.setfoultype(etFoulType.getText().toString());
                        cust.settarifdaya(spinnerTarif.getSelectedItem().toString());
                        cust.save();
                    }
                }
                Toast.makeText(InputCustomerActivity.this, "Data Anda telah disimpan. ", Toast.LENGTH_SHORT).show();

                etCode.setText("");
                etName.setText("");
                etAddress.setText("");
                etFoulType.setText("");
                spinnerTarif.setSelection(0);
            }
        });
    }

    private void setEditTextCustInfo() {
        // ALL EDIT TEXT
        etCode = (EditText) findViewById(R.id.textCustID);
        etName = (EditText) findViewById(R.id.textCustName);
        etAddress = (EditText) findViewById(R.id.textCustAddress);
        etFoulType = (EditText) findViewById(R.id.textJenisPelanggaran);
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
}
