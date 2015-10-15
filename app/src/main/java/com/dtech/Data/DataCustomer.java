package com.dtech.Data;

/**
 * Created by Administrator on 14/10/2015.
 */
public class DataCustomer {
    String name;
    String address;

    /*public DataCustomer(String name, String address) {
        this.name = name;
        this.address = address;
    }*/
    public DataCustomer(){

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
}
