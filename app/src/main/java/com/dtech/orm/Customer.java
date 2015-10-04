package com.dtech.orm;

import android.graphics.Bitmap;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ADIST on 9/18/2015.
 */
public class Customer extends SugarRecord<Customer> {

    private String code;
    private String name;
    private String address;
    private String tarifdaya;
    private String lastXPosition;
    private String lastYPosition;
    private String lastVisit;
    private boolean active;

    // START : Constructor
    public Customer(){}

    public Customer(String code, String name, String address, String tarifDaya, String lastXPosition, String lastYPosition) {
        this.setCode(code);
        this.setName(name);
        this.setAddress(address);
        this.setTarifdaya(tarifDaya);
        this.setLastXPosition(lastXPosition);
        this.setLastYPosition(lastYPosition);
        this.active = true;
        //this.image = image;
    }
    // END : Constructor

    // START : Set & Get
    public static boolean custExist(String where, String params) {
        if (!where.contains("?"))
            return false;
        List<Customer> customer = Customer.find(Customer.class, where, params);
        return customer.size() > 0;
    }

    public String lastImage(){
        ImageCustomer lastImageRec = ImageCustomer.getLastImageRecord(this);
        if (lastImageRec != null)
            return lastImageRec.getImageTest();
        return null;
    }

    public Bitmap lastImageBitmap(){
        if (lastImage() != null)
            return ImageCustomer.decodeImage(lastImage());
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTarifdaya() {
        return tarifdaya;
    }

    public void setTarifdaya(String tarifdaya) {
        this.tarifdaya = tarifdaya;
    }

    public String getLastXPosition() {
        return lastXPosition;
    }

    public void setLastXPosition(String lastXPosition) {
        this.lastXPosition = lastXPosition;
    }

    public String getLastYPosition() {
        return lastYPosition;
    }

    public void setLastYPosition(String lastYPosition) {
        this.lastYPosition = lastYPosition;
    }

    public String getLastVisit() {
        ImageCustomer lastRecord = ImageCustomer.getLastImageRecord(this);
        SimpleDateFormat xxx = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (lastRecord == null) {
            return xxx.format(Calendar.getInstance().getTime());
        }
        return lastRecord.getFoulDate();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    // END : Set & Get
}
