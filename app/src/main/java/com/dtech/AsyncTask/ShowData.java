package com.dtech.AsyncTask;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Administrator on 23/10/2015.
 */
public class ShowData extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private void ambilData(String result) {
        try {
            JSONArray posts = new JSONArray(result);

        }  catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
