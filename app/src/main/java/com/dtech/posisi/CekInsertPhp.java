package com.dtech.posisi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class CekInsertPhp extends AppCompatActivity {

    public EditText edName;
    EditText edAddress;



    InputStream is = null;
    String name;
    String address;
    String line=null;
    String result = null;
    int code;



    private static String url_insert = "http://droidsense.web.id/metal/insert.php";


    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_insert_php);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        edName = (EditText) findViewById(R.id.edName);
        edAddress = (EditText) findViewById(R.id.edAddress);

        name = edName.getText().toString();
        address = edAddress.getText().toString();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                //insert.execute(name, address);
                name = edName.getText().toString();
                address = edAddress.getText().toString();
                new InsertData().execute();

            }
        });
    }


    class InsertData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CekInsertPhp.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();

            values.add(new BasicNameValuePair("name", name));
            values.add(new BasicNameValuePair("address", address));

            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://droidsense.web.id/metal/insert.php");
                httpPost.setEntity(new UrlEncodedFormEntity(values));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch (Exception e) {

            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.i("TAG", "Retrieved");
            } catch (Exception e) {
                Log.i("TAG", e.toString());
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                code = (jsonObject.getInt("code"));
                if (code == 1) {
                    Log.i("msg", "Success");
                } else {

                }
            } catch (Exception e) {
                Log.i("TAG", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(this, "Insert Succes " + result, Toast.LENGTH_SHORT).show();
            pDialog.dismiss();
            edName.setText("");
            edAddress.setText("");
        }

    }
}