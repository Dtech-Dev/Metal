package com.dtech.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADIST on 9/18/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // DB Version
    private static final int DATABASE_VERSION = 1;

    // DB Name
    private static final String DATABASE_NAME = "dbMetal";

    // List of Table(s)
    private static final String TABLE_CUSTOMER = "customer";

    // Columns of TABLE_CUSTOMER
    private static final String KEY_ID = "id";
    private static final String KEY_CODE = "code";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_FOUL_TYPE = "foul_type";
    private static final String KEY_TARIF_DAYA = "tarif_daya";

    // Constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateCustTabel = "CREATE TABLE " + TABLE_CUSTOMER +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_CODE + " TEXT," +
                    KEY_NAME + " TEXT," +
                    KEY_ADDRESS + " TEXT," +
                    KEY_FOUL_TYPE + " TEXT," +
                    KEY_TARIF_DAYA + " TEXT)";
        db.execSQL(sqlCreateCustTabel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_CUSTOMER);
        onCreate(db);
    }

    public void addCustomer(Customer customer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CODE, customer.get_code());
        values.put(KEY_NAME, customer.get_name());
        values.put(KEY_ADDRESS, customer.get_address());
        values.put(KEY_FOUL_TYPE, customer.get_foul_type());
        values.put(KEY_TARIF_DAYA, customer.get_tarif_daya());

        db.insert(TABLE_CUSTOMER, null, values);
        db.close();
    }

    public Customer getCustomer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cr = db.query(TABLE_CUSTOMER, new String[]{KEY_ID, KEY_CODE, KEY_NAME, KEY_ADDRESS, KEY_FOUL_TYPE, KEY_TARIF_DAYA},
                KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cr != null)
            cr.moveToFirst();

        Customer customer = new Customer(Integer.parseInt(cr.getString(0)),
                cr.getString(1), cr.getString(2),
                cr.getString(3), cr.getString(4),
                cr.getString(5));

        return customer;
    }
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<Customer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cr.moveToFirst()) {
            do {
                Customer Customer = new Customer();
                Customer.set_id(Integer.parseInt(cr.getString(0)));
                Customer.set_code(cr.getString(1));
                Customer.set_name(cr.getString(2));
                Customer.set_address(cr.getString(3));
                Customer.set_foul_type(cr.getString(4));
                Customer.set_tarif_daya(cr.getString(5));
                // Adding Customer to list
                customerList.add(Customer);
            } while (cr.moveToNext());
        }

        // return Customer list
        return customerList;
    }
    public int getCustomerCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CUSTOMER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery(countQuery, null);
        cr.close();

        // return count
        return cr.getCount();
    }
    public int updateCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CODE, customer.get_code());
        values.put(KEY_NAME, customer.get_name());
        values.put(KEY_ADDRESS, customer.get_address());
        values.put(KEY_FOUL_TYPE, customer.get_foul_type());
        values.put(KEY_TARIF_DAYA, customer.get_tarif_daya());

        return db.update(TABLE_CUSTOMER, values, KEY_ID + "=?",
                new String[]{String.valueOf(customer.get_id())});
    }
    public void deleteCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMER, KEY_ID + " = ?",
                new String[] { String.valueOf(customer.get_id()) });
        db.close();
    }
}



