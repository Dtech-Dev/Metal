package com.dtech.orm;

import android.graphics.Bitmap;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by ADIST on 9/18/2015.
 */
public class Customer extends SugarRecord<Customer> {

    private String code;
    private String name;
    private String address;
    private String foultype;
    private String tarifdaya;
    private String latTude;
    private String longTude;

    // START : Constructor
    public Customer(){}

    public Customer(String code, String name, String address, String foulType, String tarifDaya, String latTude, String longTude) {
        this.setCode(code);
        this.setName(name);
        this.setAddress(address);
        this.setFoultype(foulType);
        this.setTarifdaya(tarifDaya);
        this.setLatTude(latTude);
        this.setLongTude(longTude);
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

    public String getFoultype() {
        return foultype;
    }

    public void setFoultype(String foultype) {
        this.foultype = foultype;
    }

    public String getTarifdaya() {
        return tarifdaya;
    }

    public void setTarifdaya(String tarifdaya) {
        this.tarifdaya = tarifdaya;
    }

    public String getLatTude() {
        return latTude;
    }

    public void setLatTude(String latTude) {
        this.latTude = latTude;
    }

    public String getLongTude() {
        return longTude;
    }

    public void setLongTude(String longTude) {
        this.longTude = longTude;
    }
    // END : Set & Get
}
